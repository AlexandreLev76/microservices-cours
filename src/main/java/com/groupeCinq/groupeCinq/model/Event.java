package com.groupeCinq.groupeCinq.model;

import java.util.concurrent.atomic.AtomicLong;

public class Event {
    
    private static final AtomicLong idGenerator = new AtomicLong(0);
    
    private Long id;
    private String name;
    private String description;
    
    public Event() {
        this.id = idGenerator.incrementAndGet();
    }
    
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}

