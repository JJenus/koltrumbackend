package com.koltrum.koltrum.service;

import com.koltrum.koltrum.model.AppUser;
import com.koltrum.koltrum.util.email_templates.EmailResetPassword;
import com.koltrum.koltrum.util.email_templates.EmailWelcome;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;


@Component
public class EmailSender {
    JavaMailSenderImpl mailSender;
    // SMTP server configuration
    final String smtpHost = "smtp.zoho.com";
    final int smtpPort = 465; // Use the appropriate port for your SMTP server

    // Sender's email credentials
    final String senderEmail = "noreply@koltrum.net";
    final String senderPassword = "@Koltrum4431";

    // Recipient's email address
    String recipientEmail;


    // Email subject and content
    String emailSubject = "Koltrum";
    String emailContent;

    public void sendReset(AppUser appUser, String token){
        String userName = StringUtils.capitalize(appUser.getName());

        String message = this.getPasswordResetTemp(userName, token);
        String subject = "Reset Password";

        this.sendMail(appUser.getEmail(), subject, message);
    }

    public void sendWelcomeMail(AppUser user){
        String capName = StringUtils.capitalize(user.getName());
        String subject = "Welcome to Koltrum";

        String message = this.getWelcomeTemp(capName);

        this.sendMail(user.getEmail(), subject, message);
    }

    private String getWelcomeTemp(String name) {
        String temp = EmailWelcome.TEMPLATE;
        temp = temp.replaceAll("\\{\\{ name \\}\\}", name);

        return temp;
    }

    private void initHeaders(){
        mailSender = new JavaMailSenderImpl();

        mailSender.setHost(smtpHost);
        mailSender.setPort(smtpPort);
        mailSender.setUsername(senderEmail);
        mailSender.setPassword(senderPassword);

        // Set SMTP server properties
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    }

    private void sendMail(String toEmail, String subject, String content){
        this.initHeaders();

        this.recipientEmail = toEmail;
        this.emailContent = content;
        this.emailSubject = subject;

        try {
            // Create the email message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = null;

            messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(senderEmail);
            messageHelper.setTo(recipientEmail);
            messageHelper.setSubject(emailSubject);
            messageHelper.setText(emailContent, true);

            // Send the email
            mailSender.send(message);
            
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
//            System.out.println("Failed to send email. Error message: " + e.getMessage());
        }
    }

    private String getPasswordResetTemp(String name, String token){
        String temp = EmailResetPassword.TEMPLATE;
        temp = temp.replaceAll("\\{\\{ token \\}\\}", token);
        temp = temp.replaceAll("\\{\\{ name \\}\\}", name);

        return temp;
    }

    public String getTemplate(String file) {

        File resource = null;
        String template = "";
        try {
            resource = new ClassPathResource("templates/firramo/"+file).getFile();
            template = new String(Files.readAllBytes(resource.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return template;
    }
}

