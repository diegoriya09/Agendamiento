package com.agendamiento.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendamiento.demo.Models.Dao.IUserDao;
import com.agendamiento.demo.Models.Entity.User;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private  IUserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncode;

    @Transactional
    @Override
    public User createUser(User user) {
        
        user.setPassword(passwordEncode.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        return userDao.save(user);
    }

    @Override
    public boolean checkEmail(String email) {
        
        return userDao.existsByEmail(email);
    }
}
