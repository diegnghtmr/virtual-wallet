// src/main/java/co/edu/uniquindio/virtualwallet/virtualwallet/mapping/dto/services/TransactionDto.java
package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDate;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public interface TransactionDto {
    String idTransaction();
    LocalDate date();
    double amount();
    String description();
    CategoryDto category();
    Account account();
    String status();
    String transactionType();
}