package com.gs.controller;

import com.gs.service.DynamicMongoClientService;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class Temp {

    @Autowired
    DynamicMongoClientService dynamicMongoClientService;

    @GetMapping("/test")
    public void justWorkCustom() {
        log.info("Inside the Test Api");
        MongoProperties mongoProperties = new MongoProperties();
        mongoProperties.setHost("localhost");
        mongoProperties.setPort(27017);
        mongoProperties.setPassword("password".toCharArray());
        mongoProperties.setUsername("root");
        mongoProperties.setDatabase("game");
        mongoProperties.setAuthenticationDatabase("admin");
        dynamicMongoClientService.setMongoProperties(mongoProperties);
        MongoDatabaseFactory mongoDatabaseFactory = dynamicMongoClientService.getMongoDatabaseFactory();
        log.info("Collection names in mongo db:{}", mongoDatabaseFactory.getMongoDatabase().listCollectionNames());
        getDocumentsFromCollections("game_items", "game_purchase_history");
    }

    public Iterable<Document> getDocumentsFromCollectionsWithQuery(String collectionName, BasicDBObject query) {
        MongoDatabase database = dynamicMongoClientService.getMongoTemplate().getDb();
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

    public Iterable<Document> getDocumentsFromCollections(String... collectionNames) {
        MongoDatabase database = dynamicMongoClientService.getMongoTemplate().getDb();
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
        /*db.grantRolesToUser(
   "root",
   [ "readWrite" ,{ role: "readWrite", db: "game" },
      { role: "readWrite", db: "global_games" },
      { role: "readWrite", db: "global" },
      { role: "readWrite", db: "temp" } ],
   { w: "majority" , wtimeout: 4000 }
)*/
    }
}
