/*
package com.example.planit.repository;

import com.example.planit.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsernameAndPassword(String username, String password);
}
*/

package com.example.planit.repository;

import com.example.planit.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface UserRepository extends MongoRepository<User, String> {
    @Async
    CompletableFuture<User> findByUsernameAndPassword(String username, String password);
}

