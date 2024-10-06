package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.RegisterController;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class RegisterViewController extends CoreViewController {
    private RegisterController registerController;

    @FXML
    private TextField txtIdentification;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtVerifyPassword;

    @FXML
    private DatePicker dpDateBirth;

    @FXML
    private TextField txtAddress;

    @FXML
    private Button btnRegistration;

    @FXML
    private Button btnReturn;

    @FXML
    void initialize() {
        registerController = new RegisterController();
    }

    @FXML
    public void onRegistration(ActionEvent actionEvent) {
        register(actionEvent);
    }

    @FXML
    void onReturn(ActionEvent actionEvent) {
        returnToStartup(actionEvent);
    }

    private void register(ActionEvent actionEvent) {
//        UserDto userDto = buildUserDto();
//        if (validateForm()) {
//            try {
//                registerController.registerUser(userDto);
//                showMessage("¡Registro exitoso!", "Bienvenido " + userDto.fullName(), "Te has registrado correctamente.", Alert.AlertType.INFORMATION);
//                closeWindow(actionEvent);
//                browseWindow("/user-data-view.fxml", "User Data View", actionEvent);
//            } catch (Exception e) {
//                showMessage("Error de registro", "No se pudo completar el registro", e.getMessage(), Alert.AlertType.ERROR);
//            }
//        } else {
//            showMessage("Error de validación", "Por favor, complete todos los campos", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
//        }
    }

    private UserDto buildUserDto() {
        return new UserDto(
                txtIdentification.getText(),
                txtName.getText(),
                txtPhone.getText(),
                txtEmail.getText(),
                txtPassword.getText(),
                dpDateBirth.getValue(),
                LocalDate.now(),
                txtAddress.getText(),
                0,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    private boolean validateForm() {
        return txtIdentification.getText() != null && !txtIdentification.getText().isEmpty()
                && txtName.getText() != null && !txtName.getText().isEmpty()
                && txtEmail.getText() != null && !txtEmail.getText().isEmpty()
                && txtAddress.getText() != null && !txtAddress.getText().isEmpty()
                && txtPassword.getText() != null && !txtPassword.getText().isEmpty()
                && txtPhone.getText() != null && !txtPhone.getText().isEmpty()
                && dpDateBirth.getValue() != null
                && txtPassword.getText().equals(txtVerifyPassword.getText());
    }

    private void returnToStartup(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/startup.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Banco - Inicio");
            stage.show();

            closeWindow(actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}