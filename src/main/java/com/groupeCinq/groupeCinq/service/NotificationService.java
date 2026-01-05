package com.groupeCinq.groupeCinq.service;

import com.groupeCinq.groupeCinq.model.Notification;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.SendEmailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    
    @Value("${resend.api.key}")
    private String resendApiKey;
    
    @Value("${resend.from.email:onboarding@resend.dev}")
    private String fromEmail;
    
    public void createNotification(String email, String subject, String body) {
        Notification notification = new Notification(email, subject, body);
        processNotification(notification);
    }
    
    @Async
    public void processNotification(Notification notification) {
        try {
            Resend resend = new Resend(resendApiKey);
            
            SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                    .from(fromEmail)
                    .to(notification.getEmail())
                    .subject(notification.getSubject())
                    .html("<p>" + notification.getBody() + "</p>")
                    .build();
            
            resend.emails().send(sendEmailRequest);
            
            notification.setStatus(Notification.NotificationStatus.SENT);
            
            System.out.println("OK c'est envoyé à " + notification.getEmail());
            
        } catch (ResendException e) {
            System.err.println("Erreur Resend: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
