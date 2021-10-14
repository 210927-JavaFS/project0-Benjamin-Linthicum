package com.revature.models;

import java.util.*;

public class Customer extends User{

    private HashMap<String, Account> accounts; // key is the name of the account

    public Customer(String first_name, String last_name, String user_name, String password){
        super(user_name, password, first_name, last_name);
        this.accounts = new HashMap<String, Account>();
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
