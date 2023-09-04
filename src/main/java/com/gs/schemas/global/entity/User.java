package com.gs.schemas.global.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "user")
public class User {

    @MongoId
    private ObjectId id;

    private String name;

    private String surname;
    private String email;

    private int age;

    // getters and setters
}
