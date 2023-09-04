package com.gs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class Temp {
    @Autowired
    MongoTemplate mongoTemplate;
}
