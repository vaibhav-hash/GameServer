package com.gs.service;

import com.gs.schemas.global.entity.Info;
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

    public DynamicMongoClientService() {
    }

    public MongoProperties getMongoPropertiesFromInfo(Info info) {
        try {
            if (info == null) {
                log.error("Info Object null so mongoTemplate null:{}", info);
                return null;
            }
            MongoProperties mongoProperties = new MongoProperties();
            mongoProperties.setUsername(info.getUser());
            mongoProperties.setPassword(info.getPassword().toCharArray());
            mongoProperties.setHost(info.getHost());
            mongoProperties.setDatabase(info.getDatabase());
            mongoProperties.setAuthenticationDatabase(info.getAuth_db());
            mongoProperties.setPort(info.getPort());
            return mongoProperties;
        } catch (Exception e) {
            log.error("Error in MongoPropertiese setter from info");
            e.printStackTrace();
        }
        return null;
    }
    public MongoTemplate getMongoTemplateFromMongoProperties(MongoProperties mongoProperties)
    {
        MongoClient mongoClient=generateDynamicMongoClient(mongoProperties);
        MongoDatabaseFactory mongoDatabaseFactory=generateDynamicMongoFactory(mongoClient,mongoProperties);
        MongoTemplate mongoTemplate=generateDynamicMongoTemplate(mongoDatabaseFactory);
        return mongoTemplate;
    }
    public MongoClient generateDynamicMongoClient(MongoProperties mongoProperties) {
        if(mongoProperties==null)
            log.error("Please set MongoProperties");
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

    public MongoDatabaseFactory generateDynamicMongoFactory(MongoClient mongoClient,MongoProperties mongoProperties) {
        if(mongoClient==null)
            log.error("Please generate mongoClient");
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }

    public MongoTemplate generateDynamicMongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) {
            if(mongoDatabaseFactory==null)
                log.error("Pleas generate MongoDb factory or call setMongoproperties function");
        return new MongoTemplate(mongoDatabaseFactory);
    }
}
