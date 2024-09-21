package co.edu.uniquindio.virtualwallet.virtualwallet.model;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter

public class VirtualWallet {
    private List<Deposit> depositList;
    private List<Transfer> transferList;
    private List<Withdrawal> withdrawalList;
    private List<Category> categoryList;
    private List<Budget> budgetList;
    private List<SavingsAccount> savingsAccountList;
    private List<CheckingAccount> checkingAccountList;
    private List<User> userList;
    private Administrator administrator;

    public VirtualWallet(){
        depositList = new ArrayList<>();
        transferList = new ArrayList<>();
        withdrawalList = new ArrayList<>();
        categoryList = new ArrayList<>();
        budgetList = new ArrayList<>();
        savingsAccountList = new ArrayList<>();
        checkingAccountList = new ArrayList<>();
        userList = new ArrayList<>();

    }
}
