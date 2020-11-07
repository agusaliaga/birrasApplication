package com.santander.birras.services.impl;

import com.santander.birras.services.EmailService;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {
    /**
     * PUNTO 3)
     * COMO USUARIO y ADMIN QUIERO RECIBIR NOTIFICACIONES PARA ESTAR AL TANTO DE LAS MEETUPS
     * */

    private final JavaMailSender emailSender;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Santander.Tecnologia.Meetups");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
