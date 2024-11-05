package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.AccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.I18n;

import java.util.List;

public record CheckingAccountDto(
        double balance,
        String bankName,
        String accountNumber,
        User user,
        List<TransferDto> associatedTransfers,
        List<DepositDto> associatedDeposits,
        List<WithdrawalDto> associatedWithdrawals,
        double overdraftLimit
) implements AccountDto {
    @Override
    public String accountType() {
        return I18n.get("account.type.checking");
    }
}