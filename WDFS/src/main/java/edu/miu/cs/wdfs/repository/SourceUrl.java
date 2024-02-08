package edu.miu.cs.wdfs.repository;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "source_urls")
@Data
@AllArgsConstructor
@Builder
public class SourceUrl {

    @Id
    private String id;

    private String sourceUrl;

    private String status;  //new (not started) / processing (crawling) / completed

}
