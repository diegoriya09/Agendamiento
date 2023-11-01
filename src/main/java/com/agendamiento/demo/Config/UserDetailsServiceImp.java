package com.agendamiento.demo.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.agendamiento.demo.Models.Dao.IUserDao;
import com.agendamiento.demo.Models.Entity.User;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private IUserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        User user = userDao.findByEmail(email);

        if(user!=null){
            return new CustomUserDetails(user);
        }
        throw new UsernameNotFoundException("User not available");
    }
    
}
