package com.cydeo.sundaybank.repository;

import com.cydeo.sundaybank.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    public List<Transaction> transactionList =new ArrayList<>();

    public Transaction save(Transaction transaction){
        transactionList.add(transaction);
        return transaction;

    }
}
