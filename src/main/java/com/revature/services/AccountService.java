package com.revature.services;

import com.revature.models.Account;
import com.revature.daos.AccountDao;
import com.revature.daos.AccountDaoImpl;
import java.util.*;

public class AccountService{
	
	private AccountDao accountDao = new AccountDaoImpl();
	
	public void listAllAccounts() {
		ArrayList<Account> accounts = accountDao.getAllAccounts(false);
		for(Account a: accounts) {
			System.out.println(a);
		}
	}
	
	public ArrayList<Account> getAllUnapprovedAccounts(){
		return accountDao.getAllAccounts(true);
	}
}