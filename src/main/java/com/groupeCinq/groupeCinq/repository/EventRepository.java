package com.groupeCinq.groupeCinq.repository;

import com.groupeCinq.groupeCinq.model.Event;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EventRepository {
    
    private final Map<Long, Event> events = new ConcurrentHashMap<>();
    
    public Event save(Event event) {
        events.put(event.getId(), event);
        return event;
    }
    
    public Event findById(Long id) {
        return events.get(id);
    }
    
    public List<Event> findAll() {
        return new ArrayList<>(events.values());
    }
    
    public void deleteById(Long id) {
        events.remove(id);
    }
}

