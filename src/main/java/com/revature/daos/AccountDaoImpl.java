package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.lang.model.util.ElementScanner14;

import com.revature.models.*;
import com.revature.utils.ConnectionUtil;

public class AccountDaoImpl implements AccountDao{
    
    public ArrayList<Account> getAllAccounts(boolean unapprovedOnly){
    	ArrayList<Account> accounts = new ArrayList<Account>();
    	try(Connection conn = ConnectionUtil.getConnection()){
    		String sql = "SELECT * FROM account ORDER BY user_name;";
    		if(unapprovedOnly) {
    			sql = "SELECT * FROM account WHERE account.isApproved = false;";
    		}
    		PreparedStatement statement = conn.prepareStatement(sql);
    		ResultSet results = statement.executeQuery();
    		while(results.next()) {
    			accounts.add(new Account(results.getString("account_type"),
                        				 results.getString("account_name"),
                        				 results.getDouble("balance"),
                        				 results.getBoolean("isApproved"),
                        				 results.getString("user_name")));
    		}
    	}
    	catch(SQLException e) {
    		if(e.getMessage().substring(0, 10).equals("The column")) {
    			return accounts;
    		}
    		e.printStackTrace();
    	}
    	
    	return accounts;
    }
    
    public boolean approveAccount(Account account) {
    	try(Connection conn = ConnectionUtil.getConnection()){
    		String sql = "UPDATE account SET isApproved = true WHERE account_name = ? AND user_name = ?;";
    		PreparedStatement statement = conn.prepareStatement(sql);
    		int count = 0;
    		
    		statement.setString(++count, account.getName());
    		statement.setString(++count, account.getUsername());
    		statement.execute();
    		
    		return true;
    		
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return false;
    }
    
    public boolean denyAccount(Account account) {
    	try(Connection conn = ConnectionUtil.getConnection()){
    		String sql = "DELETE FROM account WHERE account_name = ? AND user_name = ?;";
    		PreparedStatement statement = conn.prepareStatement(sql);
    		int count = 0;
    		
    		statement.setString(++count, account.getName());
    		statement.setString(++count, account.getUsername());
    		statement.execute();
    		
    		return true;
    		
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return false;
    }
}