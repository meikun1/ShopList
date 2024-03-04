package com.example.shoplist;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
    public static void main(String[] args) {
        // Адрес SMTP-сервера и порт
        String host = "smtp.mail.ru";
        int port = 465; // Используйте 587 для TLS

        // Учетные данные пользователя
        String username = "brailov.99@mail.ru";
        String password = "YxtrzUHkzGum9drVKT5P";

        // Адрес получателя
        String to = "brailov.99@mail.ru";

        // Настройка свойств
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.ssl.enable", "true");

        // Создание сессии
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Создание сообщения
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Test Subject");
            message.setText("Test Message");

            // Отправка сообщения
            Transport.send(message);

            System.out.println("Message sent successfully.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
