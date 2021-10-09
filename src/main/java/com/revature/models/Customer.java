package com.revature.models;

import java.util.*;

public class Customer {
    
    private String first_name;
    private String last_name;
    private String user_name;
    private String password; // encrypted
    private ArrayList<Account> accounts; //TODO make hashmap, hash on account name, enforce uniqueness

    public Customer(String first_name, String last_name, String user_name, String password){
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_name = user_name;
        this.password = encryptPassword(password);
        this.accounts = new ArrayList<Account>();
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

    public void applyForAccount(String type, String name, double balance){
        accounts.add(new Account(type, name, balance));
    }

    public Account getAccount(String name){
        return null; //TODO implement
    }

}
