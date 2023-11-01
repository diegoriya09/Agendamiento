package com.agendamiento.demo.Controllers;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        if(p!=null){
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
    public String createUser(@ModelAttribute User user, HttpSession session) {

        System.out.println(user);

        boolean f = userService.checkEmail(user.getEmail());

        if (f) {
            session.setAttribute("msg", "Email id already exists");
        } else {
            User userDtls = userService.createUser(user);

            if (userDtls != null) {
                session.setAttribute("msg", "User register successfully");
            } else {
                session.setAttribute("msg", "Something wrong");
            }
        }
        return "redirect:/Register";
    }
    /*
     * @GetMapping("/Login")
     * public String login(Model model) {
     * User user = new User();
     * 
     * model.addAttribute("user", user);
     * model.addAttribute("title", "Login");
     * 
     * return "Login";
     * }
     * 
     * @PostMapping("/Login")
     * public String Validate(@Valid User user, BindingResult bindingResult,
     * SessionStatus status) {
     * List<User> lista = UserDao.findAll();
     * 
     * if (!lista.isEmpty()) {
     * 
     * for (int i = 0; i < lista.size(); i += 1) {
     * if (lista.get(i).getEmail().equals(user.getEmail())
     * && lista.get(i).getPassword().equals(user.getPassword())) {
     * return "redirect:/Homepage";
     * }
     * }
     * }
     * 
     * else {
     * System.out.println("Empty list!");
     * }
     * 
     * return "redirect:Login";
     * }
     * 
     * @GetMapping("/EditProfile")
     * public String create(Model model) {
     * 
     * User user = new User();
     * 
     * model.addAttribute("title", "Edit profile");
     * model.addAttribute("user", user);
     * 
     * return "EditProfile";
     * }
     * 
     * @PostMapping("/EditProfile")
     * public String save(@Valid User user, BindingResult bindingResult,
     * SessionStatus status) {
     * 
     * UserDao.save(user);
     * status.setComplete();
     * return "redirect:Homepage";
     * }
     * 
     * @GetMapping("/EditProfile/{id}")
     * public String edit(@PathVariable(value = "id") Long id, Model model) {
     * 
     * User user = null;
     * 
     * if (id > 0) {
     * user = UserDao.findOne(id);
     * } else {
     * return "redirect:/Login";
     * }
     * model.addAttribute("title", "Edit");
     * model.addAttribute("user", user);
     * 
     * return "EditProfile";
     * }
     * 
     * @GetMapping("/SideMenu")
     * public String Menu(Model model) {
     * 
     * return "SideMenu";
     * }
     * 
     * @GetMapping("/Homepage")
     * public String home(Model model) {
     * 
     * return "Homepage";
     * }
     * 
     * @GetMapping("/forgotPassword")
     * public String forgotPass(){
     * 
     * return "";
     * }
     */
}