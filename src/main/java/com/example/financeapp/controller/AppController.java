package com.example.financeapp.controller;
import com.example.financeapp.util.InputHandler;

public class AppController {
    private final InputHandler handler;

    public AppController(InputHandler handler) {
        this.handler = handler;
    }

    public void run() {
        while (true) {
            switch (handler.showMenu()) {

                case 1:
                    handler.handleAddTransaction();
                    break;

                case 2:
                    handler.handleShowTransactions();
                    break;

                case 3:
                    handler.handleFilter();
                    break;

                case 4:
                    handler.handleShowBalance();
                    break;

                case 5:
                    handler.handleAddCategory();
                    break;

                case 0:
                    return;
            }
        }
    }
}
