package com.example.financeapp.repository;

import com.example.financeapp.config.JacksonConfig;
import com.example.financeapp.entity.Transaction;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    private final List<Transaction> transactions = new ArrayList<>();
    private static final ObjectMapper mapper = JacksonConfig.getMapper();
    private static final String FILE_PATH = "C:\\Users\\egbar\\IdeaProjects\\finance-app\\src\\data\\Transactions.json";

    public TransactionRepository() {
        loadFromFile();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }
        try {
            List<Transaction> loaded = mapper.readValue(file, new TypeReference<>() {
            });
            transactions.addAll(loaded);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке файла: " + e.getMessage());
        }
    }

    public void saveToFile() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), transactions);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении файла: " + e.getMessage());
        }
    }
}
