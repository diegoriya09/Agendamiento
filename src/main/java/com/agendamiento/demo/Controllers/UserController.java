package com.agendamiento.demo.Controllers;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.agendamiento.demo.Models.Dao.IUserDao;
import com.agendamiento.demo.Models.Entity.Event;
import com.agendamiento.demo.Models.Entity.User;
import com.agendamiento.demo.Service.EventService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private EventService eventService;

    @Autowired
    private BCryptPasswordEncoder passwordEncode;

    @ModelAttribute
    private void userdetails(Model model, Principal p) {

        String email = p.getName();
        User user = userDao.findByEmail(email);
        model.addAttribute("user", user);
    }

    @GetMapping("/")
    public String home() {
        return "User/Homepage";
    }

    @GetMapping("/Profile")
    public String profile() {
        return "User/Profile";
    }

    @GetMapping("/Calendar")
    public String calendar() {
        return "User/Calendar";
    }

    @PostMapping("/createEvent")
public ResponseEntity<String> createEvent(@Validated @RequestBody Event event, BindingResult bindingResult) {  
    if(bindingResult.hasErrors()){
        // Puedes manejar errores de validación y devolver una respuesta adecuada si es necesario
        return ResponseEntity.badRequest().body("Error de validación");
    }

    eventService.createEvent(event);
    return ResponseEntity.ok("Evento creado exitosamente");
}

    @GetMapping("/changePassword")
    public String loadChangePassword() {
        return "User/changePassword";
    }

    @PostMapping("/updatePassword")
    public String changePassword(Principal p, @RequestParam("oldPass") String oldPass,
            @RequestParam("newPass") String newPass, HttpSession session) {

        String email = p.getName();
        User loginUser = userDao.findByEmail(email);

        boolean f = passwordEncode.matches(oldPass, loginUser.getPassword());

        if (f) {

            loginUser.setPassword(passwordEncode.encode(newPass));
            User updatePasswordUser = userDao.save(loginUser);

            if (updatePasswordUser != null) {
                session.setAttribute("msg", "Password change success");
            } else {
                session.setAttribute("msg2", "Something wrong");
            }
        } else {
            session.setAttribute("msg3", "Old password incorrect");
        }

        return "redirect:/user/changePassword";
    }

    @GetMapping("/EditProfile")
    public String editProfile(Model model) {
        User user = (User) model.getAttribute("user");
        model.addAttribute("user", user);
        return "EditProfile";
    }

    @PostMapping("/EditProfile")
    public String saveProfileChanges(@ModelAttribute User user, HttpSession session) {
        User existingUser = userDao.findByEmail(user.getEmail());
        if (existingUser != null) {
            existingUser.setName(user.getName());
            // Actualiza otros campos según sea necesario
            userDao.save(existingUser);
            session.setAttribute("msg", "Profile changes saved successfully");
        } else {
            session.setAttribute("msg3", "User not found");
        }
        return "redirect:/user/EditProfile";
    }
}
