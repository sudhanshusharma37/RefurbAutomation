package utils;

import config.ConfigReader;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailUtils {

    public static void sendReportByEmail(String reportPath) {

        final String username = ConfigReader.get("email.username"); // Sender's email
        final String password = ConfigReader.get("email.password");       // App password
        // Set receiver email
        String toEmail = ConfigReader.get("email.recipients");
        String fileLink = "file:///" + reportPath.replace("\\", "/");

        // Email properties
        Properties props = new Properties();
        props.put("mail.smtp.host", ConfigReader.get("smtp.host"));  // for Gmail
        props.put("mail.smtp.port", ConfigReader.get("smtp.port"));
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS

        // Authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );
            message.setSubject(ConfigReader.get("email.subject"));

            // Message body + attachment
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(
                    "Hi,<br><br>" +
                            "Please find the attached test report.<br>" + "<br><br>Note: Please download this file at your local and access it" +
                            "<br><br>Regards,<br>Automation Team",
                    "text/html"
            );

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(reportPath); // Path to ExtentReports HTML

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            // Send email
            Transport.send(message);
            System.out.println("Report emailed successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

