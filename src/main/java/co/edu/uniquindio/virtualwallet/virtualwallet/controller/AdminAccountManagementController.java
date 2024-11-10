package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.UserDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.AccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;

import java.util.List;

public class AdminAccountManagementController extends CoreController {

    public AdminAccountManagementController() {
        super();
    }

    public List<AccountDto> getAccounts() {
        return modelFactory.getAccounts();
    }

    public List<String> getAccountsTypes() {
        return modelFactory.getAccountTypes();
    }

    public List<User> getUserList() {
        return modelFactory.getUserList();
    }


    public boolean adminAddAccount(AccountDto accountDto) {
        return modelFactory.addAccount(accountDto);
    }

    public boolean adminRemoveAccount(AccountDto accountSelected) {
        return modelFactory.removeAccount(accountSelected);
    }

    public boolean adminUpdateAccount(AccountDto accountSelected, AccountDto accountDto) {
        return modelFactory.updateAccount(accountSelected, accountDto);
    }
}
