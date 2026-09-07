package com.vardhanreddy1706.URLEncoder.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vardhanreddy1706.URLEncoder.Models.User;

public interface UserRepository extends MongoRepository<User,String>{
    
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    
}
