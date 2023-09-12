package com.juanfredes.projectbackend.repositories;

import com.juanfredes.projectbackend.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
