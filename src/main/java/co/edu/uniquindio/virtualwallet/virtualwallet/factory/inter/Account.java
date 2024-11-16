package co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Deposit;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Transfer;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Withdrawal;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "accountNumber")


public class Account implements Serializable {
    private double balance;
    private String bankName;
    private String accountNumber;
    private User user;
    private List<Transfer>associatedTransfers = new ArrayList<>();
    private List<Deposit>associatedDeposits = new ArrayList<>();
    private List<Withdrawal> associatedWithdrawals = new ArrayList<>();
    private static final long serialVersionUID = 1L;

    public Account(String bankName, String accountNumber, User user){
        this.balance = 0;
        this.accountNumber = accountNumber;
        this.user = user;
        this.associatedTransfers = new ArrayList<>();
        this.bankName = bankName;
        this.associatedDeposits = new ArrayList<>();
        this.associatedWithdrawals = new ArrayList<>();
    }
}