package com.agendamiento.demo.Controllers;

import java.util.List;

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

import jakarta.validation.constraints.NotEmpty;

@Controller
@SessionAttributes("users")
public class LoginController {
    @Autowired // inyectar dependencias, campo, metodo, constructor
    private IUserDao UserDao;

    @GetMapping("/Login")
    public String login(Model model) {
        User user = new User();

        model.addAttribute("user", user);

        return "Login";
    }

    @PostMapping("/Login")
    public String Validate(@NotEmpty User user, BindingResult bindingResult, SessionStatus status) {
        List<User> lista = UserDao.findAll();

        if (!lista.isEmpty()) {

            for (int i = 0; i < lista.size(); i += 1) {
                if (lista.get(i).getEmail().equals(user.getEmail())
                        && lista.get(i).getPassword().equals(user.getPassword())) {
                    return "";
                }
            }
        }

        else {
            System.out.println("Empty list!");
        }

        return "redirect:Login";
    }
}
