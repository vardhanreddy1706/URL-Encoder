package com.vardhanreddy1706.URLEncoder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vardhanreddy1706.URLEncoder.Models.Shorturl;
import com.vardhanreddy1706.URLEncoder.Models.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.InputStream;
import java.util.List;

@Configuration
public class dbConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Register this module so Jackson can parse the "createdAt" timestamps
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public CommandLineRunner initDatabase(MongoTemplate mongoTemplate, ObjectMapper objectMapper) {
        return args -> {
            // 1. Initialize Users Collection & Data
            if (!mongoTemplate.collectionExists(User.class)) {
                // This creates the collection based on your @Document class
                mongoTemplate.createCollection(User.class);

                // Read data from the db folder
                InputStream usersStream = new ClassPathResource("db/users.json").getInputStream();
                List<User> users = objectMapper.readValue(usersStream, new TypeReference<List<User>>(){});

                // Insert into MongoDB
                mongoTemplate.insertAll(users);
                System.out.println("✅ Successfully created Users collection and loaded JSON data.");
            }

            // 2. Initialize Short URLs Collection & Data
            if (!mongoTemplate.collectionExists(Shorturl.class)) {
                mongoTemplate.createCollection(Shorturl.class);

                InputStream urlsStream = new ClassPathResource("db/url.json").getInputStream();
                List<Shorturl> urls = objectMapper.readValue(urlsStream, new TypeReference<List<Shorturl>>(){});

                mongoTemplate.insertAll(urls);
                System.out.println("✅ Successfully created ShortUrls collection and loaded JSON data.");
            }
        };
    }
}