package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import co.edu.uniquindio.virtualwallet.virtualwallet.utils.enums.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString
public class NotificationUtil {
    private String message;
    private LocalDate date;
    private NotificationType type;

    public NotificationUtil(String message, LocalDate date, NotificationType type) {
        this.message = message;
        this.date = date;
        this.type = type;
    }

    /**
     * Obtiene el tipo de notificación traducido al idioma actual.
     *
     * @return El tipo de notificación en el idioma actual.
     */
    public String getLocalizedType() {
        String key = "notification.type." + this.type.name();
        return I18n.get(key);
    }

}

