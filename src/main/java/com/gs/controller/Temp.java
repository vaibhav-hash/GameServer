package com.gs.controller;

import com.gs.service.DynamicMongoClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class Temp {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    DynamicMongoClientService dynamicMongoClientService;
    @GetMapping("/test")
    public void justWorkCustom()
    {
        log.info("Inside the Test Api");
        MongoProperties mongoProperties = new MongoProperties();
        mongoProperties.setHost("localhost");
        mongoProperties.setPort(27017);
        mongoProperties.setPassword("password".toCharArray());
        mongoProperties.setUsername("root");
        mongoProperties.setDatabase("global");
        mongoProperties.setAuthenticationDatabase("admin");
        MongoDatabaseFactory mongoDatabaseFactory=dynamicMongoClientService.getDynamicMongoFactory(mongoProperties);
        log.info("Collection names in mongo db:{}",mongoDatabaseFactory.getMongoDatabase().listCollectionNames());
    }
}
