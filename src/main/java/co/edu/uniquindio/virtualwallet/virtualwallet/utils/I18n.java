package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import lombok.Getter;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Getter
public class I18n {
    private static ResourceBundle bundle;
    private static Locale locale = new Locale("es"); // Idioma por defecto

    static {
        loadResourceBundle();
    }

    private static void loadResourceBundle() {
        try {
            bundle = ResourceBundle.getBundle("i18n.messages", locale);
        } catch (MissingResourceException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cambia el locale y recarga el ResourceBundle.
     *
     * @param newLocale El nuevo locale a establecer.
     */
    public static void setLocale(Locale newLocale) {
        locale = newLocale;
        loadResourceBundle();
    }

    /**
     * Obtiene el valor asociado a una clave.
     *
     * @param key La clave para la cual se desea obtener el valor.
     * @return El valor traducido o una cadena indicando que falta la clave.
     */
    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return "!" + key + "!";
        }
    }

    /**
     * Obtiene el valor asociado a una clave y reemplaza los placeholders con los argumentos proporcionados.
     *
     * @param key  La clave para la cual se desea obtener el valor.
     * @param args Los argumentos para reemplazar en los placeholders.
     * @return El mensaje formateado con los argumentos.
     */
    public static String getFormatted(String key, Object... args) {
        String pattern = get(key);
        return MessageFormat.format(pattern, args);
    }
}
