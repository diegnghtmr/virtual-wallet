package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import co.edu.uniquindio.virtualwallet.virtualwallet.services.Notification;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import lombok.AllArgsConstructor;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;


import java.io.File;

@AllArgsConstructor
public class EmailAttachmentUtil implements Notification {

    private String recipient;
    private String subject;
    private String message;
    private File attachment;

    @Override
    public void sendNotification() {
        DataSource dataSource = new FileDataSource(attachment);

        Email email = EmailBuilder.startingBlank()
                .from("virtualwalletuq@gmail.com")
                .to(recipient)
                .withSubject(subject)
                .withPlainText(message)
                .withAttachment(attachment.getName(), dataSource)
                .buildEmail();

        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "virtualwalletuq@gmail.com", "ahfr tfcx btnp ambz")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {
            mailer.sendMail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
