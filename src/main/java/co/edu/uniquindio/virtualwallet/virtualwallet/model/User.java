package co.edu.uniquindio.virtualwallet.virtualwallet.model;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor

public class User extends Person{
    private String address;
    private double totalBalance;
    private List<Budget> budgetList;
    private List<Account> associatedAccounts;

    public User(String address, double totalBalance) {
        super();
        this.address = address;
        this.totalBalance = totalBalance;
        associatedAccounts = new ArrayList<>();
        budgetList = new ArrayList<>();
    }




}