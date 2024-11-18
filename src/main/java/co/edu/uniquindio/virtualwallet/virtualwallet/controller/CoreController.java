package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

public abstract class CoreController {
    ModelFactory modelFactory;

    public CoreController() {
        modelFactory = ModelFactory.getInstance();
    }


    public  boolean isTransactionIdExists(String idNumber){
        return  modelFactory.isTransactionIdExists(idNumber);
    }
}
