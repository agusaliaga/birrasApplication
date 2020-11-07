package com.santander.birras.services;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);
}
