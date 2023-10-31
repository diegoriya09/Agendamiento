package com.agendamiento.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.agendamiento.demo.Models.Dao.IUserDao;
import com.agendamiento.demo.Models.Entity.User;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("users")
public class LoginController {

    @Autowired // inyectar dependencias, campo, metodo, constructor
    private IUserDao UserDao;

    @GetMapping("/Login")
    public String login(Model model) {
        User user = new User();

        model.addAttribute("user", user);
        model.addAttribute("title", "Login");

        return "Login";
    }

    @PostMapping("/Login")
    public String Validate(@Valid User user, BindingResult bindingResult, SessionStatus status) {
        List<User> lista = UserDao.findAll();

        if (!lista.isEmpty()) {

            for (int i = 0; i < lista.size(); i += 1) {
                if (lista.get(i).getEmail().equals(user.getEmail())
                        && lista.get(i).getPassword().equals(user.getPassword())) {
                    return "redirect:/Homepage";
                }
            }
        }

        else {
            System.out.println("Empty list!");
        }

        return "redirect:Login";
    }

    @GetMapping("/EditProfile")
    public String create(Model model) {

        User user = new User();

        model.addAttribute("title", "Edit profile");
        model.addAttribute("user", user);

        return "EditProfile";
    }

    @PostMapping("/EditProfile")
    public String save(@Valid User user, BindingResult bindingResult, SessionStatus status) {

        if (bindingResult.hasErrors()) {
            return "EditProfile";
        }
        UserDao.save(user);
        status.setComplete();
        return "redirect:Homepage";
    }

    // @GetMapping("/EditProfile/{id}")
    // public String edit(@PathVariable(value = "id") Long id, Model model) {

    //     User user = null;

    //     if (id > 0) {
    //         user = UserDao.findOne(id);
    //     } else {
    //         return "redirect:/Login";
    //     }
    //     model.addAttribute("title", "Edit");
    //     model.addAttribute("user", user);

    //     return "EditProfile";
    // }

    @GetMapping("/SideMenu")
    public String Menu(Model model) {

        return "SideMenu";
    }

    @GetMapping("/Homepage")
    public String home(Model model) {

        return "Homepage";
    }
}