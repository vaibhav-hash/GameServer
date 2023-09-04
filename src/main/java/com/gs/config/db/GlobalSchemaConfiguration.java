package com.gs.config.db;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.gs.schemas.global", mongoTemplateRef = "mongoTemplateGlobal")
@ConfigurationProperties(prefix = "spring.datasource.global")
public class GlobalSchemaConfiguration {
    MongoProperties mongoProperties;
    public void setMongoProperties()
    {
    }

}
