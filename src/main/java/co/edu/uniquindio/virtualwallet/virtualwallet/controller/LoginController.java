package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.model.Person;

public class LoginController {
    private final ModelFactory modelFactory = ModelFactory.getInstance();

    public Person validateLogin(String email, String password) throws Exception {
        return modelFactory.validateLogin(email, password);
    }
}