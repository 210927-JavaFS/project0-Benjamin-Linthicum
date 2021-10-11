package com.revature.models;

import java.util.*;

public class Customer extends User{
    
    private String first_name;
    private String last_name;
    private HashMap<String, Account> accounts; // key is the name of the account

    public Customer(String first_name, String last_name, String user_name, String password){
        super(user_name, password);
        this.first_name = first_name;
        this.last_name = last_name;
        this.accounts = new HashMap<String, Account>();
    }

    public String getFirstName(){
        return first_name;
    }

    public String getLastName(){
        return last_name;
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

    public boolean transferFunds(String fromName, String targetName, double amount){
        return getAccount(fromName).transferTo(getAccount(targetName), amount);
    }

}
