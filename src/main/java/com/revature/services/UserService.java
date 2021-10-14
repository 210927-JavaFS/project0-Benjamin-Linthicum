package com.revature.services;

import com.revature.daos.UserDao;
import com.revature.daos.UserDaoImpl;
import com.revature.models.*;

public class UserService {

    private UserDao userDao = new UserDaoImpl();
    
    public boolean validateCredentials(String username, String password){
        return true;
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

}
