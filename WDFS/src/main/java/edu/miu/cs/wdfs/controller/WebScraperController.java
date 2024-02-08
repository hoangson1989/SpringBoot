package edu.miu.cs.wdfs.controller;

import edu.miu.cs.wdfs.controller.dto.AddUrlRequest;
import edu.miu.cs.wdfs.integration.Sender;
import edu.miu.cs.wdfs.repository.Link;
import edu.miu.cs.wdfs.service.WebCrawlerService;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/crawler")
@RequiredArgsConstructor
public class WebScraperController {
    // This controller handles web scraping and link filtering operations.

	@Autowired
    private WebCrawlerService webCrawlerService;
    // Autowired instance of WebCrawlerService to initiate web crawling.

    @Autowired
    private Sender sender;
    // Autowired instance of Sender to send messages to a Kafka topic.

    List<String> contentList = Arrays.asList("Drugs", "Gambling", "Guns", "Malware", "Nude", "Porn", "Sexuality", "Violence"  );

    ExecutorService executorService = Executors.newFixedThreadPool(5);

    @GetMapping("/crawlinputurl")
    public ResponseEntity<?> crawlFromUrl(@RequestParam String url) throws InterruptedException {

        if(webCrawlerService.isSourceUrlExisted(url)){
            return new ResponseEntity<>("This url is already added.", HttpStatus.OK);
        }else{
            webCrawlerService.saveSourceUrlToDB(url);
//            executorService.submit(() -> crawlUrlInBackground(url));
//            webCrawlerService.crawlFromInputUrl(url);
            return new ResponseEntity<>("This url is created", HttpStatus.OK);
        }
    }

    @PostMapping("/urls")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void crawlFromUrl(@RequestBody AddUrlRequest urlRequest) {
        webCrawlerService.saveNewUrls(urlRequest.getUrls());
    }

    @DeleteMapping("/urls")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@RequestParam String url) {
        webCrawlerService.deleteByUrl(url);
    }

    @GetMapping("/urls")
    public  ResponseEntity<List<Link>> findAllLinks() {

        return   new ResponseEntity<> (webCrawlerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/urls/{urlQuery}")
    public List<Link> find(@PathVariable String urlQuery) {
        return webCrawlerService.findAll();
    }

//    private void crawlUrlInBackground(String url){
//
//        Set<String> crawledLinks = webCrawlerService.crawlFromInputUrl(url);
//        NewAPIPayload kafkaPayload=null;
//
//        for (String cr : crawledLinks) {
//            System.out.println(cr);
//            if(!webCrawlerService.isExistInDB(cr)){
//                try{
//                    Connection con = Jsoup.connect(cr);
//                    Document doc = con.get();
//                    if(isContainKeyword(doc,contentList)){
//                        webCrawlerService.setLinkToDB(cr);
//                        String title = doc.title();
//                        String description = doc.select("meta[name=description]").attr("content");
//
//                        kafkaPayload = new NewAPIPayload(title, cr, description);
//                        sender.send(kafkaPayload);
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }
//    }



    private boolean isContainKeyword(Document document, List<String> keywordList){
        for(String word: keywordList ){
            if (document.text().contains(word)) {
                return false;
            }
        }
        return true;
    }

    @PostMapping("/crawlurl")
    public void crawlFromUrl(@RequestBody String[] url, @RequestParam String searchQuery) throws InterruptedException {
    	webCrawlerService.crawlFromUrl(url, searchQuery);
    	System.out.println("==testing==");
    }

    @GetMapping("/crawl")
    public void startCrawling(@RequestParam String searchQuery) {
        // Initiates the web crawling process and link filtering based on a search query.
        // You can use the list of URLs from your original code or modify it as needed.
        List<String> urlsToCrawl = new ArrayList<>();
        urlsToCrawl.add("https://www.abcnews.com");
        urlsToCrawl.add("https://www.npr.org");
        urlsToCrawl.add("https://www.nytimes.com");

        startCrawling(urlsToCrawl, searchQuery);
    }
    
    
    private void startCrawling(List<String> urlsToCrawl, String searchQuery) {
    	HashMap<String, Document> crawledLinks = webCrawlerService.startCrawling(urlsToCrawl);
        for (String cr : crawledLinks.keySet()) {
            System.out.println(cr);
        }

        crawledLinks = webCrawlerService.filterLinks(crawledLinks, searchQuery);
        for (String urls : crawledLinks.keySet()) {
            System.out.println(urls);
        }

        crawledLinks.keySet().forEach(System.out::println);
    }
}
