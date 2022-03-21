package com.cydeo.sundaybank.service;

import com.cydeo.sundaybank.entity.Account;
import com.cydeo.sundaybank.enums.AccountType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountService {
     Account createNewAccount(BigDecimal balance, Date creationDate , AccountType accountType , Long userId);
     List<Account> listAllAccount();
}
