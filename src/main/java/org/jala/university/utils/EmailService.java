package org.jala.university.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService() {
        this.mailSender = configureJavaMailSender();
    }

    private JavaMailSender configureJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("jalaubankapp@gmail.com");
        mailSender.setPassword("hbzi swdr ymhj dgoc");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.localhost", "localhost");

        return mailSender;
    }

    public String sendEmail(String receiver, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("jalaubankapp@gmail.com");
            message.setTo(receiver);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            return "Mail sent successfully!";
        } catch (Exception e) {
            return "Error while sending mail: " + e.getMessage();
        }
    }
}
