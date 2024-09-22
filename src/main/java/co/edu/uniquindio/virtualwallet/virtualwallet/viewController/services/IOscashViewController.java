package co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services;

import javafx.event.ActionEvent;

public interface IOscashViewController {
    void onNotification(ActionEvent event);
    void onSendMessage(ActionEvent event);
    void onSendRating(ActionEvent event);
    void initialize();
}