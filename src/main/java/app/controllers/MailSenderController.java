package app.controllers;

import java.util.Properties;

import app.entities.Carport;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class MailSenderController {

    
    public static void sendCarportDetailsEmail(Carport carport, String emailRecipient) {
        // Provide recipient's email ID
        String to = "fog.carports@gmail.com"; // fog.carports@gmail.com is the test mail
        // Provide sender's email ID (your Gmail email address)
        String from = "fog.carports@gmail.com";
        // Provide your Gmail email address and App Password
        final String username = "fog.carports@gmail.com";
        final String password = "zmkl yqfr jvrc pzua";
        // Update the host for Gmail
        String host = "smtp.gmail.com";
        // Configure Gmail's SMTP server details
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        // Create the Session object
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);
            // Set From email field
            message.setFrom(new InternetAddress(from));
            // Set To email field
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set email subject field
            message.setSubject("A new carport has been ordered!");
            // Construct the email body
            String emailBody = sendOrderToSeller(carport);
            // Set the content of the email message
            message.setText(emailBody);
            // Send the email message
            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String sendOrderToSeller(Carport carport) {

        StringBuilder builder = new StringBuilder();

        // send information about the carport to the email body
        builder.append("Carport Details:\n");
        builder.append("Width: ").append(carport.getWidth()).append("\n");
        builder.append("Length: ").append(carport.getLength()).append("\n");
        builder.append("Roof: ").append(carport.getRoof()).append("\n");

        // Check if the carport includes a shed
        if (carport.getShed() != null) {
            // If a shed is included, append its details to the email body
            builder.append("Shed Width: ").append(carport.getShed().getWidth()).append("\n");
            builder.append("Shed Length: ").append(carport.getShed().getLength());
        }

        // Return the complete email body as a string
        return builder.toString();
    }

}