package co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services;

import javafx.event.ActionEvent;

public interface IUserManagementViewController<T> {
    void onUpdate(ActionEvent event);

    void onGoDashboard(ActionEvent event);

    void onLogout(ActionEvent event);

    void initialize();

    void initView();

    void initDataBinding();

    void listenerSelection();

    boolean validateData(T object);
}
