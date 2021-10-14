package com.revature.models;

import java.util.*;

public class Customer extends User{

    private ArrayList<Account> accounts; // key is the name of the account

    public Customer(String first_name, String last_name, String user_name, String password){
        super(user_name, password, first_name, last_name);
        this.accounts = new ArrayList<Account>();
    }

    public boolean applyForAccount(String type, String name){
        accounts.add(new Account(type, name));
        return true;
    }

    @Override
    public void addAccount(Account a){
        accounts.add(a);
    }

    @Override
    public ArrayList<Account> getAccounts(){
        return accounts;
    }

    public Account getAccount(String name){
        for(Account a: accounts){
            if(a.getName().equals(name)){
                return a;
            }
        }
        return null;
    }

    public boolean transferFunds(String fromName, String targetName, double amount){
        return getAccount(fromName).transferTo(getAccount(targetName), amount);
    }

}
