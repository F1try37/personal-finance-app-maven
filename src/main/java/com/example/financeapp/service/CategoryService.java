package com.example.financeapp.service;

import com.example.financeapp.entity.Category;
import com.example.financeapp.repository.CategoryRepository;

import java.util.List;

public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public void addCategory(String name) {
        if (repository.findByName(name) == null) {
            repository.addCategory(name);
        } else {
            System.out.println("Такая категория уже существует");
        }
    }

    public List<Category> getCategories() {
        return repository.getCategories();
    }

    public Category findByName(String name) {
        return repository.findByName(name);
    }

    public Category findById(int id) {
        return repository.findById(id);
    }
}
