package com.groupeCinq.groupeCinq.service;

import com.groupeCinq.groupeCinq.model.Event;
import com.groupeCinq.groupeCinq.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    @Value("${notification.email:alexandre.levenez@ecoles-epsi.net}")
    private String notificationEmail;
    
    public Event create(Event event) {
        Event savedEvent = eventRepository.save(event);
        notificationService.createNotification(
                notificationEmail,
                "Événement créé",
                "L'événement \"" + savedEvent.getName() + "\" a été créé."
        );
        return savedEvent;
    }
    
    public Event findById(Long id) {
        Event event = eventRepository.findById(id);
        if (event != null) {
            notificationService.createNotification(
                    notificationEmail,
                    "Événement consulté",
                    "L'événement \"" + event.getName() + "\" a été consulté."
            );
        }
        return event;
    }
    
    public List<Event> findAll() {
        List<Event> events = eventRepository.findAll();
        notificationService.createNotification(
                notificationEmail,
                "Liste des événements consultée",
                events.size() + " événement(s) consulté(s)."
        );
        return events;
    }
    
    public Event update(Long id, Event event) {
        Event existingEvent = eventRepository.findById(id);
        if (existingEvent != null) {
            existingEvent.setName(event.getName());
            existingEvent.setDescription(event.getDescription());
            Event updatedEvent = eventRepository.save(existingEvent);
            notificationService.createNotification(
                    notificationEmail,
                    "Événement modifié",
                    "L'événement \"" + updatedEvent.getName() + "\" a été modifié."
            );
            return updatedEvent;
        }
        return null;
    }
    
    public void delete(Long id) {
        Event event = eventRepository.findById(id);
        if (event != null) {
            String eventName = event.getName();
            eventRepository.deleteById(id);
            notificationService.createNotification(
                    notificationEmail,
                    "Événement supprimé",
                    "L'événement \"" + eventName + "\" a été supprimé."
            );
        }
    }
}

