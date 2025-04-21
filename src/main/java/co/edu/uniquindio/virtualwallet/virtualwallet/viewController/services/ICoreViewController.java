package co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services;

import javafx.event.ActionEvent;

public interface ICoreViewController <T>{
    void onNew(ActionEvent event);

    void onAdd(ActionEvent event);

    void onRemove(ActionEvent event);

    void onUpdate(ActionEvent event);

    void onNotification(ActionEvent event);

    void initialize();

    void initView();

    void initDataBinding();

    void listenerSelection();

    void showInformation(T object);

    boolean validateData(T object);

    void clearFields();

    void deselectTable();
}
