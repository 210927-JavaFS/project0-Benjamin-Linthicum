package com.revature.controllers;

import com.revature.services.UserService;
import com.revature.models.*;
import java.util.Objects;

public class UserController {

    private UserService userService = new UserService();
    
    public boolean validateCredentials(String username, String password){
        return userService.validateCredentials(username, password);
    }

    public boolean isUsernameAvailable(String username){
        return Objects.isNull(userService.findByUserName(username));
    }

    public User createNewUser(String username, String password, String firstName, String lastName){
        return userService.createNewUser(username, password, firstName, lastName);
    }

}
