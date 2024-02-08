package edu.miu.cs.wdfs.service;

import edu.miu.cs.wdfs.integration.Sender;
import edu.miu.cs.wdfs.model.NewAPIPayload;
import edu.miu.cs.wdfs.repository.Link;
import edu.miu.cs.wdfs.repository.LinkRepository;
import edu.miu.cs.wdfs.repository.SourceUrl;
import edu.miu.cs.wdfs.repository.SourceUrlRepository;
import lombok.Data;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
@Data
public class WebCrawlerService {


    @Autowired
    LinkRepository linkRepository;

    @Autowired
    SourceUrlRepository sourceUrlRepository;
    // This service class provides web crawling functionality to discover and collect links from web pages.

    private static final int MAX_DEPTH = 3;
    // Maximum depth of crawling, limiting how far the crawler will traverse links.

	private HashMap<String, Document> visitedLinks = new HashMap<>();
	// A set to keep track of visited links, avoiding duplicate requests.

	private Set<String> existsLinks = new HashSet<>();
	// A set to keep track of visited links, avoiding duplicate requests.

	@Autowired
	private Sender sender;


	private List<String> contentList = Arrays.asList("Drugs", "Gambling", "Guns", "Malware", "Nude", "Porn",
			"Sexuality", "Violence");

	// This service class provides web crawling functionality to discover and
	// collect links from web pages.


	@Async
	@Transactional
    public void crawlFromInputUrl(String url) {
        // Initiates the web crawling process and returns a set of visited links.
		existsLinks.clear(); // Clear the visited links set before starting.
		crawlFromUrl(1, url);
		NewAPIPayload kafkaPayload = null;

		for (String cr : existsLinks) {
			System.out.println(cr);
			if(!isExistInDB(cr)){
				try{
					Connection con = Jsoup.connect(cr);
					Document doc = con.get();
					if(isContainKeyword(doc,contentList)){
						setLinkToDB(cr);
						String title = doc.title();
						String description = doc.select("meta[name=description]").attr("content");

						kafkaPayload = new NewAPIPayload(title, cr, description);
						sender.send(kafkaPayload);
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
    }

    public void setLinkToDB(String url){
        linkRepository.save(new Link(url));
        System.out.println("Save db successfully "+url);
    }

    public void saveSourceUrlToDB(String url){
		SourceUrl sourceUrl = SourceUrl.builder()
				.sourceUrl(url)
				.status("NEW")
				.build();
        sourceUrlRepository.save(sourceUrl);
        System.out.println("Save SourceURL to Database successfully "+sourceUrl);
    }

    public boolean isExistInDB(String url){
        Optional<Link> existedLink = linkRepository.findById(url);
        if (existedLink.isEmpty()) {
            return false;
        }else {
            return true;
        }
    }

    public boolean isSourceUrlExisted(String url){
        Optional<SourceUrl> existedSourceUrl = sourceUrlRepository.findById(url);
        if (!existedSourceUrl.isPresent()) {
            return false;
        }else {
            return true;
        }
    }

    public void deleteRecords(){
        linkRepository.deleteAll();
    }

	public void deleteByUrl(String url) {
		//!!TODO delete me
	}

	public List<Link> findAll() {
		//!!TODO delete me
		return null;
	}
	public void saveNewUrls(String [] urls) {

	}


	public HashMap<String, Document> startCrawling(List<String> urls) {
		// Initiates the web crawling process and returns a set of visited links.
		visitedLinks.clear(); // Clear the visited links set before starting.

		for (String url : urls) {
			crawl(1, url); // Start crawling from the provided URLs.
		}
		return visitedLinks; // Return the set of visited links.
	}

	public void saveToDB(String url) {
		linkRepository.save(new Link(url));
		System.out.println("Save db successfully " + url);
	}

	@Async
	@Transactional
	public void crawlFromUrl(String[] url, String searchQuery) {
//		

		if (url == null || url.length == 0) {
			return;
		}
		List<String> urlsToCrawl = new ArrayList<>();
		for (String s : url) {
			urlsToCrawl.add(s);
		}
		HashMap<String, Document> crawledLinks = startCrawling(urlsToCrawl);

		HashMap<String, Document> filteredCrawledLinks = filterLinks(crawledLinks, searchQuery);

		for (String cr : filteredCrawledLinks.keySet()) {
			System.out.println(cr);
			if (!isExistInDB(cr)) {
				try {
					Document doc = null;
					if (filteredCrawledLinks.get(cr) == null) {
						Connection con = Jsoup.connect(cr);
						doc = con.get();
					} else {
						System.out.println("Already in HashMap");
						doc = filteredCrawledLinks.get(cr);
					}

					if (isContainKeyword(doc, contentList)) {
						saveToDB(cr);
						String title = doc.title();
						String description = doc.select("meta[name=description]").attr("content");
						sender.send(new NewAPIPayload(title, cr, description));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public HashMap<String, Document> filterLinks(HashMap<String, Document> links, String searchQuery) {
		// Filters links based on the provided search query.
		HashMap<String, Document> filteredLinks = new HashMap<>();
		for (String link : links.keySet()) {
			if (link.contains(searchQuery)) {
				filteredLinks.put(link, links.get(link));
			}
		}
		return filteredLinks;
	}

	private boolean isContainKeyword(Document document, List<String> keywordList) {
		for (String word : keywordList) {
			if (document.text().contains(word)) {
				return false;
			}
		}
		return true;
	}

	private void crawl(int level, String url) {
		// Recursive method to crawl web pages to the specified depth.
		if (level <= MAX_DEPTH) {
			Document doc = request(url);
			if (doc != null) {
				for (Element link : doc.select("a[href]")) {
					String nextLink = link.absUrl("href");
					if (!visitedLinks.containsKey(nextLink)) {
						visitedLinks.put(nextLink, null);
						crawl(level + 1, nextLink); // Recursively crawl the next link.
					}
				}
			}
		}
	}

	private void crawlFromUrl(int level, String url) {
		// Recursive method to crawl web pages to the specified depth.
		if (level <= MAX_DEPTH) {
			Document doc = request(url);
			if (doc != null) {
				for (Element link : doc.select("a[href]")) {
					String nextLink = link.absUrl("href");
					if (!existsLinks.contains(nextLink)) {
						existsLinks.add(nextLink);
						crawl(level + 1, nextLink); // Recursively crawl the next link.
					}
				}
			}
		}
	}

	private Document request(String url) {
		// Sends an HTTP request to the specified URL and retrieves the web page
		// content.
		try {
			Connection con = Jsoup.connect(url);
			Document doc = con.get();
			if (con.response().statusCode() == 200) {
				System.out.println("Crawling url " + url);
				visitedLinks.put(url, doc); // Mark the URL as visited.
				return doc;
			}
			return null;
		} catch (IOException e) {
			return null; // Handle IO exceptions and return null for unsuccessful requests.
		}
	}

}
