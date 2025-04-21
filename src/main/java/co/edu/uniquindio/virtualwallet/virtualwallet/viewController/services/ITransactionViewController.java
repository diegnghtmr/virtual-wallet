package co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services;

import javafx.event.ActionEvent;

public interface ITransactionViewController <T> {
    void onNew(ActionEvent event);

    void onAdd(ActionEvent event);

    void onNotification(ActionEvent event);

    void initialize();

    void initView();

    void initDataBinding();

    void listenerSelection();

    boolean validateData(T object);

    void clearFields();

    void deselectTable();
}
