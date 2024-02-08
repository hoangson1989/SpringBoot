package edu.miu.cs.acs.database;

import edu.miu.cs.acs.models.ApiPayload;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface URLRepository extends MongoRepository<ApiPayload,String> {
}
