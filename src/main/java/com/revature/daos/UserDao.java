package com.revature.daos;

import com.revature.models.User;

public interface UserDao {
    
    public User findByUserName(String username);
    public boolean insertNewUser(User user);
}
