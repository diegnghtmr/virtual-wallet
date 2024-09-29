package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import co.edu.uniquindio.virtualwallet.virtualwallet.exceptions.UserException;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.CheckingAccount;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.SavingsAccount;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.VirtualWallet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PersistenceUtil {

    public static final String ACCOUNTS_FILE_PATH = "src/main/resources/persistence/FileAccount.txt";
    public static final String USERS_FILE_PATH = "src/main/resources/persistence/FileUser.txt";
    public static final String LOG_FILE_PATH = "src/main/resources/persistence/log/VirtualWalletLog.txt";
//    public static final String OBJECTS_FILE_PATH = "co.edu.uniquindio.programacion3/src/main/resources/persistencia/archivoObjetos.txt";
    public static final String VIRTUAL_WALLET_MODEL_BINARY_FILE_PATH = "src/main/resources/persistence/Model.dat";
    public static final String VIRTUAL_WALLET_XML_FILE_PATH = "src/main/resources/persistence/Model.xml";

    public static void loadFileData(VirtualWallet virtualWallet) throws FileNotFoundException, IOException {
        ArrayList<Account> loadedAccounts = loadAccounts();
        if (loadedAccounts.size() > 0)
            virtualWallet.getAccounts().addAll(loadedAccounts);

//        ArrayList<User> loadedUsers = loadUsers();
//        if (loadedUsers.size() > 0)
//            virtualWallet.getUserList().addAll(loadedUsers);
    }

    public static void saveAccounts(ArrayList<Account> accountList) throws IOException {
        String content = "";
        for (Account account : accountList) {
            if (account instanceof CheckingAccount) {
                CheckingAccount checkingAccount = (CheckingAccount) account;
                content += checkingAccount.getBankName()
                        + "," + checkingAccount.getAccountNumber()
                        + "," + checkingAccount.getBalance()
                        + "," + checkingAccount.getUser().getFullName()
                        + "," + checkingAccount.getUser().getId()
                        + "," + checkingAccount.getUser().getAddress()
                        + "," + checkingAccount.getUser().getEmail()
                        + "," + checkingAccount.getUser().getBirthDate()
                        + "," + checkingAccount.getUser().getPhoneNumber()
                        + "," + checkingAccount.getUser().getPassword()
                        + "," + checkingAccount.getUser().getRegistrationDate()
                        + "," + checkingAccount.getUser().getTotalBalance()
                        + "," + checkingAccount.getOverdraftLimit() + "\n";
            } else if (account instanceof SavingsAccount) {
                SavingsAccount savingsAccount = (SavingsAccount) account;
                content += savingsAccount.getBankName()
                        + "," + savingsAccount.getAccountNumber()
                        + "," + savingsAccount.getBalance()
                        + "," + savingsAccount.getUser().getFullName()
                        + "," + savingsAccount.getUser().getId()
                        + "," + savingsAccount.getUser().getAddress()
                        + "," + savingsAccount.getUser().getEmail()
                        + "," + savingsAccount.getUser().getBirthDate()
                        + "," + savingsAccount.getUser().getPhoneNumber()
                        + "," + savingsAccount.getUser().getPassword()
                        + "," + savingsAccount.getUser().getRegistrationDate()
                        + "," + savingsAccount.getUser().getTotalBalance() + "\n";
            }
        }
        FileUtil.saveFile(ACCOUNTS_FILE_PATH, content, false);
    }

//        public static void saveUsers(ArrayList<User> userList) throws IOException {
//        String content = "";
//        for (User user : userList) {
//            content += user.getNombre() +
//                    "," + user.getApellido() +
//                    "," + user.getCedula() +
//                    "," + user.getFechaNacimiento() + "\n";
//        }
//        FileUtil.saveFile(EMPLOYEES_FILE_PATH, content, false);
//    }

    public static ArrayList<Account> loadAccounts() throws FileNotFoundException, IOException {
        ArrayList<Account> accounts = new ArrayList<>();
        ArrayList<String> content = FileUtil.readFile(ACCOUNTS_FILE_PATH);
        String line;
        for (String s : content) {
            line = s;
            String[] attributes = line.split(",");
            String bankName = attributes[0];
            String accountNumber = attributes[1];
            double balance = Double.parseDouble(attributes[2]);
            String fullName = attributes[3];
            String id = attributes[4];
            String address = attributes[5];
            String email = attributes[6];
            LocalDate birthDate = LocalDate.parse(attributes[7]);
            String phoneNumber = attributes[8];
            String password = attributes[9];
            LocalDate registrationDate = LocalDate.parse(attributes[10]);
            double totalBalance = Double.parseDouble(attributes[11]);

            User user = User.builder()
                    .fullName(fullName)
                    .id(id)
                    .address(address)
                    .email(email)
                    .birthDate(birthDate)
                    .phoneNumber(phoneNumber)
                    .password(password)
                    .registrationDate(registrationDate)
                    .totalBalance(totalBalance)
                    .build();

            if (attributes.length == 13) { // CheckingAccount
                double overdraftLimit = Double.parseDouble(attributes[12]);
                CheckingAccount checkingAccount = CheckingAccount.builder()
                        .bankName(bankName)
                        .accountNumber(accountNumber)
                        .balance(balance)
                        .user(user)
                        .overdraftLimit(overdraftLimit)
                        .build();
                accounts.add(checkingAccount);
            } else { // SavingsAccount
                SavingsAccount savingsAccount = SavingsAccount.builder()
                        .bankName(bankName)
                        .accountNumber(accountNumber)
                        .balance(balance)
                        .user(user)
                        .build();
                accounts.add(savingsAccount);
            }
        }
        return accounts;
    }

//    public static ArrayList<User> loadUsers() throws FileNotFoundException, IOException {
//        ArrayList<User> users = new ArrayList<User>();
//        ArrayList<String> content = FileUtil.readFile(EMPLOYEES_FILE_PATH);
//        String line = "";
//        for (int i = 0; i < content.size(); i++) {
//            line = content.get(i);
//            User user = new User();
//            user.setNombre(line.split(",")[0]);
//            user.setApellido(line.split(",")[1]);
//            user.setCedula(line.split(",")[2]);
//            user.setFechaNacimiento(line.split(",")[3]);
//            users.add(user);
//        }
//        return users;
//    }

    public static void saveLogRecord(String logMessage, int level, String action) {
        FileUtil.saveLogRecord(logMessage, level, action, LOG_FILE_PATH);
    }

    public static boolean login(String username, String password) throws FileNotFoundException, IOException, UserException {
        if (validateUser(username, password)) {
            return true;
        } else {
            throw new UserException("Usuario no existe");
        }
    }

    private static boolean validateUser(String username, String password) throws FileNotFoundException, IOException {
        ArrayList<User> users = PersistenceUtil.loadUsers(USERS_FILE_PATH);

        for (int userIndex = 0; userIndex < users.size(); userIndex++) {
            User userAux = users.get(userIndex);
            if (userAux.getFullName().equalsIgnoreCase(username) && userAux.getPassword().equalsIgnoreCase(password)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<User> loadUsers(String path) throws FileNotFoundException, IOException {
        ArrayList<User> users = new ArrayList<User>();
        ArrayList<String> content = FileUtil.readFile(path);
        String line = "";

        for (int i = 0; i < content.size(); i++) {
            line = content.get(i);
            User user = new User();
            user.setFullName(line.split(",")[0]);
            user.setPassword(line.split(",")[1]);
            users.add(user);
        }
        return users;
    }

    public static void saveObjects(ArrayList<User> userList, String path) throws IOException {
        String content = "";
        for (User userAux : userList) {
            content += userAux.getFullName() + "," + userAux.getId() + "," + userAux.getAddress()
                    + "," + userAux.getEmail() + "," + userAux.getBirthDate() + "," + userAux.getPhoneNumber()
                    + "," + userAux.getPassword() + "," + userAux.getRegistrationDate() + "," + userAux.getTotalBalance() + "\n";
        }
        FileUtil.saveFile(path, content, true);
    }

    public static VirtualWallet loadBinaryVirtualWalletResource() {
        VirtualWallet virtualWallet = null;
        try {
            virtualWallet = (VirtualWallet) FileUtil.loadSerializedResource(VIRTUAL_WALLET_MODEL_BINARY_FILE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return virtualWallet;
    }

    public static void saveBinaryVirtualWalletResource(VirtualWallet virtualWallet) {
        try {
            FileUtil.saveSerializedResource(VIRTUAL_WALLET_MODEL_BINARY_FILE_PATH, virtualWallet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static VirtualWallet loadXMLVirtualWalletResource() {
        VirtualWallet virtualWallet = null;
        try {
            virtualWallet = (VirtualWallet) FileUtil.loadSerializedXMLResource(VIRTUAL_WALLET_XML_FILE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return virtualWallet;
    }

    public static void saveXMLVirtualWalletResource(VirtualWallet virtualWallet) {
        try {
            FileUtil.saveSerializedXMLResource(VIRTUAL_WALLET_XML_FILE_PATH, virtualWallet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
