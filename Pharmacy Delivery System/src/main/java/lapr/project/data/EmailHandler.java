package lapr.project.data;

import javax.mail.Session;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The type Email handler.
 */
public class EmailHandler {

    /**
     * The Session.
     */
    private final Session sessao;

    /**
     * The User Name.
     */
    private final String username;

    /**
     * Instantiates a new Email handler.
     *
     * @throws IOException the io exception
     */
    public EmailHandler() throws IOException {
        Properties appProperties = new Properties();
        String propFileName = "application.properties";

        InputStream inptStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inptStream != null) {
            appProperties.load(inptStream);
        } else {
            throw new FileNotFoundException("Application property does not exist");
        }

        inptStream.close();

        username = appProperties.getProperty("email.from", "g032lapr3@outlook.pt");
        final String password = appProperties.getProperty("email.password", "manafasemcovid1");
        String host = appProperties.getProperty("email.host", "smtp.office365.com");
        String port = appProperties.getProperty("email.port", "587");

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        sessao = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

    }


    /**
     * Send email boolean.
     *
     * @param dest    the dest
     * @param subject the subject
     * @param content the content
     * @return the boolean
     */
    public boolean sendEmail(String dest, String subject, String content) {
        try {
            Message msg = new MimeMessage(sessao);
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dest));
            msg.setSubject(subject);
            msg.setText(content);
            Transport.send(msg);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }

    }
}
