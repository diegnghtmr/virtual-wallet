package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.NotificationUtil;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.enums.NotificationType;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.INotificationViewController;
import javafx.collections.FXCollections;
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

    @FXML
    private Button btnCloseNotifications;

    @FXML
    private Button btnReturnToUserData;

    @FXML
    private TableView<NotificationUtil> tblNotification;

    @FXML
    private TableColumn<NotificationUtil, String> tcMessage;

    @FXML
    private TableColumn<NotificationUtil, LocalDate> tcDate;

    @FXML
    private TableColumn<NotificationUtil, NotificationType> tcType;

    @FXML
    private TextField txtFilter;

    @FXML
    void onCloseNotifications(ActionEvent event) {
        closeWindow(event);
    }

    @FXML
    void onReturnToUserData(ActionEvent event) {
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

    public void initialize() {
        User user = (User) Session.getInstance().getPerson();

        // Configurar las columnas
        tcMessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tcType.setCellValueFactory(new PropertyValueFactory<>("type"));

        // Cargar las notificaciones en la tabla
        tblNotification.setItems(FXCollections.observableArrayList(user.getNotificationUtils()));
    }
}
