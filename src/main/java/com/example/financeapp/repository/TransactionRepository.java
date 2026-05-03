package com.example.financeapp.repository;

import com.example.financeapp.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    private final List<Transaction> transactions = new ArrayList<>();

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
