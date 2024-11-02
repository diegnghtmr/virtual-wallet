package co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.enums.TransactionStatus;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.NotificationUtil;
import co.edu.uniquindio.virtualwallet.virtualwallet.services.Observable;
import co.edu.uniquindio.virtualwallet.virtualwallet.services.Observer;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Category;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.enums.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder

public  class Transaction implements Serializable, Observable {
    private String idTransaction;
    private LocalDate date;
    private double amount;
    private String description;
    private Category category;
    private Account account;
    private TransactionStatus status; // Nuevo atributo
    private List<Observer> observers = new ArrayList<>();
    private static final long serialVersionUID = 1L;


    public Transaction(double amount, String description, Category category, Account account) {
        this.idTransaction = generateId();
        this.date = LocalDate.now();
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.account = account;
        this.status = TransactionStatus.PENDING;
    }

    private String generateId() {
        SecureRandom random = new SecureRandom();
        int idNumber = random.nextInt(1_000_000_000);
        return String.format("%09d", idNumber);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(NotificationUtil notificationUtil) {
        for (Observer observer : observers) {
            observer.update(notificationUtil);
        }
    }

    public void executeTransaction() {
        // Lógica de la transacción

        // Crear notificación
        NotificationUtil notificationUtil = new NotificationUtil("Transacción realizada", LocalDate.now(), NotificationType.TRANSACTION);

        // Notificar a los observadores
        notifyObservers(notificationUtil);
    }

}
