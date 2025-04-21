package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import co.edu.uniquindio.virtualwallet.virtualwallet.model.Person;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;

public class Session {
    private Person person;
    private static Session INSTANCE;

    private Session() {
    }

    public static Session getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Session();
        }
        return INSTANCE;
    }

    public void setPerson(Person person) {
        INSTANCE.person = person;
    }

    public Person getPerson() {
        return INSTANCE.person;
    }

    public void closeSession() {
        INSTANCE.person = null;
    }

}
