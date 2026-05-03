package com.example.financeapp.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private final int id;
    private BigDecimal amount;
    private TransactionType transactionType;
    private Category category;
    private String description;
    private final LocalDateTime dateTime;

    public Transaction(BigDecimal amount, int id, TransactionType transactionType, Category category, String description, LocalDateTime dateTime) {
        this.amount = amount;
        this.id = id;
        this.transactionType = transactionType;
        this.category = category;
        this.description = description;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "id: " + id + ", сумма: " + amount + ", тип: " + transactionType + ", категория: " + category + ", описание: " + description + ", дата: " + dateTime;
    }
}
