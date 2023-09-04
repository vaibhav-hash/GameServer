package com.gs.service;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.stereotype.Service;

import static java.util.Collections.singletonList;

@Service
@Slf4j
public class DynamicMongoClientService {

    MongoProperties mongoProperties;
    MongoClient mongoClient;
    MongoDatabaseFactory mongoDatabaseFactory;
    MongoTemplate mongoTemplate;

    public DynamicMongoClientService() {
    }

    public void generateDynamicMongoClient() {
        if(mongoProperties==null)
            log.error("Please set MongoProperties");
        MongoCredential credential = MongoCredential
                .createCredential(mongoProperties.getUsername(),
                        mongoProperties.getAuthenticationDatabase(),
                        mongoProperties.getPassword());

        mongoClient = MongoClients.create(MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder
                        .hosts(singletonList(new ServerAddress(mongoProperties.getHost(),
                                mongoProperties.getPort()))))
                .credential(credential)
                .build());
    }

    public void generateDynamicMongoFactory() {
        if(mongoClient==null)
            generateDynamicMongoClient();
        this.mongoDatabaseFactory = new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }

    public void generateDynamicMongoTemplate() {
            if(mongoDatabaseFactory==null)
                generateDynamicMongoFactory();
        mongoTemplate = new MongoTemplate(mongoDatabaseFactory);
    }

    public void setMongoProperties(MongoProperties mongoProperties)
    {
        this.mongoProperties = mongoProperties;
        generateDynamicMongoClient();
        generateDynamicMongoFactory();
        generateDynamicMongoTemplate();
        // if you are setting externally call other functions to set fields according to this
    }
    public MongoProperties getMongoProperties() {
        return mongoProperties;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoDatabaseFactory getMongoDatabaseFactory() {
        return mongoDatabaseFactory;
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
}
