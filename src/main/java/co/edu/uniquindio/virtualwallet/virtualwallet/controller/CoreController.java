package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

public abstract class CoreController {
    ModelFactory modelFactory;

    public CoreController() {
        modelFactory = ModelFactory.getInstance();
    }


}
