package co.edu.uniquindio.virtualwallet.virtualwallet.utils;


import lombok.AllArgsConstructor;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

@AllArgsConstructor
public class EmailUtil  {

//    private String recipient, subject, message;
//
//    @Override
//    public void sendNotification() {
//        Email email = EmailBuilder.startingBlank()
//                .from("uniEventos8@gmail.com")
//                .to(recipient)
//                .withSubject(subject)
//                .withPlainText(message)
//                .buildEmail();
//
//        try (Mailer mailer = MailerBuilder
//                .withSMTPServer("smtp.gmail.com", 587, "uniEventos8@gmail.com", "u s s v t t m l e z q j z u p x")
//                .withTransportStrategy(TransportStrategy.SMTP_TLS)
//                .withDebugLogging(true)
//                .buildMailer()) {
//            mailer.sendMail(email);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}