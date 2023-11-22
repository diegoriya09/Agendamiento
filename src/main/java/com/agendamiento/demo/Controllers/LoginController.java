package com.agendamiento.demo.Controllers;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.agendamiento.demo.Models.Dao.IUserDao;
import com.agendamiento.demo.Models.Entity.User;
import com.agendamiento.demo.Service.UserService;

@Controller
public class LoginController {

    @Autowired
    private IUserDao userDao;

    @Autowired // inyectar dependencias, campo, metodo, constructor
    private UserService userService;

    @ModelAttribute
    private void userdetails(Model model, Principal p) {
        if (p != null) {
            String email = p.getName();
            User user = userDao.findByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/signin")
    public String login() {
        return "Login";
    }

    @GetMapping("/Register")
    public String register() {
        return "Register";
    }

    @PostMapping("/createUser")
    public String createUser(@Validated User user, BindingResult bindingResult, HttpSession session, Model model) {

        boolean f = userService.checkEmail(user.getEmail());

        if(bindingResult.hasErrors()){
            model.addAttribute("user", user);
            session.setAttribute("msg4", "Please, complete the fields correctly");
            return "Register";
        }


        if (f) {
            session.setAttribute("msg", "Email id already exists");
        } else {
            User userDtls = userService.createUser(user);

            if (userDtls != null) {
                session.setAttribute("msg", "User register successfully");
            } else {
                session.setAttribute("msg3", "Something wrong, try again");
            }
        }
        return "redirect:/Register";
    }
}