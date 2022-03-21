package com.cydeo.sundaybank.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
@Data
@Builder
public class Transaction {
    private UUID sender;    //Overview. UUID (Universally Unique Identifier), also known as GUID (Globally Unique Identifier) represents a 128-bit long value that is unique for all practical purposes.
    private UUID receiver;
    private BigDecimal amount;
    private String message;
    private Date creationDate;


}
