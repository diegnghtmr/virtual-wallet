package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.UserDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.EmailUtil;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;

public class ValidationController extends CoreController {
    public ValidationController() {
        super();
    }

    public boolean verifyCode(String verificationCode) {
        String sessionVerificationCode = Session.getInstance().getVerificationCode();
        return sessionVerificationCode != null && sessionVerificationCode.equals(verificationCode);
    }

    public void resendVerificationCode(String email) {
        String subject = "Nuevo código de verificación cuenta VirtualWallet";
        String message = "Su nuevo código de verificación es: " + modelFactory.generateRandomCode() + ", gracias por registrarse en nuestra app.";
        EmailUtil emailUtil = new EmailUtil(email, subject, message);
        emailUtil.sendNotification();
    }
}
