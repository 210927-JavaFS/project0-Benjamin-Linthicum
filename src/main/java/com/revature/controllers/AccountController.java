package com.revature.controllers;

import com.revature.services.AccountService;

public class AccountController {
	
	private AccountService accountService = new AccountService();
	
    public void listAllAccounts() {
    	accountService.listAllAccounts();
    }
}
