package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import java.util.Map;

public class AverageUserBalanceController extends CoreController {

    public AverageUserBalanceController() {
        super();
    }

    public double getAverageUserBalance() {
        return modelFactory.getAverageUserBalance();
    }


}
