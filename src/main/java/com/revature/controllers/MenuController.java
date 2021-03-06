package com.revature.controllers;

import java.util.*;

import javax.lang.model.util.ElementScanner14;

import java.util.Objects;
import com.revature.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class MenuController {

    private static Scanner scan = new Scanner(System.in);
    private static Logger log = LoggerFactory.getLogger(MenuController.class);
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
            System.out.println("Welcome to the bank. Please choose one of the options:\n\"Login\"\n\"Register\"");
            while (!response.equals("Login") && !response.equals("Register")) {
                response = scan.nextLine();
                switch (response) {
                    case "Login":
                        loginMenu();
                        break;
                    case "Register":
                        registerMenu();
                        break;
                    default:
                        System.out.println("Invalid input. Please input \"Login\" to login or \"Register\" to register.");
                }
            }
        }
    }

    private void CustomerMenu(){
        String response = "";
        boolean accountFound = false;
        boolean justTransfered = false; //True if deposit, withdraw or transfer were called. Intended to fix a printing error.
        boolean accountApproved = true;
        while(true) {
        	if(!justTransfered) {
        		System.out.println("\nEnter \"apply\" to apply for a new account.");
            	System.out.println("Enter \"list\" to obtain a list of your account information.");
            	System.out.println("Enter \"deposit\" to deposit into an account.");
            	System.out.println("Enter \"withdraw\" to withdraw from an account.");
            	System.out.println("Enter \"transfer\" to transfer money between accounts.");
            	System.out.println("Enter \"logout\" to logout.\n");
        	}
            String accountName = "";
            accountFound = false;
            double amount = 0.0;
            double withdrawAccountBalance = 0.0; // the current balance of the account being targetted for withdrawal
            response = scan.nextLine();
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
                    System.out.println("What type of account will it be? Either \"reserve\", \"savings\", or \"spend\".");
                    String accountType = "";
                    int attempts = 0;
                    while(true){
                        accountType = scan.nextLine();
                        if(accountType.equals("I made you")){
                            System.out.println("\nI SEE NO GOD HERE BUT ME.");
                            continue;
                        }
                        if(accountType.equals("reserve") || accountType.equals("savings") || accountType.equals("spend")){
                            break;
                        }
                        System.out.println("\nMispelled response. Please try again.");
                        if(attempts >= 2){
                            System.out.println("If you continue to have trouble, please reference a dictionary or your local second grade English teacher.");
                        }
                        attempts++;
                    }
                    if(userController.applyForAccount(name, accountType, currentUser)){
                        System.out.println("\nAccount application successfully submitted.");
                    }
                    else{
                        System.out.println("\nAn unexpected error occured. Account creation failed.");
                    }
                    break;

                case "list":
                    userController.listAccounts(currentUser);
                    break;

                case "deposit":
                    if(currentUser.getApprovedAccountCount() < 1){
                        System.out.println("\nYou have no approved accounts. Unable to perform a deposit.");
                        break;
                    }
                    userController.listAccounts(currentUser);
                    System.out.println("\nWhat is the name of the account into which you would like to make a deposit?");

                    while(!accountFound){
                    	accountApproved = true;
                        accountName = scan.nextLine();
                        for(Account a: currentUser.getAccounts()){
                            if(accountName.equals(a.getName())){
                            	if(!a.getIsApproved()) {
                            		System.out.println("Account not yet approved. Please select another account.");
                            		accountApproved = false;
                            		break;
                            	}
                                accountFound = true;
                                break;
                            }
                        }
                        if(!accountFound && accountApproved) {
                        	System.out.println("\nNo such account with that name. Please try again.");
                        }
                    }

                    System.out.println("How much would you like to deposit?");
                    while(true){
                    	try {
                            amount = scan.nextDouble();
                            if(amount > 0.0){
                                break;
                            }
                    	}
                    	catch (InputMismatchException e) {}
                        System.out.println("\nInvalid input. Please try again.");
                    }
                    if(userController.deposit(currentUser.getUserName(), accountName, amount)){
                        currentUser.getAccount(accountName).deposit(amount);
                        System.out.println("\nDeposit successful. New balance:\n" + currentUser.getAccount(accountName).getBalance());
                    }
                    else{
                        System.out.println("\nDeposit failed.");
                    }
                    
                    justTransfered = true;
                    
                    break;

                case "withdraw":
                    if(currentUser.getApprovedAccountCount() < 1){
                        System.out.println("\nYou have no approved accounts. Unable to perform a withdrawal.");
                        break;
                    }
                    userController.listAccounts(currentUser);
                    System.out.println("\nWhat is the name of the account from which you would like to withdraw?");

                    while(!accountFound){
                    	accountApproved = true;
                        accountName = scan.nextLine();
                        for(Account a: currentUser.getAccounts()){
                            if(accountName.equals(a.getName())){
                            	if(!a.getIsApproved()) {
                            		System.out.println("Account not yet approved. Please select another account.");
                            		accountApproved = false;
                            		break;
                            	}
                                accountFound = true;
                                withdrawAccountBalance = a.getBalance();
                                System.out.println("Current balance: " + a.getBalance());
                                break;
                            }
                        }
                        if(!accountFound && accountApproved) {
                        	System.out.println("\nNo such account with that name. Please try again.");
                        }
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
                        System.out.println("\nInvalid input. Please try again.");
                    }

                    if(userController.withdraw(currentUser.getUserName(), accountName, amount)){
                        currentUser.getAccount(accountName).withdraw(amount);
                        System.out.println("\nWithdrawal successful. New balance:\n" + currentUser.getAccount(accountName).getBalance());
                    }
                    else{
                        System.out.println("\nWithdraw failed.");
                    }
                    
                    justTransfered = true;

                    break;

                case "transfer":
                    if(currentUser.getApprovedAccountCount() < 2){
                        System.out.println("\nYou must have atleast two approved accounts in order to perform a transfer.");
                        break;
                    }
                    String targetAccountName = ""; // The account to be transferred to
                    userController.listAccounts(currentUser);
                    System.out.println("\nEnter the name of the account to withdraw from in this transfer.");

                    while(!accountFound){
                    	accountApproved = true;
                        accountName = scan.nextLine();
                        for(Account a: currentUser.getAccounts()){
                            if(accountName.equals(a.getName())){
                            	if(!a.getIsApproved()) {
                            		System.out.println("Account not yet approved. Please select another account.");
                            		accountApproved = false;
                            		break;
                            	}
                                withdrawAccountBalance = a.getBalance();
                                accountFound = true;
                                break;
                            }
                        }
                        if(!accountFound && accountApproved) {
                        	System.out.println("\nNo such account with that name. Please try again.");
                        }
                    }
                    System.out.println("\nEnter the name of the account to deposit to in this transfer.");
                    accountFound = false;
                    while(!accountFound){
                    	accountApproved = true;
                        targetAccountName = scan.nextLine();
                        for(Account a: currentUser.getAccounts()){
                            if(targetAccountName.equals(a.getName())){
                                if(targetAccountName.equals(accountName)){
                                    System.out.println("\nThey must be different accounts. Please enter a different account to transfer to.");
                                }
                                else if(!a.getIsApproved()) {
                            		System.out.println("Account not yet approved. Please select another account.");
                            		accountApproved = false;
                            	}
                                else {
                                	accountFound = true;
                                }
                                break;
                            }
                        }
                        if(!targetAccountName.equals(accountName) && !accountFound && accountApproved){
                            System.out.println("\nNo such account with that name. Please try again.");
                        }
                    }

                    System.out.println("\nHow much would you like to transfer?");
                    while(true){
                        if(scan.hasNextDouble()){ // Potential bug, TEST THIS
                            amount = scan.nextDouble();
                            if(amount > withdrawAccountBalance){
                                System.out.println("\nThis amount exceeds the balance of your account. Please enter a lower number.");
                                continue;
                            }
                            if(amount >= 0.0){
                                break;
                            }
                        }
                        System.out.println("\nInvalid input. Please try again.");
                    }

                    if(userController.transfer(currentUser.getUserName(), accountName, targetAccountName, amount)){
                        currentUser.transferFunds(accountName, targetAccountName, amount);
                        System.out.println("\nTransfer successful. New balances:");
                        System.out.println(currentUser.getAccount(accountName).getName() + ": " + currentUser.getAccount(accountName).getBalance());
                        System.out.println(currentUser.getAccount(targetAccountName).getName() + ": " + currentUser.getAccount(targetAccountName).getBalance());
                    }
                    else {
                    	System.out.println("\nTransfer failed. An unexpected error occured.");
                    }
                    
                    justTransfered = true;

                    break;

                case "logout":
                    return;

                default:
                	if(!justTransfered) {
                		System.out.println("\nUnrecognized response. Please check your spelling.");
                	}
                	justTransfered = false;
                    break;
            }
            accountFound = false;
        }
    }

    private void employeeMenu(){
    	while(true) {
    		System.out.println("\nEnter \"list accounts\" to list all accounts.");
    		System.out.println("Enter \"screen accounts\" to approve/deny pending accounts.");
    		System.out.println("Enter \"list customers\" to list all customers.");
    		System.out.println("Enter \"logout\" to logout.\n");
    		String response = scan.nextLine();
    		switch(response) {
        		case "list accounts":
        			accountController.listAllAccounts();
        			break;
        		
        		case "screen accounts":
        			ArrayList<Account> accounts = accountController.getAllUnapprovedAccounts();
        			if(accounts.isEmpty()) {
        				System.out.println("\nThere are no pending accounts.");
        				break;
        			}
        			for(Account a: accounts) {
        				System.out.println(a);
        			}
        			String username = "";
        			String accountName = "";
        			boolean notFound = true;
        			Account defendent = null;
        			while(notFound) {
        				System.out.println("\nEnter the username of the owner of the account to judge:");
            			username = scan.nextLine();
            			System.out.println("Enter the name of the account to judge:");
            			accountName = scan.nextLine();
        				for(Account a: accounts) {
        					if(username.equals(a.getUsername()) && accountName.equals(a.getName())){
        						defendent = a;
        						notFound = false;
        						break;
        					}
        				}
        				if(notFound) {
        					System.out.println("\nNo such pending account exists.\n");
        					userController.incrementMispelling(currentUser.getUserName());
        				}
        			}
        			System.out.println("Enter \"approve\" or \"deny\":");
        			while(true) {
        				response = scan.nextLine();
        				if(response.equals("approve")) {
        					if(accountController.approveAccount(defendent)) {
        						System.out.println("\nAccount approved successfully.");
        						defendent.approve();
        					}
        					else {
        						System.out.println("\nAn error occured while attempting to approve account.");
        					}
        					break;
        				}
        				else if(response.equals("deny")) {
        					if(accountController.denyAccount(defendent)) {
        						System.out.println("\nAccount denied successfully, I hope you're happy.");
        					}
        					else {
        						System.out.println("\nAn error occured while attempting to deny account.");
        					}
        					break;
        				}
        				else {
        					System.out.println("Please enter a valid response.");
        					userController.incrementMispelling(currentUser.getUserName());
        				}
        			}
        			break;
        		
        		case "list customers":
        			if(!userController.listCustomers()) {
        				System.out.println("An unexpected error occured.");
        			}
        			break;
        		
        		case "logout":
        			return;
        		
        		default:
        			System.out.println("Invalid input. As you spell your next entry, please consider that we are paying you.");
        			userController.incrementMispelling(currentUser.getUserName());
    		}
    	}
    }

    private void adminMenu(){
    	while(true) {
    		System.out.println("\nEnter \"list accounts\" to list all accounts.");
    		System.out.println("Enter \"screen accounts\" to approve/deny pending accounts.");
    		System.out.println("Enter \"list customers\" to list all customers.");
    		System.out.println("Enter \"logout\" to logout.");
    		System.out.println("Enter \"list employees\" to list all employees.");
    		System.out.println("Enter \"view transgressions\" to decide who to antagonize today.");
    		System.out.println("Enter \"add note\" to make a note against an employee.\n");
    		String response = scan.nextLine();
    		switch(response) {
        		case "list accounts":
        			accountController.listAllAccounts();
        			break;
        		
        		case "screen accounts":
        			ArrayList<Account> accounts = accountController.getAllUnapprovedAccounts();
        			if(accounts.isEmpty()) {
        				System.out.println("\nThere are no pending accounts.");
        				break;
        			}
        			for(Account a: accounts) {
        				System.out.println(a);
        			}
        			String username = "";
        			String accountName = "";
        			boolean notFound = true;
        			Account defendent = null;
        			while(notFound) {
        				System.out.println("\nEnter the username of the owner of the account to judge:");
            			username = scan.nextLine();
            			System.out.println("Enter the name of the account to judge:");
            			accountName = scan.nextLine();
        				for(Account a: accounts) {
        					if(username.equals(a.getUsername()) && accountName.equals(a.getName())){
        						defendent = a;
        						notFound = false;
        						break;
        					}
        				}
        				if(notFound) {
        					System.out.println("\nNo such pending account exists.\n");
        				}
        			}
        			System.out.println("Enter \"approve\" or \"deny\":");
        			while(true) {
        				response = scan.nextLine();
        				if(response.equals("approve")) {
        					if(accountController.approveAccount(defendent)) {
        						System.out.println("\nAccount approved successfully.");
        						defendent.approve();
        					}
        					else {
        						System.out.println("\nAn error occured while attempting to approve account.");
        					}
        					break;
        				}
        				else if(response.equals("deny")) {
        					if(accountController.denyAccount(defendent)) {
        						System.out.println("\nAccount denied successfully, I hope you're happy.");
        					}
        					else {
        						System.out.println("\nAn error occured while attempting to deny account.");
        					}
        					break;
        				}
        				else {
        					System.out.println("Please enter a valid response.");
        				}
        			}
        			break;
        		
        		case "list customers":
        			if(!userController.listCustomers()) {
        				System.out.println("An unexpected error occured.");
        			}
        			break;
        			
        		case "list employees":
        			if(!userController.listEmployees()) {
        				System.out.println("An unexpected error occured.");
        			}
        			break;
        			
        		case "view transgressions":
        			if(!userController.listTransgressions()) {
        				System.out.println("An uexpected error occured.");
        			}
        			break;
        			
        		case "add note":
        			System.out.println("Enter the username of the employee you would like to make a note on.");
        			String employeeName = "";
        			while(true) {
        				employeeName = scan.nextLine();
            			if(!userController.isUsernameAvailable(employeeName)){
            				break;
            			}
            			System.out.println("Employee not found. Re-enter username.");
        			}
        			System.out.println("What would you like your note to be? No more than 100 characters: ");
        			String note = "";
        			while(true) {
        				note = scan.nextLine();
            			if(note.length() >= 100){
            				System.out.println("Note too long! try again.");
            				continue;
            			}
            			break;
        			}
        			if(!userController.addNote(note, employeeName)) {
        				System.out.println("An unexpected error occured.");
        			}
        			break;
        		
        		case "logout":
        			return;
        		
        		default:
        			System.out.println("Invalid input. It's okay, everyone makes mistakes.");
    		}
    	}
    }

    private void loginMenu() {
        while (true) {
            System.out.println("\nPlease input your username:");
            String username = scan.nextLine();
            System.out.println("\nPlease input your password:");
            String password = scan.nextLine();
            currentUser = userController.login(username, password);
            if (!Objects.isNull(currentUser)) {
                log.info("User " + currentUser.getUserName() + " logged in.");
                break;
            }
            System.out.println("\nLogin failed. Type \"1\" to try again, and anything else to return to the menu.");
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
            log.info("User " + currentUser.getUserName() + " registered a new account.");
            break;
        }
    }

}
