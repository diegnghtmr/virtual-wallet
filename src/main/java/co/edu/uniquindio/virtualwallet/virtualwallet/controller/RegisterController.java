package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.UserDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.EmailUtil;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;

import java.util.List;

public class RegisterController extends CoreController {
    private ModelFactory modelFactory;

    public RegisterController() {
        this.modelFactory = ModelFactory.getInstance();
    }

    public boolean registerUser(UserDto userDto) {
        return modelFactory.registerUser(userDto);
    }

    public void sendVerificationCode(UserDto userDto) {
        String email = userDto.email();
        String subject = "Código de verificación cuenta VirtualWallet";
        String verificationCode = modelFactory.generateRandomCode();
        String message = "Hola " + userDto.fullName() + ", su código de verificación es: " + verificationCode + ". Gracias por registrarse en nuestra app.";

        modelFactory.setVerificationCode(userDto.id(), verificationCode);

        EmailUtil emailUtil = new EmailUtil(email, subject, message);
        emailUtil.sendNotification();
    }
}