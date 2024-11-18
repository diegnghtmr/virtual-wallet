package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Function;

import javafx.scene.control.Alert.AlertType;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;

public abstract class CoreViewController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    protected void showMessage(String title, String header, String content, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        // Agregar ícono personalizado con tamaño aumentado
        ImageView icon = new ImageView(new Image(getClass().getResource("/img/information.png").toExternalForm()));
        icon.setFitWidth(80);  // Aumentado de 64 a 80
        icon.setFitHeight(80); // Aumentado de 64 a 80
        icon.setPreserveRatio(true);
        icon.setSmooth(true);
        alert.setGraphic(icon);

        // Aplicar estilos personalizados
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/custom-dialog.css").toExternalForm());

        // Aplicar clase de estilo según el tipo de alerta
        String styleClass = switch (alertType) {
            case CONFIRMATION -> "custom-confirmation";
            case ERROR -> "custom-error";
            case WARNING -> "custom-warning";
            default -> "custom-info";
        };
        dialogPane.getStyleClass().add(styleClass);

        // Personalizar botones
        alert.getButtonTypes().setAll(ButtonType.OK);
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDefaultButton(true);
        okButton.setText("Entendido");

        // Mantener el tamaño de la alerta
        dialogPane.setPrefWidth(400);
        dialogPane.setPrefHeight(250);

        // Mostrar alerta y esperar respuesta
        alert.showAndWait();
    }

    protected boolean showConfirmationMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Agregar ícono personalizado
        ImageView icon = new ImageView(new Image(getClass().getResource("/img/confirm-icon.png").toExternalForm()));
        icon.setFitWidth(80);
        icon.setFitHeight(80);
        icon.setPreserveRatio(true);
        icon.setSmooth(true);
        alert.setGraphic(icon);

        // Aplicar estilos personalizados
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/custom-dialog.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-confirmation");

        // Personalizar botones
        ButtonType okButton = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, cancelButton);

        // Ajustar el tamaño de la alerta
        dialogPane.setPrefWidth(400);
        dialogPane.setPrefHeight(250);

        // Aplicar estilos específicos a los botones
        Button okButtonNode = (Button) alert.getDialogPane().lookupButton(okButton);
        okButtonNode.getStyleClass().add("accept-button");

        Button cancelButtonNode = (Button) alert.getDialogPane().lookupButton(cancelButton);
        cancelButtonNode.getStyleClass().add("cancel-button");

        // Mostrar alerta y esperar respuesta
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == okButton;
    }

    protected  <T> void initializeComboBox(ComboBox<T> comboBox,
                                        ObservableList<T> items,
                                        Function<T, String> displayFunction) {
        comboBox.setItems(items);
        comboBox.setCellFactory(lv -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : displayFunction.apply(item));
            }
        });
        comboBox.setButtonCell(new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : displayFunction.apply(item));
            }
        });
    }

    public void browseWindow(String nameFileFxml, String titleWindow, ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nameFileFxml));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(titleWindow);
            stage.show();

            closeWindow(actionEvent);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void openWindow(String nameFileFxml, String titleWindow, Stage ownerStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nameFileFxml));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(titleWindow);

            // Establecer el propietario de la nueva ventana
            if (ownerStage != null) {
                stage.initOwner(ownerStage);
            }

            stage.show();

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

}
