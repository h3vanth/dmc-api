package io.bbw.dmc.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.bbw.dmc.pojo.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
