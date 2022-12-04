package com.Spring.Security.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usermodel")
public class UserModel {
	
	@Id
	private String id;
	
	private String username;
	
	@JsonIgnore
	private String password;
	private List<String> roles;
	
	
	
	
	
	
	

}
