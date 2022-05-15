package com.app.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

public class SimpleMongoConfig {

//    @Bean
//    public MongoClient mongo() {
//        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/data_base");
//        MongoClientSettings mongoClientSettings =
//                MongoClientSettings.builder().applyConnectionString(connectionString).build();
//        return MongoClients.create(mongoClientSettings);
//    }
//
//    @Bean
//    public MongoTemplate mongoTemplate(MongoClient mongoClient) throws Exception {
//        return new MongoTemplate(mongoClient, "data_base");
//    }

}
