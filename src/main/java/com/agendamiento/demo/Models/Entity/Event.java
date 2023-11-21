package com.agendamiento.demo.Models.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String eventName;
    private String eventTimeFrom;
    private String eventTimeTo;

    @ManyToOne
    private User user;

    public Event() {

    }

    public Event(String eventName, String eventTimeFrom, String eventTimeTo, User user) {
        this.eventName = eventName;
        this.eventTimeFrom = eventTimeFrom;
        this.eventTimeTo = eventTimeTo;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventTimeFrom() {
        return eventTimeFrom;
    }

    public void setEventTimeFrom(String eventTimeFrom) {
        this.eventTimeFrom = eventTimeFrom;
    }

    public String getEventTimeTo() {
        return eventTimeTo;
    }

    public void setEventTimeTo(String eventTimeTo) {
        this.eventTimeTo = eventTimeTo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
