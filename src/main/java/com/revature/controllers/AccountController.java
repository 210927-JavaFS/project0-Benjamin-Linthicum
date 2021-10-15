package com.revature.controllers;

import com.revature.services.AccountService;
import com.revature.models.Account;
import java.util.*;

public class AccountController {
	
	private AccountService accountService = new AccountService();
	
    public void listAllAccounts() {
    	accountService.listAllAccounts();
    }
    
    public ArrayList<Account> getAllUnapprovedAccounts(){
    	return accountService.getAllUnapprovedAccounts();
    }
    
    public boolean approveAccount(Account account) {
    	return accountService.approveAccount(account);
    }
    
    public boolean denyAccount(Account account) {
    	return accountService.denyAccount(account);
    }
}
