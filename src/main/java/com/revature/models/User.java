package com.revature.models;

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
