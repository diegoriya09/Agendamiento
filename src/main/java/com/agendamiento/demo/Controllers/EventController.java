package com.agendamiento.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.agendamiento.demo.Models.Entity.Event;
import com.agendamiento.demo.Service.EventService;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/Calendar")
    public String showCalendar(Model model) {
        model.addAttribute("event", new Event());
        return "Calendar";
    }

    @PostMapping("/events/save")
    public String saveEvent(@ModelAttribute Event event, Model model) {
        eventService.saveEvent(event);
        return "redirect:/Calendar";
    }

}
