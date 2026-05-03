package com.example.financeapp;

import com.example.financeapp.controller.AppController;
import com.example.financeapp.repository.CategoryRepository;
import com.example.financeapp.repository.TransactionRepository;
import com.example.financeapp.service.CategoryService;
import com.example.financeapp.service.TransactionService;
import com.example.financeapp.util.InputHandler;

public class FinanceAppApplication {
    public static void main(String[] args) {
        TransactionRepository repository = new TransactionRepository();
        CategoryRepository categoryRepository = new CategoryRepository();
        TransactionService service = new TransactionService(repository);
        CategoryService categoryService = new CategoryService(categoryRepository);
        InputHandler handler = new InputHandler(service, categoryService);
        AppController controller = new AppController(handler);

        controller.run();
    }
}