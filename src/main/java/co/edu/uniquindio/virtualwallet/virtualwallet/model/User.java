package co.edu.uniquindio.virtualwallet.virtualwallet.model;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.services.Observer;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.NotificationUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString
public class User extends Person implements Observer {
    private String address;
    private double totalBalance;
    private List<Budget> budgetList = new ArrayList<>();
    private List<Account> associatedAccounts = new ArrayList<>();
    private String verificationCode;
    private boolean isVerified;
    private List<NotificationUtil> notificationUtils = new ArrayList<>();

    public User(String address, double totalBalance) {
        super();
        this.address = address;
        this.totalBalance = totalBalance;
        associatedAccounts = new ArrayList<>();
        budgetList = new ArrayList<>();
        notificationUtils = new ArrayList<>();
    }

    @Override
    public void update(NotificationUtil notificationUtil) {
        notificationUtils.add(notificationUtil);
    }

}