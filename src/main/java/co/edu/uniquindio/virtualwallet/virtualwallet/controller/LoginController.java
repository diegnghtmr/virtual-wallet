package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.model.Person;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.SessionManager;

public class LoginController {
    private final ModelFactory modelFactory = ModelFactory.getInstance();

    public Person validateLogin(String email, String password) throws Exception {
        Person user = modelFactory.validateLogin(email, password);
        SessionManager.getInstance().addSession(user);
        return user;
    }

    public void saveSession(Person validatedUser) {
        modelFactory.saveSession(validatedUser);
    }

    public boolean isFirstLogin(Person validatedUser) {
        return modelFactory.isFirstLogin(validatedUser);
    }
}