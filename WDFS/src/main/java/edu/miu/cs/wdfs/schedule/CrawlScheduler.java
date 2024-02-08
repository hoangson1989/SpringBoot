package edu.miu.cs.wdfs.schedule;

import edu.miu.cs.wdfs.service.WebCrawlerService;
import edu.miu.cs.wdfs.repository.SourceUrl;
import edu.miu.cs.wdfs.repository.SourceUrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CrawlScheduler {
    @Autowired
    private MongoOperations mongoOperations;
    private final SourceUrlRepository sourceUrlRepository;
    private final WebCrawlerService webCrawlerService;

    @Scheduled(fixedRate = 1000)
    public void execute(){

        BasicQuery query = new BasicQuery("{ status : 'NEW' }");
        SourceUrl sourceUrl = mongoOperations.findOne(query, SourceUrl.class);
        //if found one
        if(sourceUrl != null) {
            System.out.println("URL to crawl - " + sourceUrl.getSourceUrl());
            // update status to PROCESSING
            sourceUrl.setStatus("PROCESSING");
            sourceUrlRepository.save(sourceUrl);
            //start crawling
            webCrawlerService.crawlFromInputUrl(sourceUrl.getSourceUrl());
            //update status to Completed after crawl
            sourceUrl.setStatus("COMPLETED");
            sourceUrlRepository.save(sourceUrl);
        }
    }
}
