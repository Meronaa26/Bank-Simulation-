package com.cydeo.sundaybank.service;

import com.cydeo.sundaybank.entity.Account;
import com.cydeo.sundaybank.entity.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface TransactionService {
    Transaction makeTransfer(BigDecimal amount , Date creationDate , Account sender , Account receiver , String message );
    List<Transaction> findAll();
}
