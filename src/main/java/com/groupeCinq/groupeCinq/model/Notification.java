package com.groupeCinq.groupeCinq.model;

import java.util.concurrent.atomic.AtomicLong;

public class Notification {
    
    private static final AtomicLong idGenerator = new AtomicLong(0);
    
    private Long id;
    private String email;
    private String subject;
    private String body;
    private NotificationStatus status;
    
    public Notification() {
        this.id = idGenerator.incrementAndGet();
        this.status = NotificationStatus.CREATED;
    }
    
    public Notification(String email, String subject, String body) {
        this();
        this.email = email;
        this.subject = subject;
        this.body = body;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public String getBody() {
        return body;
    }
    
    public void setStatus(NotificationStatus status) {
        this.status = status;
    }
    
    public enum NotificationStatus {
        CREATED, SENT
    }
}
