package co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.enums.TransactionStatus;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.security.SecureRandom;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder

public  class Transaction implements Serializable {
    private String idTransaction;
    private LocalDate date;
    private double amount;
    private String description;
    private Category category;
    private Account account;
    private TransactionStatus status; // Nuevo atributo
    private static final long serialVersionUID = 1L;


    public Transaction(double amount, String description, Category category, Account account) {
        this.idTransaction = generateId();
        this.date = LocalDate.now();
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.account = account;
        this.status = TransactionStatus.PENDING;
    }

    private String generateId() {
        SecureRandom random = new SecureRandom();
        int idNumber = random.nextInt(1_000_000_000);
        return String.format("%09d", idNumber);
    }
}
