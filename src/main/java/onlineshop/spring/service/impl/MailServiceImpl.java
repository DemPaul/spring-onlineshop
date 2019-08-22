package onlineshop.spring.service.impl;

import onlineshop.spring.entity.Code;
import org.apache.log4j.Logger;
import onlineshop.spring.service.MailService;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger mailServiceLogger = Logger.getLogger(MailServiceImpl.class);

    @Override
    public void sendConfirmCode(Code code) {

        final String username = "temail884@gmail.com";
        final String password = "12Qwert12";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(code.getEmail())
            );
            message.setSubject("Single-use code to confirm the purchase");
            message.setText("Your confirmation code: " + code.getValue());

            Transport.send(message);

        } catch (MessagingException e) {
            mailServiceLogger.error("Problem with sending confirmation code", e);
        }
    }
}
