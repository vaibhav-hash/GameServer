package com.gs.schemas.global.repo;

import com.gs.schemas.global.entity.Info;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoMongoRepository extends MongoRepository<Info, ObjectId> {
}
