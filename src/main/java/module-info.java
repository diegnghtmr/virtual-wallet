module co.edu.uniquindio.virtualwallet.virtualwallet {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens co.edu.uniquindio.virtualwallet.virtualwallet to javafx.fxml;
    exports co.edu.uniquindio.virtualwallet.virtualwallet;
}