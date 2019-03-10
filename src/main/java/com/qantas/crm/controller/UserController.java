package com.qantas.crm.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import com.qantas.crm.exception.*;
import com.qantas.crm.model.User;
import com.qantas.crm.service.UserService;
import com.qantas.crm.validator.UserDataValidator;
import com.qantas.crm.dal.UserDAL;
import com.qantas.crm.dal.UserDALImpl;
import com.qantas.crm.dal.UserRepository;

@RestController
@RequestMapping(path = "/api/v1")
@Api(value = "User Management System", description = "Operations pertaining to user in Profile Management System")
public class UserController {

	// TODO Can be extended to use service class instead of calling repository
	// classs directly
	// @Autowired
	//UserRepository userRepository;

	// @Autowired
	//UserDALImpl userDAL;

	//@Autowired
	UserService userService;
	
	private UserDataValidator userDataValidator;

	@Autowired
	public UserController(UserService userService) {//UserRepository userRepository, UserDALImpl userDAL) {
		//this.userRepository = userRepository;
		//this.userDAL = userDAL;
		this.userService = userService;
	}

	@Autowired
	public void ValidationController(UserDataValidator userDataValidator) {
		this.userDataValidator = userDataValidator;
	}

	@ApiOperation(value = "Add an user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created user"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 409, message = "User already exists") })
	@PostMapping(path = "/users", produces = "application/json")
	public ResponseEntity<?> createUser(
			@ApiParam(value = "User object store in database table", required = true) @Valid @RequestBody User user,
			Errors errors) {
		userDataValidator.validate(user, errors);
		HttpHeaders headers = new HttpHeaders();
		if (errors.hasErrors()) {
			return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
		}
		ResponseEntity<?> resp = null;
		User existingUser = userService.getUserByEmail(user.getEmail());
		if (existingUser != null) {
			resp = new ResponseEntity<>(headers, HttpStatus.CONFLICT);
		} else {
			existingUser = userService.addUser(user);//userRepository.save(user)
			headers.add("Location", "/crm/api/v1/users/" + existingUser.getUserId());
			resp = new ResponseEntity<>(headers, HttpStatus.CREATED);
		}
		return resp;
	}

	@ApiOperation(value = "Get an user by Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved user"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 404, message = "Resource doesn't exist") })
	@GetMapping(path = "/users/{id}", produces = "application/json")
	public ResponseEntity<User> getUserById(
			@ApiParam(value = "User id from which user object will retrieve", required = true) @PathVariable(value = "id") String userId)
					throws ResourceNotFoundException,Exception {
		/*User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));*/
		User user = userService.getUserById(userId);
		return ResponseEntity.ok().body(user);
	}

	@ApiOperation(value = "View a list of available users", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved all users"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 404, message = "Resource doesn't exist") })
	@GetMapping(path = "/users", produces = "application/json")
	public List<User> getAllUsers() {
		// TODO need to introduce pagination
		//return userRepository.findAll();
		return userService.getAllUsers();
	}

	@ApiOperation(value = "Update an user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated user"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 404, message = "Resource doesn't exist") })
	@PutMapping(path = "/users/{id}", produces = "application/json")
	public ResponseEntity<User> updateUser(
			@ApiParam(value = "User Id to update user object", required = true) @PathVariable(value = "id") String userId,
			@ApiParam(value = "Update user object", required = true) @Valid @RequestBody User userDetails)
					throws ResourceNotFoundException,Exception {
		/*User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		userDetails.setUserId(userId);
		final User updatedUser = userRepository.save(userDetails);*/
		final User updatedUser = userService.updateUser(userId, userDetails);
		return ResponseEntity.ok(updatedUser);
	}

	@ApiOperation(value = "Delete an user")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Successfully deleted user"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 404, message = "Resource doesn't exist") })
	@DeleteMapping(path = "/users/{id}")
	public ResponseEntity<?> deleteUser(
			@ApiParam(value = "User Id from which user object will delete from database table", required = true) @PathVariable(value = "id") String userId)
					throws ResourceNotFoundException,Exception{
		/*User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

		userRepository.delete(user);*/
		userService.deleteUser(userId);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
	}

}
