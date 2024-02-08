package edu.miu.cs.wdfs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceUrlRepository extends MongoRepository<SourceUrl, String> {
}
