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

public class UserDaoImpl implements UserDao{
    
    @Override
    public User findByUserName(String username){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM bankuser WHERE user_name = ?;";
            User user = null;

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            if(result.next()) {
            	if(result.getString("accesslevel").equals("Customer")){
            		user = new Customer(result.getString("first_name"),
            				result.getString("last_name"),
            				result.getString("user_name"),
            				result.getString("pass"));
            	}
            	else if(result.getString("accesslevel").equals("Employee")){
            		user = new Employee(result.getString("first_name"),
            				result.getString("last_name"),
            				result.getString("user_name"),
            				result.getString("pass"));
            	}	
            	else if(result.getString("accesslevel").equals("Admin")){
            		user = new Admin(result.getString("first_name"),
            				result.getString("last_name"),
            				result.getString("user_name"),
            				result.getString("pass"));
            	}
            }
            return user;

        }
        catch (SQLException e){
        	/*if(e.getMessage().substring(0, 10).equals("The column")) {
    			return null;
    		}*/
            e.printStackTrace();
        }
        return null; //TODO
    }

    @Override
    public boolean insertNewUser(User user){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO bankuser VALUES (?,?,?,?,?);";
            PreparedStatement statement = conn.prepareStatement(sql);
            int count = 0;

            statement.setString(++count, user.getUserName());
            statement.setString(++count, user.getFirstName());
            statement.setString(++count, user.getLastName());
            if(user instanceof Customer) // right now, this is the only possible result
                statement.setString(++count, "Customer");
            else if(user instanceof Employee)
                statement.setString(++count, "Employee");
            else
                statement.setString(++count, "Admin");
            statement.setString(++count, user.getEncryptedPassword());
            statement.execute();
            
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<Account> findAccountsByUser(String username){
    	ArrayList<Account> accounts = new ArrayList<Account>();
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM account WHERE user_name = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                accounts.add(new Account(result.getString("user_name"),
                                         result.getString("account_name"),
                                         result.getDouble("balance"),
                                         result.getBoolean("isApproved"),
                                         result.getString("account_type"))); 
            }

            return accounts;
        }
        catch (SQLException e){
        	if(e.getMessage().substring(0, 10).equals("The column")) {
    			return accounts;
    		}
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addAccount(String name, String type, User user){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO account (account_name, user_name, account_type, balance, isApproved) " +
                         "VALUES (?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            int count = 0;

            statement.setString(++count, name);
            statement.setString(++count, user.getUserName());
            statement.setString(++count, type);
            statement.setDouble(++count, 0.0);
            statement.setBoolean(++count, false);
            statement.execute();

            user.applyForAccount(name, type);

            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deposit(String username, String accountName, double amount){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "UPDATE account SET balance = balance + ? WHERE user_name = ? AND account_name = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            int count = 0;

            statement.setDouble(++count, amount);
            statement.setString(++count, username);
            statement.setString(++count, accountName);
            statement.execute();
            
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean withdraw(String username, String accountName, double amount){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "UPDATE account SET balance = balance - ? WHERE user_name = ? AND account_name = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            int count = 0;

            statement.setDouble(++count, amount);
            statement.setString(++count, username);
            statement.setString(++count, accountName);
            statement.execute();
            
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean transfer(String username, String fromName, String toName, double amount){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "CALL Transfer(?,?,?,?);";
            PreparedStatement statement = conn.prepareStatement(sql);
            int count = 0;

            statement.setString(++count, username);
            statement.setString(++count, fromName);
            statement.setString(++count, toName);
            statement.setDouble(++count, amount);
            statement.execute();

            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public void incrementMispelling(String username) {
    	try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "UPDATE transgressions SET mispellings = mispellings + 1 WHERE user_name = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public ArrayList<Customer> getAllCustomers(){
    	ArrayList<Customer> customers = new ArrayList<Customer>(); //String first_name, String last_name, String user_name, String password
    	try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM bankuser WHERE accesslevel = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "Customer");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                customers.add(new Customer(result.getString("first_name"),
                                         result.getString("last_name"),
                                         result.getString("user_name"),
                                         result.getString("pass"))); 
            }
            return customers;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    	return customers;
    }
    
    @Override
    public ArrayList<Employee> getAllEmployees(){
    	ArrayList<Employee> employees = new ArrayList<Employee>();
    	try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM bankuser WHERE accesslevel = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "Employee");
            ResultSet result = statement.executeQuery();
            while(result.next()){
            	employees.add(new Employee(result.getString("user_name"),
                                         result.getString("pass"),
                                         result.getString("first_name"),
                                         result.getString("last_name"))); 
            }
            return employees;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    	return employees;
    }
    
    @Override
    public ArrayList<Transgressions> getAllTransgressions(){
    	ArrayList<Transgressions> transgressions = new ArrayList<Transgressions>();
    	try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM transgressions";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while(result.next()){
            	transgressions.add(new Transgressions(result.getString("note"),
                                         			  result.getInt("mispellings"),
                                         			  result.getString("user_name"))); 
            }
            return transgressions;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    	return transgressions;
    }
    
    @Override
    public boolean addNote(String note, String username) {
    	try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "UPDATE transgressions SET note = ? WHERE user_name = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,note);
            statement.setString(2,username);
            statement.execute();
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    	return false;
    }

}
