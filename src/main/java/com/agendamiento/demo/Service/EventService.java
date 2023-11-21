package com.agendamiento.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendamiento.demo.Models.Dao.IEventDao;
import com.agendamiento.demo.Models.Entity.Event;

@Service
public class EventService {
    
    @Autowired
    private IEventDao eventDao;

    @Transactional
    public void saveEvent(Event event) {
        eventDao.save(event);
    }
}

