package com.qantas.crm.model;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Document
@ApiModel(description="Address of a User")
public class Address {
	
	@ApiModelProperty(notes = "Address type, home or office")
    private String type;
	@ApiModelProperty(notes = "Detailed address")
    private String address;
	@ApiModelProperty(notes = "User phone number")
    private String phone;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
   

}
