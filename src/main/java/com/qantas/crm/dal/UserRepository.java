package com.qantas.crm.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.qantas.crm.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
