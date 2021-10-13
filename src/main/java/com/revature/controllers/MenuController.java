package com.revature.controllers;

import java.util.Scanner;

public class MenuController {
    
    private static Scanner scan = new Scanner(System.in);
    private static AccountController accountController = new AccountController();
    private static UserController userController = new UserController();

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

    }

}
