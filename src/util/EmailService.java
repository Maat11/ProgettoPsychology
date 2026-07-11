package util;

import java.io.IOException;
import java.io.InputStream;
import java.net.PasswordAuthentication;
import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailService {
	
	
	private static final Properties config = new Properties();

    // Questo blocco si esegue una sola volta all'avvio e carica i dati dal file
    static {
        try (InputStream input = EmailService.class.getClassLoader().getResourceAsStream("properties/email.properties")) {
            if (input == null) {
                System.err.println("Errore: Impossibile trovare il file email.properties in src/properties/");
            } else {
                config.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void inviaEmail(String destinatario, String oggetto, String testoEmail) {
        // 1. Configura le proprietà del server SMTP di Google
        Properties props = new Properties();
        props.put("mail.smtp.host", config.getProperty("mail.smtp.host"));
        props.put("mail.smtp.port", config.getProperty("mail.smtp.port"));
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Attiva la connessione sicura

        // 2. Recupera le tue credenziali dal file
        final String username = config.getProperty("mail.utente");
        final String password = config.getProperty("mail.password");

        // 3. Crea la sessione autenticata con Gmail
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new jakarta.mail.PasswordAuthentication(username, password);
            }
        });

        try {
            // 4. Costruisci il messaggio email
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Mittente
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario)); // Destinatario
            message.setSubject(oggetto); // Oggetto della mail
            message.setText(testoEmail); // Corpo del testo

            // 5. Spedisci l'email
            Transport.send(message);
            System.out.println("Email inviata correttamente via file .properties!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Errore durante l'invio dell'email: " + e.getMessage());
        }
    }
    
    
}
