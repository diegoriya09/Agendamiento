package com.agendamiento.demo.Service;

import com.agendamiento.demo.Models.Entity.User;

public interface UserService {
    
    public User createUser(User user);

    public boolean checkEmail(String email);
}

