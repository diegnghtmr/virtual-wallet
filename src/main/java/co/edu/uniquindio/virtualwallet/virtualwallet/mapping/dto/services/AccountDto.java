package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.DepositDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.TransferDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.UserDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.WithdrawalDto;

import java.util.List;

public interface AccountDto {
    double balance();
    String bankName();
    String accountNumber();
    String userId();
    List<TransferDto> associatedTransfers();
    List<DepositDto> associatedDeposits();
    List<WithdrawalDto> associatedWithdrawals();
    String accountType();
}