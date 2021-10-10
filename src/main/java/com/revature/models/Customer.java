package com.revature.models;

import java.util.*;

public class Customer {
    
    private String first_name;
    private String last_name;
    private String user_name;
    private String password; // encrypted
    private HashMap<String, Account> accounts; // key is the name of the account

    public Customer(String first_name, String last_name, String user_name, String password){
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_name = user_name;
        this.password = encryptPassword(password);
        this.accounts = new HashMap<String, Account>();
    }

    private String encryptPassword(String password){
        return password; //TODO encrypt
    }

    public String getFirstName(){
        return first_name;
    }

    public String getLastName(){
        return last_name;
    }

    public String getUserName(){
        return user_name;
    }

    public String getEncryptedPassword(){
        return password;
    }

    public boolean applyForAccount(String type, String name){
        if(accounts.containsKey(name)){
            return false;
        }
        accounts.put(name, new Account(type, name));
        return true;
    }

    public Account getAccount(String name){
        return accounts.get(name); // returns null if key is not found
    }

}
