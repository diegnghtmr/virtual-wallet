package co.edu.uniquindio.virtualwallet.virtualwallet.model;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Budget implements Serializable {
    private String id;
    private String name;
    private double totalAmount;
    private double amountSpent;
    private Category category;
    private User user;
}