package com.revature.daos;

import com.revature.models.*;
import java.util.*;

public interface UserDao {
    
    public User findByUserName(String username);
    public boolean insertNewUser(User user);
    public ArrayList<Account> findAccountsByUser(String username);
    public boolean addAccount(String name, String type, User user);
    public boolean deposit(String username, String accountName, double amount);
    public boolean withdraw(String username, String accountName, double amount);
    public boolean transfer(String username, String fromName, String toName, double amount);
}
