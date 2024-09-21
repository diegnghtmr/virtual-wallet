package co.edu.uniquindio.virtualwallet.virtualwallet.Factory.inter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.SecureRandom;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    private String idTransaction;
    private LocalDate date;
    private double amount;
    private String description;
    private Category category;


    public Transaction(double amount, String description, Category category) {
        this.date = LocalDate.now();
        this.idTransaction = generateId();
        this.amount = amount;
        this.description = description;
        this.category = category;
    }

    private String generateId() {
        SecureRandom random = new SecureRandom();
        int idNumber = random.nextInt(1_000_000_000); // Genera un número entre 0 y 999999999
        return String.format("%09d", idNumber); // Formatea a 9 dígitos
    }
}
