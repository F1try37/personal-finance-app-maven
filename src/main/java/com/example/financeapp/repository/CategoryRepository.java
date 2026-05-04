package com.example.financeapp.repository;

import com.example.financeapp.config.JacksonConfig;
import com.example.financeapp.entity.Category;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private final List<Category> categories = new ArrayList<>();
    private int nextId = 0;
    private static final String FILE_PATH = "C:\\Users\\egbar\\IdeaProjects\\finance-app\\src\\data\\categories.json";
    ObjectMapper mapper = JacksonConfig.getMapper();

    public CategoryRepository() {
        loadFromFile();
    }

    public void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            categories.add(new Category(nextId++, "Транспорт"));
            categories.add(new Category(nextId++, "Супермаркеты"));
            categories.add(new Category(nextId++, "Такси"));
            saveToFile();
            return;
        }
        try {
            List<Category> loaded = mapper.readValue(file, new TypeReference<>() {
            });
            categories.addAll(loaded);
            nextId = categories.stream().mapToInt(Category::getId).max().orElse(-1) + 1;
        } catch (IOException e) {
            System.out.println("Ошибка при загрузку из файла: " + e.getMessage());
        }
    }

    public void saveToFile() {
        try {
            File file = new File(FILE_PATH);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, categories);
        } catch (IOException e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void addCategory(String name) {
        Category category = new Category(nextId++, name);
        categories.add(category);
        saveToFile();
    }

    public Category findByName(String name) {
        for (Category c: categories) {
            if (name.trim().equalsIgnoreCase(c.getName())) {
                return c;
            }
        }
        return null;
    }

    public Category findById(int id) {
        for (Category c: categories) {
            if (id == c.getId()) {
                return c;
            }
        }
        return null;
    }
}
