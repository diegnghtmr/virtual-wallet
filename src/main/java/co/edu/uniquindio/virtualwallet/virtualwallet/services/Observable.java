package co.edu.uniquindio.virtualwallet.virtualwallet.services;

import co.edu.uniquindio.virtualwallet.virtualwallet.utils.NotificationUtil;

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(NotificationUtil notificationUtil);
}
