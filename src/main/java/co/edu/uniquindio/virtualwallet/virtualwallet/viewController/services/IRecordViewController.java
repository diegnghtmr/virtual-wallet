package co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services;

import javafx.event.ActionEvent;

public interface IRecordViewController<T> {
    void onGetCurrentRecords(ActionEvent event);

    void onGetPreviousRecords(ActionEvent event);

    void onGetSubsequentRecords(ActionEvent event);

    void onNotification(ActionEvent event);

    void initialize();

    void initView();

    void initDataBinding();

    void listenerSelection();

    boolean validateData();

    void clearFields();

    void deselectTable();
}
