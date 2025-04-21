package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.UserDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.EmailUtil;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;

public class ValidationController extends CoreController {
    public ValidationController() {
        super();
    }

    public boolean verifyCode(String verificationCode) {
        return modelFactory.verifyCode(verificationCode);
    }

    public void resendVerificationCode(String id, String email) {
        String subject = "Nuevo código de verificación cuenta VirtualWallet";
        String newVerificationCode = modelFactory.generateRandomCode();
        String message = "Su nuevo código de verificación es: " + newVerificationCode + ", gracias por registrarse en nuestra app.";
        modelFactory.setVerificationCode(id, newVerificationCode);

        EmailUtil emailUtil = new EmailUtil(email, subject, message);
        emailUtil.sendNotification();
    }
}
