package com.revature.models;

public class Account {
    private String type; // "Spend" or "Growth", too lazy to do Enumeration and we haven't been taught that yet anyway
    private double balance;

    public Account (String type, double balance){
        this.type = type;
        this.balance = balance;
    }

    public double getBalance(){
        return balance;
    }

    public String getType(){
        return type;
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

}
