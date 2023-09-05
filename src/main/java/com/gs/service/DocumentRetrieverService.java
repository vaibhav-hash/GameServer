package com.gs.service;

import com.gs.schemas.global.entity.Info;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class DocumentRetrieverService {

    public Iterable<Document> getDocumentsFromCollectionsWithQuery(String collectionName, BasicDBObject query, MongoTemplate mongoTemplate) {
        MongoDatabase database = mongoTemplate.getDb();
        MongoCollection<Document> collection = database.getCollection(collectionName);
        List<Document> results = new ArrayList<>();

        try (MongoCursor<Document> cursor = collection.find(query).iterator()) {
            while (cursor.hasNext()) {
                results.add(cursor.next());
            }
        }
        log.info("Hello The Results are");
        return results;
    }

    public Iterable<Document> getDocumentsFromCollections(MongoTemplate mongoTemplate, String... collectionNames) {
        MongoDatabase database = mongoTemplate.getDb();
        // Create an empty list to store query results
        List<Document> results = new ArrayList<>();

        for (String collectionName : collectionNames) {
            MongoCollection<Document> collection = database.getCollection(collectionName);

            try (MongoCursor<Document> cursor = collection.find().iterator()) {
                while (cursor.hasNext()) {
                    results.add(cursor.next());
                }
            }
        }
        log.info("Hello The Results are:{}", results);
        return results;
    }
}
