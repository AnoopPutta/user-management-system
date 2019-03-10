package com.qantas.crm.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.qantas.crm.model.User;

@Component
public class UserDataValidator {

    public void validate(User user, Errors errors) {
       
            if (user.getEmail() == null || user.getEmail().isEmpty() ) {
                errors.reject("email cannot be empty");
                
            }
       
            if (user.getfirstName() == null || user.getfirstName().isEmpty() ) {
                errors.reject("first name cannot be empty");
            }
            
            if (user.getLastName() ==null || user.getLastName().isEmpty() ) {
                errors.reject("last name cannot be empty");
            }
    }
}