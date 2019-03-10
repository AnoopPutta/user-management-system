package com.qantas.crm.model;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Document
@ApiModel(description="All details about the User. ")
public class User {

	
	@Id
	@ApiModelProperty(hidden=true)
	private String userId;
	@ApiModelProperty(notes = "The user first name")
	@NotNull(message = "User's first name must not be null")
	private String firstName;
	@ApiModelProperty(notes = "The user last name")
	@NotNull(message = "User's last name must not be null")
	private String lastName;
	@ApiModelProperty(notes = "Email Id of the user")
	@NotNull(message = "User's email must not be null")
	private String email;
	
	@ApiModelProperty(notes = "Addresses of the user")
	private Set<Address> addresses = new HashSet<Address>();
	
	@ApiModelProperty(notes = "Additional details of a user")
	private Map<String, String> additionalDetails = new HashMap<>();
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getfirstName() {
		return firstName;
	}

	public void setfirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}
	
	public Map<String, String> getAdditionalDetails() {
		return additionalDetails;
	}

	public void setAdditionalDetails(Map<String, String> additionalDetails) {
		this.additionalDetails = additionalDetails;
	}
}
