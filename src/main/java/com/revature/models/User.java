package com.revature.models;

public abstract class User {
    
    private String user_name;
    private String password; // encrypted

    public User(String user_name, String password){
        this.user_name = user_name;
        this.password = encryptPassword(password);
    }

    private String encryptPassword(String pass){
        return pass; //TODO encrypt
    }

    public String getUserName(){
        return user_name;
    }

    public String getEncryptedPassword(){
        return password;
    }

}
