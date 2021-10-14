package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.revature.models.*;
import com.revature.utils.ConnectionUtil;

public class UserDaoImpl implements UserDao{
    
    @Override
    public User findByUserName(String username){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM "; //TODO
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
            if(user instanceof Customer)
                statement.setString(++count, "Customer");
            else
                statement.setString(++count, "Employee");

            statement.execute();

            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
