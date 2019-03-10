package com.qantas.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qantas.crm.dal.UserDALImpl;
import com.qantas.crm.dal.UserRepository;
import com.qantas.crm.exception.ResourceNotFoundException;
import com.qantas.crm.model.User;

@Service
public class UserService {
	
	UserRepository userRepository;

	UserDALImpl userDAL;
	
	@Autowired
	public UserService(UserRepository userRepository, UserDALImpl userDAL) {
		this.userRepository = userRepository;
		this.userDAL = userDAL;
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public void deleteUser(String userId) throws ResourceNotFoundException,Exception {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

		userRepository.delete(user);
	}

	public User updateUser(String userId, User userDetails) throws ResourceNotFoundException,Exception{
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		userDetails.setUserId(userId);
		User updatedUser = userRepository.save(userDetails);
		return updatedUser;
	}
	
	public User getUserById(String userId) throws ResourceNotFoundException,Exception {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		return user;
	}
	
	public User getUserByEmail(String email) { 
		return userDAL.getUserByEmail(email);
	}
	
	public User addUser(User user) {
		return userRepository.save(user);
	}
}
