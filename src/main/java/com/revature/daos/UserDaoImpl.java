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

            if(result.getString("account_type").equals("Customer")){
                user = new Customer(result.getString("first_name"),
                                    result.getString("last_name"),
                                    result.getString("user_name"),
                                    result.getString("pass"));
            }
            else if(result.getString("account_type").equals("Employee")){
                user = new Employee(result.getString("first_name"),
                                    result.getString("last_name"),
                                    result.getString("user_name"),
                                    result.getString("pass"));
            }
            else if(result.getString("account_type").equals("Admin")){
                user = new Admin(result.getString("first_name"),
                                    result.getString("last_name"),
                                    result.getString("user_name"),
                                    result.getString("pass"));
            }
            return user;

        }
        catch (SQLException e){
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
            statement.setString(++count, user.getEncryptedPassword());
            statement.setString(++count, user.getFirstName());
            statement.setString(++count, user.getLastName());
            if(user instanceof Customer) // right now, this is the only possible result
                statement.setString(++count, "Customer");
            else if(user instanceof Employee)
                statement.setString(++count, "Employee");
            else
                statement.setString(++count, "Admin");
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
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM account WHERE user_name = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            ArrayList<Account> accounts = new ArrayList<Account>();
            ResultSet result = statement.executeQuery();
            while(result.next()){
                accounts.add(new Account(result.getString("account_type"),
                                         result.getString("account_name"),
                                         result.getDouble("balance"),
                                         result.getBoolean("isApproved"))); 
            }

            return accounts;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addAccount(String name, String type, User user){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO account VALUES (account_name, user_name, account_type, balance, isApproved) " +
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

}
