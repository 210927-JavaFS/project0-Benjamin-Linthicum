package com.revature.models;

public class Account {
    private String type; // "Spend" or "Growth", too lazy to do Enumeration and we haven't been taught that yet anyway
    private String name;
    private double balance;
    private boolean isApproved;

    public Account (String type, String name, double balance, boolean isApproved){
        this.type = type;
        this.name = name;
        this.balance = balance;
        this.isApproved = isApproved;
    }

    public Account (String type, String name){
        this.type = type;
        this.name = name;
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
        return "Name: " + name + ", Type: " + type + ", Balance: " + balance + (isApproved ? ", Approved" : ", Pending Approval");
    }

}
