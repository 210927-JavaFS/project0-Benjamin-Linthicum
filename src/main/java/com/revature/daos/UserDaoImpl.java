package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            String sql = "INSERT INTO bankuser VALUES (?,?,?,?,?)";
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

}
