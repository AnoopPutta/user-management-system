package com.qantas.crm.dal;

import java.util.List;

import com.qantas.crm.model.User;

public interface UserDAL {
	
	User getUserByEmail(String email);
	
	
	/*
	List<User> getAllUsers();

	User getUserById(String userId);

	User addNewUser(User user);
	
	Object getAllUserSettings(String userId);

	String getUserSetting(String userId, String key);

	String addUserSetting(String userId, String key, String value);*/
}