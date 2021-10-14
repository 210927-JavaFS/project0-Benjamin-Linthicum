package com.revature.daos;

import com.revature.models.*;
import java.util.*;

public interface UserDao {
    
    public User findByUserName(String username);
    public boolean insertNewUser(User user);
    public ArrayList<Account> findAccountsByUser(String username);
}
