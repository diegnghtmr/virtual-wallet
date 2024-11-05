package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class I18n {
    private static ResourceBundle bundle;
    private static Locale locale = new Locale("es");


    static {
        loadResourceBundle();
    }

    private static void loadResourceBundle() {
        bundle = ResourceBundle.getBundle("i18n.messages", locale);
    }

    public static void setLocale(Locale newLocale) {
        locale = newLocale;
        loadResourceBundle();
    }

    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return "!" + key + "!";
        }
    }
}
