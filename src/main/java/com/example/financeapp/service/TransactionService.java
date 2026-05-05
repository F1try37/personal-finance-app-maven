package com.example.financeapp.service;

import com.example.financeapp.config.JacksonConfig;
import com.example.financeapp.entity.Category;
import com.example.financeapp.entity.TransactionType;
import com.example.financeapp.entity.Transaction;
import com.example.financeapp.repository.TransactionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private final TransactionRepository repository;
    private static final ObjectMapper mapper = JacksonConfig.getMapper();

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public BigDecimal getBalance() {
        return repository.getTransactions().stream().map(t -> t.getTransactionType().equals(TransactionType.INCOME) ? t.getAmount() : t.getAmount().negate()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addTransaction(BigDecimal amount, TransactionType transactionType, Category category, String description) {
        int id;
        if (repository.getTransactions().isEmpty()) {
            id = 0;
        } else {
            Transaction lastTransaction = repository.getTransactions().getLast();
            id = lastTransaction.getId() + 1;
        }

        LocalDateTime dateTime = LocalDateTime.now();

        repository.getTransactions().add(new Transaction(amount,id,transactionType,category,description,dateTime));
        repository.saveToFile();
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(repository.getTransactions());
    }

    public void filterByType (TransactionType type) {
        int k = 0;
        for (Transaction transaction: repository.getTransactions()) {
            if (transaction.getTransactionType() == type) {
                k++;
                System.out.println(transaction);
            }
        }
        if (k == 0) System.out.println("Ничего не найдено.");
    }

    public void filterById (int input) {
        int k = 0;
        for (Transaction transaction: repository.getTransactions()) {
            if (transaction.getId() == input) {
                System.out.println(transaction);
                k++;
            }
        }
        if (k == 0) System.out.println("Ничего не найдено.");
    }

    public void filterByCategory(Category category) {
        int k = 0;
        for (Transaction transaction: repository.getTransactions()) {
            if (transaction.getCategory().equals(category)) {
                System.out.println(transaction);
                k++;
            }
        }
        if (k == 0) System.out.println("Ничего не найдено.");
    }

    public void filterByDescription(String input) {
        int k = 0;
        for (Transaction transaction: repository.getTransactions()) {
            if (transaction.getDescription().toLowerCase().contains(input.toLowerCase())) {
                System.out.println(transaction);
                k++;
            }
        }
        if (k == 0) System.out.println("Ничего не найдено.");
    }

    public void filterByDateTime (LocalDateTime from, LocalDateTime to) {
        int k = 0;
        for (Transaction transaction: repository.getTransactions()) {
            if (transaction.getDateTime().isAfter(from) && transaction.getDateTime().isBefore(to)) {
                System.out.println(transaction);
                k++;
            }
        }
        if (k == 0) System.out.println("Ничего не найдено.");
    }

    public void filterByAmount(BigDecimal fromSum, BigDecimal toSum) {
        int k = 0;
        for (Transaction transaction: repository.getTransactions()) {
            if (transaction.getAmount().compareTo(fromSum) >= 0 && transaction.getAmount().compareTo(toSum) <= 0) {
                System.out.println(transaction);
                k++;
            }
        }
        if (k == 0) System.out.println("Ничего не найдено.");
    }

    public void export_(String fileName) {
        try {
            String filePath = "C:\\Users\\egbar\\IdeaProjects\\finance-app\\src\\data\\" + fileName + ".json";
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), repository.getTransactions());
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    public void import_(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Файл не существует");
            return;
        }
        try {
            List<Transaction> loaded = mapper.readValue(file, new TypeReference<>() {
            });
            for (Transaction t: loaded) {
                int nextId = repository.getNextId();
                t.setId(nextId);
                repository.getTransactions().add(t);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при импорте файла: " + e.getMessage());
        }
    }
}
