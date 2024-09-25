// src/main/java/co/edu/uniquindio/virtualwallet/virtualwallet/mapping/dto/services/TransactionDto.java
package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;

import java.time.LocalDate;

public interface TransactionDto {
    String idTransaction();
    LocalDate date();
    double amount();
    String description();
    CategoryDto category();
    Account account();
    String state();
    String transactionType();
}