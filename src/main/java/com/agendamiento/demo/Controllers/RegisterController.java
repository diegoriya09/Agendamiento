package com.agendamiento.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.agendamiento.demo.Models.Dao.IUserDao;
import com.agendamiento.demo.Models.Entity.User;

import jakarta.validation.Valid;


@Controller
@SessionAttributes("users")
public class RegisterController {
    
    @Autowired
    private IUserDao UserDao;

    @GetMapping("/Register")
    public String Register(Model model){
        User user = new User();

        model.addAttribute("user", user);
        model.addAttribute("title", "Register");

        return "Register";
    }

    @PostMapping("/Register")
    public String ValidateRegister(@Valid User user, BindingResult bindingResult, SessionStatus status, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("title", "Registration Form");
            return "Register";
        }

        UserDao.save(user);
        status.setComplete();

        return "redirect:/Login";
    }

}
