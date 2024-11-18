package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.WithdrawalDto;

import java.util.List;

public class WithdrawalManagementController extends CoreController {
    public WithdrawalManagementController() {
        super();
    }

    public List<WithdrawalDto> getWithdrawalsByUser(String userId) {
        return modelFactory.getWithdrawalsByUser(userId);
    }

    public List<Account> getAccountsByUserId(String id) {
        return modelFactory.getAccountListByUserId(id);
    }

    public List<CategoryDto> getCategoriesByUserId(String id) {
        return modelFactory.getCategoriesByUserId(id);
    }


    public boolean isWithdrawalIdExists(String idTransaction) {
        return modelFactory.isTransactionIdExists(idTransaction);
    }

    public boolean isTransactionIdExists(String idNumber) {
        return modelFactory.isTransactionIdExists(idNumber);
    }

    public boolean userAddWithdrawal(WithdrawalDto withdrawalDto) {
        return modelFactory.addWithdrawal(withdrawalDto);
    }
}
