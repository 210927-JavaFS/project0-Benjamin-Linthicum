package com.revature.models;
import java.util.*;

public abstract class User {
    
    private String user_name;
    private String password; // encrypted
    private String first_name;
    private String last_name;

    public User(String user_name, String password, String first_name, String last_name){
        this.user_name = user_name;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public void addAccount(Account a){
        return;
    }
    
    public void applyForAccount(String name, String type){
        return;
    }

    public ArrayList<Account> getAccounts(){
        return null;
    }

    public String getUserName(){
        return user_name;
    }

    public String getEncryptedPassword(){
        return password;
    }

    public String getFirstName(){
        return first_name;
    }

    public String getLastName(){
        return last_name;
    }

}
