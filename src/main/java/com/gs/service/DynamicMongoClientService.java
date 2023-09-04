package com.gs.service;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.stereotype.Service;

import static java.util.Collections.singletonList;

@Service
public class DynamicMongoClientService {

    MongoProperties mongoProperties;
    MongoClient mongoClient;
    public MongoDatabaseFactory getDynamicMongoFactory(MongoProperties mongoProperties)
    {
        this.mongoProperties=mongoProperties;
        this.mongoClient=getDynamicMongoClient(this.mongoProperties);
        return new SimpleMongoClientDatabaseFactory(this.mongoClient,this.mongoProperties.getDatabase());
    }
    public MongoClient getDynamicMongoClient(MongoProperties mongoProperties) {

        MongoCredential credential = MongoCredential
                .createCredential(mongoProperties.getUsername(),
                        mongoProperties.getAuthenticationDatabase(),
                        mongoProperties.getPassword());

        return MongoClients.create(MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder
                        .hosts(singletonList(new ServerAddress(mongoProperties.getHost(),
                                mongoProperties.getPort()))))
                .credential(credential)
                .build());
    }

}
