package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.AccountDto;

import java.util.List;

public class AccountManagementController extends CoreController {
    public AccountManagementController() {
        super();
    }
    // Account Type Methods
    // --------------------
    public List<String> getAccountTypes() {
        return modelFactory.getAccountTypes();
    }

    // Account Management Methods
    // --------------------------
    public boolean addAccountDto(AccountDto accountDto) {
        return modelFactory.addAccount(accountDto);
    }

    public boolean removeAccount(AccountDto accountSelected) {
        return modelFactory.removeAccount(accountSelected);
    }

    public boolean updateAccount(AccountDto accountSelected, AccountDto accountDto) {
        return modelFactory.updateAccount(accountSelected, accountDto);
    }

    // User Account Methods
    // --------------------
    public List<AccountDto> getAccountsByUserId(String userId) {
        return modelFactory.getAccountsByUserId(userId);
    }
}
