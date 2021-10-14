package com.revature.controllers;

import java.util.Scanner;

import javax.lang.model.util.ElementScanner14;

import java.util.Objects;
import com.revature.models.*;

public class MenuController {

    private static Scanner scan = new Scanner(System.in);
    private static AccountController accountController = new AccountController();
    private static UserController userController = new UserController();
    private User currentUser;

    public void welcomeMenu() {
        while (true) {
            if(!Objects.isNull(currentUser)){
                if(currentUser instanceof Customer)
                    CustomerMenu();
                else if(currentUser instanceof Employee)
                    employeeMenu();
                else
                    adminMenu();
            }
            String response = "";
            System.out.println("Welcome to the bank. Please choose one of the options:\n" + "1: Login\n" + "2: Register");
            while (!response.equals("1") && !response.equals("2")) {
                response = scan.nextLine();
                switch (response) {
                    case "1":
                        loginMenu();
                        break;
                    case "2":
                        registerMenu();
                        break;
                    default:
                        System.out.println("Invalid input. Please input \"1\" to login or \"2\" to register.");
                }
            }
        }
    }

    private void CustomerMenu(){
        System.out.println("Enter \"apply\" to apply for a new account.");
        System.out.println("Enter \"list\" to obtain a list of your account information.");
        System.out.println("Enter \"deposit\" to deposit into an account.");
        System.out.println("Enter \"withdraw\" to withdraw from an account.");
        System.out.println("Enter \"transfer\" to transfer money between accounts.");
        System.out.println("Enter \"logout\" to logout.");
        String response = "";
        boolean badResponse;
        do{
            badResponse = false;
            String accountName = "";
            boolean accountFound = false;
            double amount = 0.0;
            switch (response) {
                case "apply":
                    System.out.println("What would you like to name your account?");
                    String name = "";
                    while(true){
                        name = scan.nextLine();
                        for(Account a: currentUser.getAccounts()){
                            if(name.equals(a.getName())){
                                System.out.println("Account with this name already exists. Please enter a different name.");
                                continue;
                            }
                        }
                        break;
                    }
                    System.out.println("What type of account will it be? Either \"checkings\", \"savings\", or \"spend\".");
                    String accountType = "";
                    int attempts = 0;
                    while(true){
                        accountType = scan.nextLine();
                        if(accountType.equals("I made you")){
                            System.out.println("I see no god here but me.");
                            continue;
                        }
                        if(accountType.equals("checkings") || accountType.equals("savings") || accountType.equals("spend")){
                            break;
                        }
                        System.out.println("Mispelled response. Please try again.");
                        if(attempts >= 2){
                            System.out.println("If you continue to have trouble, please reference a dictionary or your local second grade English teacher.");
                        }
                        attempts++;
                    }
                    if(userController.applyForAccount(name, accountType, currentUser)){
                        System.out.println("Account application successfully submitted.");
                    }
                    else{
                        System.out.println("An unexpected error occured. Account creation failed.");
                    }
                    break;

                case "list":
                    userController.listAccounts(currentUser);
                    break;

                case "deposit":
                    System.out.println("What is the name of the account into which you would like to make a deposit?");
                    userController.listAccounts(currentUser);

                    while(!accountFound){
                        accountName = scan.nextLine();
                        for(Account a: currentUser.getAccounts()){
                            if(accountName.equals(a.getName())){
                                accountFound = true;
                                break;
                            }
                        }
                        System.out.println("No such account with that name. Please try again.");
                    }

                    System.out.println("How much would you like to deposit?");
                    while(true){
                        if(scan.hasNextDouble()){ // Potential bug, TEST THIS
                            amount = scan.nextDouble();
                            if(amount > 0.0){
                                break;
                            }
                        }
                        System.out.println("Invalid input. Please try again.");
                    }
                    if(userController.deposit(currentUser.getUserName(), accountName, amount)){
                        System.out.println("Deposit successful. New balance:\n" + currentUser.getAccount(accountName).getName());
                    }
                    else{
                        System.out.println("Deposit failed.");
                    }
                    
                    break;

                case "withdraw":
                    System.out.println("What is the name of the account from which you would like to withdraw?");
                    userController.listAccounts(currentUser);
                    double withdrawAccountBalance = 0.0; // the current balance of the account being targetted for withdrawal

                    while(!accountFound){
                        accountName = scan.nextLine();
                        for(Account a: currentUser.getAccounts()){
                            if(accountName.equals(a.getName())){
                                accountFound = true;
                                withdrawAccountBalance = a.getBalance();
                                System.out.println("Current balance: " + a.getBalance());
                                break;
                            }
                        }
                        System.out.println("No such account with that name. Please try again.");
                    }

                    System.out.println("How much would you like to withdraw?");
                    while(true){
                        if(scan.hasNextDouble()){ // Potential bug, TEST THIS
                            amount = scan.nextDouble();
                            if(amount > withdrawAccountBalance){
                                System.out.println("This amount exceeds your current balance. Please enter a lower number.");
                                continue;
                            }
                            if(amount >= 0.0){
                                break;
                            }
                        }
                        System.out.println("Invalid input. Please try again.");
                    }

                    if(userController.withdraw(currentUser.getUserName(), accountName, amount)){
                        System.out.println("Withdrawal successful. New balance:\n" + currentUser.getAccount(accountName).getName());
                    }
                    else{
                        System.out.println("Withdraw failed.");
                    }

                    break;

                case "transfer":
                    break;

                case "logout":
                    return;

                default:
                    System.out.println("Unrecognized response. Please check your spelling.");
                    badResponse = true;
            }
            accountFound = false;
        } while(badResponse);
    }

    private void employeeMenu(){
        //TODO
    }

    private void adminMenu(){
        //TODO
    }

    private void loginMenu() {
        while (true) {
            System.out.println("Please input your username:");
            String username = scan.nextLine();
            System.out.println("Please input your password:");
            String password = scan.nextLine();
            currentUser = userController.login(username, password);
            if (!Objects.isNull(currentUser)) {
                System.out.println("Login successful. Welcome.");
                break;
            }
            System.out.println("Type \"1\" to try again, and anything else to return to the menu.");
            if (scan.nextLine().equals("1")) {
                continue;
            }
            break;
        }
    }

    private void registerMenu() {
        while (true) {
            System.out.println("Please input the username of your new account:");
            String username = scan.nextLine();
            if (!userController.isUsernameAvailable(username)) {
                System.out.println("Username is already taken.");
                continue;
            }
            System.out.println("Please input the password of your new account:");
            String password = scan.nextLine();
            System.out.println("Please input your first name:");
            String firstName = scan.nextLine();
            System.out.println("Please input your last name:");
            String lastName = scan.nextLine();
            currentUser = userController.createNewUser(username, password, firstName, lastName);
            if (Objects.isNull(currentUser)) {
                System.out.println("An unexpected error occured. Account creation failed.");
                continue;
            }
            System.out.println("Account creation successful!");
        }
    }

}
