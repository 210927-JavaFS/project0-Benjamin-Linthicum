package com.revature.models;

public class Employee extends User{
    
    public Employee(String user_name, String password, String first_name, String last_name){
        super(user_name, password, first_name, last_name);
    }
    
    public String toString(){
    	return "First name: " + this.getFirstName() + ", Last name: " + this.getLastName() + ", Username: " + this.getUserName();
    }

}
