package co.edu.uniquindio.virtualwallet.virtualwallet.viewController.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObserverManagement {

    private static ObserverManagement INSTANCE;
    private Map<EventType, List<ObserverView>> observers = new HashMap<>();

    private ObserverManagement() {
    }

    public static ObserverManagement getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ObserverManagement();
        }
        return INSTANCE;
    }

    public void addObserver(EventType event, ObserverView observer) {
        observers.computeIfAbsent(event, k -> new ArrayList<>()).add(observer);
    }

    public void removeObserver(EventType event, ObserverView observer) {
        List<ObserverView> observersList = observers.get(event);
        if (observersList != null) {
            observersList.remove(observer);
            if (observersList.isEmpty()) {
                observers.remove(event);
            }
        }
    }

    public void notifyObservers(EventType event) {
        List<ObserverView> observersList = observers.get(event);
        if (observersList != null) {
            observersList.forEach(observer -> observer.updateView(event));
        }
    }

}
