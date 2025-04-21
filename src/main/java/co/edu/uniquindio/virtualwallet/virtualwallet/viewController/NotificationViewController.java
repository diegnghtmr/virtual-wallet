package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.NotificationUtil;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.enums.NotificationType;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.INotificationViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class NotificationViewController extends CoreViewController implements INotificationViewController {
    private ObservableList<NotificationUtil> notificationList;

    @FXML
    private Button btnCloseNotifications;

    @FXML
    private Button btnReturnToUserData;

    @FXML
    private TableView<NotificationUtil> tblNotification;

    @FXML
    private TableColumn<NotificationUtil, String> tcMessage;

    @FXML
    private TableColumn<NotificationUtil, String> tcDate;

    @FXML
    private TableColumn<NotificationUtil, String> tcType;

    @FXML
    private TextField txtFilter;

    @FXML
    void onCloseNotifications(ActionEvent event) {
        closeWindow(event);
    }

    @FXML
    void onReturnToUserData(ActionEvent event) {
        returnToUserData(event);
    }

    public void initialize() {
        User user = (User) Session.getInstance().getPerson();
        notificationList = FXCollections.observableArrayList(user.getNotificationUtils());
        loadNotifications();
        setupFilter(notificationList);
    }

    private void loadNotifications() {
        // Configurar las columnas
        tcDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        tcMessage.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMessage()));
        tcType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocalizedType()));

        // Cargar las notificaciones en la tabla
        tblNotification.setItems(notificationList);
    }

    private void returnToUserData(ActionEvent event) {
        // Obtener el Stage de la ventana de notificaciones
        Stage notificationStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Obtener el Stage del UserContainer (propietario)
        Stage userContainerStage = (Stage) notificationStage.getOwner();

        // Cerrar la ventana de notificaciones
        notificationStage.close();

        // Cerrar la ventana del UserContainer
        if (userContainerStage != null) {
            userContainerStage.close();
        }

        // Navegar a la vista de datos del usuario
        openWindow("/view/user-data-view.fxml", "Datos de Usuario", null);
    }

    private void setupFilter(ObservableList<NotificationUtil> notificationList) {
        txtFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<NotificationUtil> filteredList = filteredList(notificationList, newValue);
            tblNotification.setItems(filteredList);
        });
    }

    private ObservableList<NotificationUtil> filteredList(ObservableList<NotificationUtil> originalList, String searchText) {
        List<NotificationUtil> filteredList = new ArrayList<>();
        for (NotificationUtil notification : originalList) {
            if (searchFindsNotification(notification, searchText)) {
                filteredList.add(notification);
            }
        }
        return FXCollections.observableList(filteredList);
    }

    private boolean searchFindsNotification(NotificationUtil notification, String searchText) {
        return (notification.getMessage().toLowerCase().contains(searchText.toLowerCase())) ||
                (notification.getDate().toString().toLowerCase().contains(searchText.toLowerCase())) ||
                (notification.getLocalizedType().toLowerCase().contains(searchText.toLowerCase()));
    }

}
