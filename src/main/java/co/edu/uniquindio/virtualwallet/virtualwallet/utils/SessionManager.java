package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import co.edu.uniquindio.virtualwallet.virtualwallet.model.Person;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static SessionManager INSTANCE;
    private Map<String, LocalDate> sessions;

    private SessionManager() {
        sessions = new HashMap<>();
    }

    public static SessionManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SessionManager();
        }
        return INSTANCE;
    }

    public void addSession(Person person) {
        sessions.put(person.getEmail(), LocalDate.now());
    }

    public boolean hasLoggedInBefore(Person person) {
        return sessions.containsKey(person.getEmail());
    }
}
