package app.controllers;

import java.util.Properties;

import app.entities.Carport;
import app.entities.Order;
import app.entities.User;
import io.javalin.http.Context;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class MailSenderController {


    public static void sendCarportDetailsEmail(Carport carport, String emailRecipient, String userName, int userPhoneNumber, Context ctx) {
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
            String emailBody = sendOrderToSeller(carport, userName, userPhoneNumber, ctx);
            // Set the content of the email message
            message.setText(emailBody);
            // Send the email message
            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendDetailsToCustomerWithoutLogin(Carport carport, String emailRecipient, String userName, int userPhoneNumber, String userEmail) {
        // Provide recipient's email ID
        String to = emailRecipient; // fog.carports@gmail.com is the test mail
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
            message.setSubject("Tak for din bestilling!");
            // Construct the email body
            String emailBody = sendOrderToCustomerWithoutLogin(carport, userName, userPhoneNumber, userEmail);
            // Set the content of the email message
            message.setText(emailBody);
            // Send the email message
            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendDetailsToCustomerWithLogin(Carport carport, String emailRecipient, String userName) {
        // Provide recipient's email ID
        String to = emailRecipient; // fog.carports@gmail.com is the test mail
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
            message.setSubject("Tak for din bestilling!");
            // Construct the email body
            String emailBody = sendOrderToCustomerLogin(carport, userName);
            // Set the content of the email message
            message.setText(emailBody);
            // Send the email message
            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String sendOrderToSeller(Carport carport, String userName, int userPhoneNumber, Context ctx) {

        StringBuilder builder = new StringBuilder();

        // send information about the carport to the email body
        builder.append("Carport Detaljer:\n");

        User currentUser = ctx.sessionAttribute("currentUser");

        if(currentUser != null) {
            builder.append("Navn: ").append(currentUser.getFirstName()).append("\n");
            builder.append("Telefon nummer: ").append(currentUser.getPhoneNumber()).append("\n");
        } else {
            builder.append("Navn: ").append(userName).append("\n");
            builder.append("Telefon nummer: ").append(userPhoneNumber).append("\n");
        }
        builder.append("Bredde: ").append(carport.getWidth()).append("\n");
        builder.append("Længde: ").append(carport.getLength()).append("\n");
        builder.append("Tag: ").append(carport.getRoof()).append("\n");


        // Check if the carport includes a shed
        if (carport.getShed() != null) {
            // If a shed is included, append its details to the email body
            builder.append("Skur bredde: ").append(carport.getShed().getWidth()).append("\n");
            builder.append("Skur længde: ").append(carport.getShed().getLength());
        }

        builder.append("\nTak for at du vælger at handle hos FOG \n");
        builder.append("Venlig hilsen,\nFOG Team");

        // Return the complete email body as a string
        return builder.toString();
    }

    private static String sendOrderToCustomerWithoutLogin(Carport carport, String userName, int userPhoneNumber, String userEmail) {

        StringBuilder builder = new StringBuilder();

        // send information about the details to the email body
        builder.append("Hej ").append(userName).append(",\n");
        builder.append("Tusind tak for din bestilling! Vi har nu oprettet en bruger til dig, så du kan se din ordre status:\n");
        builder.append("Email: ").append(userEmail).append("\n");
        builder.append("Kodeord: ").append(userPhoneNumber).append("\n");
        builder.append("Detaljer på din carport:\n");
        builder.append("Bredde: ").append(carport.getWidth()).append("\n");
        builder.append("Længde: ").append(carport.getLength()).append("\n");
        builder.append("Tag: ").append(carport.getRoof()).append("\n");

        // Check if the carport includes a shed
        if (carport.getShed() != null) {
            // If a shed is included, append its details to the email body
            builder.append("Skur bredde: ").append(carport.getShed().getWidth()).append("\n");
            builder.append("Skur længde: ").append(carport.getShed().getLength());
        }

        builder.append("Du vil snart modtage en email med yderligere informationer om din ordre.\n");
        builder.append("\nTak for at du vælger at handle hos FOG \n");
        builder.append("Venlig hilsen,\nFOG Team");


        // Return the complete email body as a string
        return builder.toString();
    }

    private static String sendOrderToCustomerLogin(Carport carport, String userName) {

        StringBuilder builder = new StringBuilder();

        // send information about the details to the email body
        builder.append("Hej ").append(userName).append(",\n");
        builder.append("Tusind tak for din bestilling! Her er din ordre bekræftigelse\n");
        builder.append("Detaljer på din carport:\n");
        builder.append("Bredde: ").append(carport.getWidth()).append("\n");
        builder.append("Længde: ").append(carport.getLength()).append("\n");
        builder.append("Tag: ").append(carport.getRoof()).append("\n");

        // Check if the carport includes a shed
        if (carport.getShed() != null) {
            // If a shed is included, append its details to the email body
            builder.append("Skur bredde: ").append(carport.getShed().getWidth()).append("\n");
            builder.append("Skur længde: ").append(carport.getShed().getLength());
        }

        builder.append("Du vil snart modtage en email med yderligere informationer om din ordre.\n");
        builder.append("\nTak for at du vælger at handle hos FOG \n");
        builder.append("Venlig hilsen,\nFOG Team");

        // Return the complete email body as a string
        return builder.toString();
    }

    // Status Mail to Customer
    public static void sendStatusToCustomer(Order order, String status, String emailRecipient) {
        // Provide recipient's email ID
        String to = emailRecipient;
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
            message.setSubject("Status på din ordre!");
            // Construct the email body
            String emailBody = sendOrderStatus(order, status, emailRecipient);
            // Set the content of the email message
            message.setText(emailBody);
            // Send the email message
            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    private static String sendOrderStatus(Order order, String status, String emailRecipeint) {
        StringBuilder builder = new StringBuilder();


        builder.append("Hej!\n");

        // Choosing message based on order status
        switch (order.getStatus().toLowerCase()) {
            case "under process":
                builder.append("Din ordre er nu under behandling.\n");
                break;
            case "paid":
                builder.append("Vi har modtaget din betaling, og din ordre er nu i gang.\n");
                break;
            case "cancelled":
                builder.append("Din ordre er blevet annulleret.\n");
                break;
            default:
                builder.append("Kontakt os for yderligere information om din ordre.\n");
        }


        builder.append("Din ordre #").append(order.getOrderNr()).append("\n");
        builder.append("Ordre Status: ").append(order.getStatus()).append("\n\n");

        builder.append("\nTak for at du vælger at handle hos FOG \n");
        builder.append("Venlig hilsen,\nFOG Team");

        // Return the complete email body as a string
        return builder.toString();
    }



}