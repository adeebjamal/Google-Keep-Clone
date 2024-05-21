package com.example.GoogleKeepClone.Utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailUtilityImpl implements EmailUtility {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String email, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(subject);
            message.setText(body);
            this.javaMailSender.send(message);
            System.out.println("Email sent successfully...");
        }
        catch(Exception e) {
            System.out.println("EmailUtilityImpl : sendEmail");
            System.out.println(e.getMessage());
        }
    }

}
