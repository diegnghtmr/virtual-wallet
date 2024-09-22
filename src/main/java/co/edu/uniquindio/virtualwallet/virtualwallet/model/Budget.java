package co.edu.uniquindio.virtualwallet.virtualwallet.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Budget {
    private String id;
    private String name;
    private double totalAmount;
    private double amountSpent;
    private Category category;
    private User user;
}