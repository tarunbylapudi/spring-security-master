package com.Spring.Security.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Spring.Security.entity.UserModel;

@Repository
public interface UserRepository extends MongoRepository< UserModel, String> {

	List<UserModel> findByUsername(String username);
	
}
