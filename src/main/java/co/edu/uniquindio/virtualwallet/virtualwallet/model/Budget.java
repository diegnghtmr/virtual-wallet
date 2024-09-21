package co.edu.uniquindio.virtualwallet.virtualwallet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor

public class Budget {
    private String id;
    private String name;
    private double totalAmount;
    private double amountSpent;
    private Category category;
    private User user;

    public Budget(String id, String name, double totalAmount, double amountSpent, User user) {
        this.id = id;
        this.name = name;
        this.totalAmount = totalAmount;
        this.amountSpent = amountSpent;
        this.user = user;
    }
}