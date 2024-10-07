package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import co.edu.uniquindio.virtualwallet.virtualwallet.model.Person;

public class Session {
    private Person person;
    private String verificationCode;
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

    public void setVerificationCode(String verificationCode) {
        INSTANCE.verificationCode = verificationCode;
    }

    public String getVerificationCode() {
        return INSTANCE.verificationCode;
    }
}
