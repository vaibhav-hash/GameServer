package com.gs.schemas.global.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("info")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Info {

        @MongoId
        private ObjectId id;

        private String first;
        private String second;
        private String third;
        
        public Info(ObjectId id, String first, String second, String third) {
            super();
            this.id = id;
            this.first = first;
            this.second = second;
            this.third = third;
        }
}