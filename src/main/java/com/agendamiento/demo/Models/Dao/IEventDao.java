package com.agendamiento.demo.Models.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendamiento.demo.Models.Entity.Event;

public interface IEventDao extends JpaRepository<Event, Long>{
    
}
