package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.AccountDto;

import java.util.List;

public record SavingsAccountDto(
        double balance,
        String bankName,
        String accountNumber,
        UserDto user,
        List<TransferDto> associatedTransfers,
        List<DepositDto> associatedDeposits,
        List<WithdrawalDto> associatedWithdrawals
) implements AccountDto {
    @Override
    public String accountType() {
        return "AHORROS";
    }
}