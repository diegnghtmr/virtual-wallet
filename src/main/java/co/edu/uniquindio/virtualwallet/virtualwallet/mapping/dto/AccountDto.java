package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto;

import java.util.List;

public record AccountDto(
        double balance,
        String bankName,
        String accountNumber,
        UserDto user,
        List<TransferDto> associatedTransfers,
        List<DepositDto> associatedDeposits,
        List<WithdrawalDto> associatedWithdrawals
) {
    public String accountType() {
        return this.getClass().getSimpleName();
    }
}

