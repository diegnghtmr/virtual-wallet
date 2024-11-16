package co.edu.uniquindio.virtualwallet.virtualwallet.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Budget implements Serializable {
    private String id;
    private String name;
    private double totalAmount;
    private double amountSpent;
    private Category category;
    private User user;
    private static final long serialVersionUID = 1L;
}