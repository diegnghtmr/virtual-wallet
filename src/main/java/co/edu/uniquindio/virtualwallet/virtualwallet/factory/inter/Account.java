package co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Deposit;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Transfer;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Withdrawal;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Setter
@Getter
@NoArgsConstructor


public abstract class Account {
    private String id;
    private String bankName;
    private String accountNumber;
    private User user;
    private List<Transfer>associatedTransfers;
    private List<Deposit>associatedDeposits ;
    private List<Withdrawal> associatedWithdrawal;

    public Account(String bankName, String accountNumber, User user){
        this.id = generateId();
        this.accountNumber = accountNumber;
        this.user = user;
        this.associatedTransfers = new ArrayList<>();
        this.bankName = bankName;
        this.associatedDeposits = new ArrayList<>();
        this.associatedWithdrawal = new ArrayList<>();

    }
    private String generateId() {
        Random random = new Random();
        int idNumber = random.nextInt(1_000_000_000); // Genera un número entre 0 y 999999999
        return String.format("%09d", idNumber); // Formatea a 9 dígitos
    }
    @Override
    public String toString(){
        return getAccountNumber();
    }


}