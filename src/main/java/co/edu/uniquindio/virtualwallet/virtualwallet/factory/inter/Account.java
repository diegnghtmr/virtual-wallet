package co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Deposit;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Transfer;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Withdrawal;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder

public  class Account {
    private double balance;
    private String bankName;
    private String accountNumber;
    private User user;
    private List<Transfer>associatedTransfers;
    private List<Deposit>associatedDeposits ;
    private List<Withdrawal> associatedWithdrawal;

    public Account(String bankName, String accountNumber, User user){
        this.balance = 0;
        this.accountNumber = accountNumber;
        this.user = user;
        this.associatedTransfers = new ArrayList<>();
        this.bankName = bankName;
        this.associatedDeposits = new ArrayList<>();
        this.associatedWithdrawal = new ArrayList<>();
    }
}