package com.revature.services;

import com.revature.daos.UserDao;
import com.revature.daos.UserDaoImpl;
import com.revature.models.*;
import java.util.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService {

    private UserDao userDao = new UserDaoImpl();
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    public User login(String username, String password){
        User user = findByUserName(username);
        if(Objects.isNull(user)){
            System.out.println("No such user found. Please check that you entered the username correctly.");
            return null;
        }
        else if(encoder.matches(password, user.getEncryptedPassword())){
            System.out.println("Password incorrect.");
            return null;
        }
        if(user instanceof Customer){
            ArrayList<Account> accounts = userDao.findAccountsByUser(username);
            for(Account a: accounts){
                user.addAccount(a);
            }
        }
        System.out.println("Login successful. Welcome, " + user.getFirstName() + ".");
        return user;
    }

    public Customer createNewUser(String username, String password, String firstName, String lastName){
    	encoder.encode(password);
        Customer customer = new Customer(firstName, lastName, username, password);
        if(userDao.insertNewUser(customer)){
            return customer;
        }
        return null;
    }

    public User findByUserName(String username){
        return userDao.findByUserName(username);
    }

    public void listAccounts(User user){
        ArrayList<Account> accounts = user.getAccounts();
        for(Account a: accounts){
            System.out.println(a);
        }
    }

    public boolean applyForAccount(String accountName, String type, User user){
        return userDao.addAccount(accountName, type, user);
    }

    public boolean deposit(String username, String accountName, double amount){
        return userDao.deposit(username, accountName, amount);
    }

    public boolean withdraw(String username, String accountName, double amount){
        return userDao.withdraw(username, accountName, amount);
    }

    public boolean transfer(String username, String fromName, String toName, double amount){
        return userDao.transfer(username, fromName, toName, amount);
    }

}
