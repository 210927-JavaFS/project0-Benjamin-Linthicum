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
    		String sql = "SELECT * FROM account;";
    		if(unapprovedOnly) {
    			sql = "SELECT * FROM account WHERE account.isApproved = true;";
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
    		e.printStackTrace();
    	}
    	
    	return accounts;
    }
}