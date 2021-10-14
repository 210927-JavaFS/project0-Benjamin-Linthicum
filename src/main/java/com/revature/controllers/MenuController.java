package com.revature.controllers;

import java.util.Scanner;
import java.util.Objects;
import com.revature.models.*;

public class MenuController {
    
    private static Scanner scan = new Scanner(System.in);
    private static AccountController accountController = new AccountController();
    private static UserController userController = new UserController();
    private User currentUser;

    public void welcomeMenu(){
        String response = "";
        System.out.println("Welcome to the bank. Please choose one of the options:\n"
            + "1: Login\n"
            + "2: Register");
        while(!response.equals("1") && !response.equals("2")){
            response = scan.nextLine();
            switch (response){
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

    private void loginMenu(){ //TODO
        while(true){
            System.out.println("Please input your username:");
            String username = scan.nextLine();
            System.out.println("Please input your password:");
            String password = scan.nextLine();
            if(userController.validateCredentials(username, password)){
                System.out.println("Login successful. Welcome.");
                break;
            }
            System.out.println("Login credentials invalid. Please try again.");

        }
    }

    private void registerMenu(){ //TODO
        while(true){
            System.out.println("Please input the username of your new account:");
            String username = scan.nextLine();
            if(!userController.isUsernameAvailable(username)){
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
            if(Objects.isNull(currentUser)){
                System.out.println("An unexpected error occured. Account creation failed.");
                continue;
            }
            System.out.println("Account creation successful!");
        }
    }

}
