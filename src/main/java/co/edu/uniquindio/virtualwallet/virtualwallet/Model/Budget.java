package co.edu.uniquindio.virtualwallet.virtualwallet.Model;

import lombok.AllArgsConstructor;
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

    public Budget(String id, String name, double totalAmount, double amountSpent) {
        this.id = id;
        this.name = name;
        this.totalAmount = totalAmount;
        this.amountSpent = amountSpent;
    }
}