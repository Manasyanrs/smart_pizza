package com.example.smartpizza.service;

public interface MailService {

    void sendOrderCheck(String to, String subject, String text);
}
