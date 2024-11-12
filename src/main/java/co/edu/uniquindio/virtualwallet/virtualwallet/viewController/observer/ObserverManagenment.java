package co.edu.uniquindio.virtualwallet.virtualwallet.viewController.observer;

public class ObserverManagenment {

    private ObserverView observerView;
    private static ObserverManagenment INSTANCE;

    private ObserverManagenment() {}

    public static ObserverManagenment getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ObserverManagenment();
        }
        return INSTANCE;
    }

    public void agregarObservador(ObserverView observerView) {
        this.observerView = observerView;
    }

    public void notificar() {
        this.observerView.notificar();
    }

}
