package co.edu.uniquindio.virtualwallet.virtualwallet.Factory.inter;

import co.edu.uniquindio.virtualwallet.virtualwallet.Factory.inter.implementation.Deposit;
import co.edu.uniquindio.virtualwallet.virtualwallet.Factory.inter.implementation.Transfer;
import co.edu.uniquindio.virtualwallet.virtualwallet.Factory.inter.implementation.Withdrawal;
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
    private List<Transfer>associatedTransfers = new ArrayList<>();
    private List<Deposit>associatedDeposits = new ArrayList<>();
    private List<Withdrawal> associatedWithdrawal = new ArrayList<>();

    public Account(String bankName,List<Transfer>associatedTransfers,List<Deposit>associatedDeposits, List<Withdrawal> associatedWithdrawal, String accountNumber, User user){
        this.id = generateId();
        this.accountNumber = accountNumber;
        this.user = user;
        this.associatedTransfers = associatedTransfers;
        this.bankName = bankName;
        this.associatedDeposits = associatedDeposits;
        this.associatedWithdrawal = associatedWithdrawal;

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