package com.gs.controller;

import com.gs.schemas.global.entity.Info;
import com.gs.schemas.global.repo.InfoMongoRepository;
import com.gs.service.DocumentRetrieverService;
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
    @Autowired
    InfoMongoRepository infoMongoRepository;
    @Autowired
    DocumentRetrieverService documentRetrieverService;

    @GetMapping("/test")
    public void justWorkCustom() {
        log.info("Inside the Test Api");
        Info info=infoMongoRepository.findAll().get(0);
        log.info("info is:{}",info);
        MongoProperties mongoProperties=dynamicMongoClientService.getMongoPropertiesFromInfo(info);
        dynamicMongoClientService.setMongoProperties(mongoProperties);
        documentRetrieverService.getDocumentsFromCollections(dynamicMongoClientService.getMongoTemplate(),"temp_info");
    }


        /*db.grantRolesToUser(
   "root",
   [ "readWrite" ,{ role: "readWrite", db: "game" },
      { role: "readWrite", db: "global_games" },
      { role: "readWrite", db: "global" },
      { role: "readWrite", db: "temp" } ],
   { w: "majority" , wtimeout: 4000 }
)*/
//        [{
//            "_id": {
//                "$oid": "64f5a8df3a961905303f764b"
//            },
//            "auth_db": "admin",
//                    "database": "global_games",
//                    "host": "localhost",
//                    "password": "password",
//                    "port": 27017,
//                    "user": "root"
//        }]
}
