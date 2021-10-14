package com.revature.controllers;

import com.revature.services.UserService;
import com.revature.models.*;
import java.util.Objects;

public class UserController {

    private UserService userService = new UserService();
    
    public User login(String username, String password){
        return userService.login(username, password);
    }

    public boolean isUsernameAvailable(String username){
        return Objects.isNull(userService.findByUserName(username));
    }

    public User createNewUser(String username, String password, String firstName, String lastName){
        return userService.createNewUser(username, password, firstName, lastName);
    }

    public void listAccounts(User user){
        userService.listAccounts(user);
    }

    public boolean applyForAccount(String accountName, String type, User user){
        return userService.applyForAccount(accountName, type, user);
    }

    public boolean deposit(String username, String accountName, double amount){
        return userService.deposit(username, accountName, amount);
    }

    public boolean withdraw(String username, String accountName, double amount){
        return userService.withdraw(username, accountName, amount);
    }

}
