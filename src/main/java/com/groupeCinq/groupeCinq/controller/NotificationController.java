package com.groupeCinq.groupeCinq.controller;

import com.groupeCinq.groupeCinq.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;
    
    @PostMapping("/email")
    public ResponseEntity<Void> createEmailNotification(
            @RequestParam String email,
            @RequestParam String subject,
            @RequestParam String body) {
        
        notificationService.createNotification(email, subject, body);
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
