package com.revature.services;

import com.revature.daos.UserDao;
import com.revature.daos.UserDaoImpl;
import com.revature.models.*;
import java.util.Objects;

public class UserService {

    private UserDao userDao = new UserDaoImpl();
    
    public User login(String username, String password){
        User user = findByUserName(username);
        if(Objects.isNull(user)){
            System.out.println("No such user found. Please check you entered the username correctly.");
            return null;
        }
        else if(!encryptPassword(password).equals(user.getEncryptedPassword())){
            System.out.println("Password incorrect.");
            return null;
        }
        System.out.println("Login successful. Welcome, " + user.getFirstName() + ".");
        return user;
    }

    public Customer createNewUser(String username, String password, String firstName, String lastName){
        Customer customer = new Customer(firstName, lastName, username, encryptPassword(password));
        if(userDao.insertNewUser(customer)){
            return customer;
        }
        return null;
    }

    public String encryptPassword(String password){
        return "";
    }

    public User findByUserName(String username){
        return userDao.findByUserName(username);
    }

}
