package com.cydeo.sundaybank.repository;

import com.cydeo.sundaybank.entity.Account;
import com.cydeo.sundaybank.exception.RecordNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class AccountRepository {
    public static List<Account> accountList =new ArrayList<>();
    public Account save(Account account){
        accountList.add(account);
        return account;
    }

    public List<Account> findAll() {
        return accountList;
    }

    public Account findById(UUID accountId) {
      return   accountList.stream().filter(account -> account.getId().equals(accountId)).findAny()
                          .orElseThrow(()->new RecordNotFoundException("This account is not in database"));

    }
}
