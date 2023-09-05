package com.gs.schemas.global.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "info")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Info {

    @MongoId
    ObjectId id;
    @Field(name = "auth_db")
    String auth_db;
    @Field(name = "database")
    String database;
    @Field(name = "host")
    String host;
    @Field(name = "password")
    String password;
    @Field(name = "port")
    Integer port;
    @Field(name = "user")
    String user;
}