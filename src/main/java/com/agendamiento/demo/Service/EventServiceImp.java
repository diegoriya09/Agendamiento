package com.agendamiento.demo.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendamiento.demo.Models.Dao.IEventDao;
import com.agendamiento.demo.Models.Entity.Event;

@Service
public class EventServiceImp implements EventService {

    private final IEventDao eventDao;

    public EventServiceImp(IEventDao eventDao) {
        this.eventDao = eventDao;
    }
    
    @Transactional
    @Override
    public Event createEvent(Event event) {
        return eventDao.save(event);
    }  
}
