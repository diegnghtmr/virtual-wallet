package co.edu.uniquindio.virtualwallet.virtualwallet.model;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Transaction;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Category implements Serializable {
    private String id;
    private String name;
    private String description;
    private List<Budget> budgetList;
    private List<Transaction> transactionList;

    public Category(String id, String name, String description) {
        this.id = generateId();
        this.name = name;
        this.description = description;
        this.budgetList = new ArrayList<>();
        this.transactionList = new ArrayList<>();
    }

    private String generateId() {
        Random random = new Random();
        int idNumber = random.nextInt(1_000_000_000); // Genera un número entre 0 y 999999999
        return String.format("%09d", idNumber); // Formatea a 9 dígitos
    }
}