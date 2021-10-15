package com.revature.models;

public class Account {
    private String type; // "Spend" or "Growth", too lazy to do Enumeration and we haven't been taught that yet anyway
    private String name;
    private double balance;
    private boolean isApproved;
    private String username; //username of owner

    public Account (String type, String name, double balance, boolean isApproved, String username){
        this.type = type;
        this.name = name;
        this.balance = balance;
        this.isApproved = isApproved;
        this.username = username;
    }

    public Account (String type, String name, String username){
        this.type = type;
        this.name = name;
        this.username = username;
        this.balance = 0;
        this.isApproved = false;
    }

    public double getBalance(){
        return balance;
    }

    public boolean getIsApproved(){
        return isApproved;
    }

    public String getType(){
        return type;
    }

    public String getName(){
        return name;
    }
    
    public String getUsername() {
    	return username;
    }

    public void approve(){
        isApproved = true;
    }

    public boolean withdraw(double amount){
        if(balance >= amount){
            balance -= amount;
            return true;
        }
        return false;
    }

    public void deposit(double amount){
        balance += amount;
    }

    public boolean transferTo(Account target, double amount){
        if(this.withdraw(amount)){
            target.deposit(amount);
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
    	String result = "Name: " + name + ", Type: " + type + ", Balance: " + balance;
    	result += (isApproved ? ", Approved" : ", Pending Approval");
    	result += ", Owner: " + username;
    	return result;
    }

}
