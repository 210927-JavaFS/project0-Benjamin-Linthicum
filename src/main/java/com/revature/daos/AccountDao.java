package com.revature.daos;

import com.revature.models.*;
import java.util.*;

public interface AccountDao {
    
    public ArrayList<Account> getAllAccounts(boolean unapprovedOnly);
    public boolean approveAccount(Account account);
    public boolean denyAccount(Account account);
}