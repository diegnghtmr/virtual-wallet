package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.UserDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.EmailUtil;

public class RegisterController extends CoreController {
    private ModelFactory modelFactory;

//    public RegisterController() {
//        this.modelFactory = ModelFactory.getInstance();
//    }
//
//    public boolean registerUser(UserDto userDto) {
//        return modelFactory.registerUser(userDto);
//    }
//
//    public void sendVerificationCode(String email) {
//        String verificationCode = generateVerificationCode();
//        String subject = "Código de verificación";
//        String message = "Hola, su código de verificación es: " + verificationCode;
//        EmailUtil.sendNotification(email, subject, message);
//    }
//
//    private String generateVerificationCode() {
//        // Implement code generation logic
//        return "123456";
//    }
}