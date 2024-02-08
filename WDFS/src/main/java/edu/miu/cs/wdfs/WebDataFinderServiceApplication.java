package edu.miu.cs.wdfs;
//String url = "https://mixedanalytics.com/blog/list-actually-free-open-no-auth-needed-apis/"; // URL to the webpage to scrape

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableKafka
@EnableDiscoveryClient
@Configuration
@EnableAsync
@EnableScheduling
public class WebDataFinderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebDataFinderServiceApplication.class, args);

    }

}



