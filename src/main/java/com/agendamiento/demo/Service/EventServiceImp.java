package com.agendamiento.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendamiento.demo.Models.Dao.IEventDao;
import com.agendamiento.demo.Models.Entity.Event;

@Service
public class EventServiceImp implements EventService {

    @Autowired
    private IEventDao eventDao;

    @Transactional(readOnly=true)
    @Override
    public Event createEvent(Event event) {
        return eventDao.save(event);
    }  
}
