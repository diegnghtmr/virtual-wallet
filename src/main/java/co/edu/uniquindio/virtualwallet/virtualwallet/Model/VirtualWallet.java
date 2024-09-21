package co.edu.uniquindio.virtualwallet.virtualwallet.model;

import co.edu.uniquindio.virtualwallet.virtualwallet.Factory.inter.implementation.Withdrawal;

import java.util.ArrayList;

public class VirtualWallet {
    private List<Deposit> depositList;
    private List<Transfer> transferList;
    private List<Withdrawal> withdrawalList;
}
