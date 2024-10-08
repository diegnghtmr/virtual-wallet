package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.model.Person;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.SessionManager;

public class LoginController {
    private final ModelFactory modelFactory = ModelFactory.getInstance();

    public Person validateLogin(String email, String password) throws Exception {
        return modelFactory.validateLogin(email, password);
    }

    public void saveSession(Person validatedUser) {
        Session.getInstance().setPerson(validatedUser);
    }

    public boolean isVerified() {
        return modelFactory.isVerified();
    }

}