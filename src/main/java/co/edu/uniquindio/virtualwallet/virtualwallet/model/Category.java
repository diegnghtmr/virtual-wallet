package co.edu.uniquindio.virtualwallet.virtualwallet.model;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Transaction;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder

public class Category {
    private int id;
    private String name;
    private String description;
    private List<Budget> budgetList;
    private List<Transaction> transactionList;

    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.budgetList = new ArrayList<>();
        this.transactionList = new ArrayList<>();
    }
}