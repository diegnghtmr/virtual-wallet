package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;

import java.util.List;

public class FrecuentsTransactionsController extends CoreController {

    public FrecuentsTransactionsController() {
        super();
    }

    public List<User> getUsersWithMostTransactions() {
        return modelFactory.getUsersWithMostTransactions();
    }

    public int countTransaction(User user) {
        return modelFactory.countTransaction(user);
    }
}
