package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;

import java.util.List;

public class MovementManagementController extends CoreController{
    public MovementManagementController(){
        super();
    }


    public List<TransactionDto> getTransactionsByUser(String userId) {
        return modelFactory.getTransactionsByUser(userId);
    }

    public List<Account> getAccountsByUserId(String id) {
        return modelFactory.getAccountListByUserId(id);
    }

    public void generateSerialization() {
        modelFactory.generateSerialization();
    }
}
