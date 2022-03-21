package com.cydeo.sundaybank.service.impl;

import com.cydeo.sundaybank.entity.Account;
import com.cydeo.sundaybank.entity.Transaction;
import com.cydeo.sundaybank.enums.AccountType;
import com.cydeo.sundaybank.exception.AccountOwnerShipException;
import com.cydeo.sundaybank.exception.BadRequestException;
import com.cydeo.sundaybank.exception.BalanceNotSufficentException;
import com.cydeo.sundaybank.repository.AccountRepository;
import com.cydeo.sundaybank.repository.TransactionRepository;
import com.cydeo.sundaybank.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Value("{under_construction}")
    private boolean underConstruction;

    AccountRepository accountRepository;
    TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository ,  TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository=transactionRepository;
    }

    @Override
    public Transaction makeTransfer(BigDecimal amount, Date creationDate, Account sender, Account receiver, String message) {
        checkAccountOwnership(sender, receiver);
        validateAccount(sender ,receiver);
        executeBalanceAndUpdateIfRequired(amount ,sender ,receiver);
        return transactionRepository.save(Transaction.builder().amount(amount).creationDate(creationDate).sender(sender.getId())
                .receiver(receiver.getId()).message(message).build());
    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, Account sender, Account receiver) {
        
        if(checkSenderBalance(sender,amount)){
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
            
        }else{
            throw new BalanceNotSufficentException("balance is not enough for this transaction");
        }

    }

    private boolean checkSenderBalance(Account sender, BigDecimal amount) {
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO)>0;
    }

    private void validateAccount(Account sender, Account receiver) {
        if(sender==null || receiver==null){
            throw new BadRequestException("sender or receiver can not be null");
        }
        if(sender.getId().equals(receiver.getId())){
            throw new BadRequestException("Sender account needs to be different from receiver account");
        }
        findAccountById(sender.getId());
        findAccountById(receiver.getId());

    }

    private Account findAccountById(UUID accountId) {

      return accountRepository.findById(accountId);
    }


    private void checkAccountOwnership(Account sender, Account receiver) {
        if((sender.getAccountType().equals(AccountType.SAVINGS) || receiver.getAccountType().equals(AccountType.SAVINGS))
              && !sender.getUserId().equals(receiver.getUserId()) ){
            throw new AccountOwnerShipException("when one of the account type is SAVING, sender and receiver  has to be same person");
        }
    }

    @Override
    public List<Transaction> findAll() {
        return null;
    }
}
