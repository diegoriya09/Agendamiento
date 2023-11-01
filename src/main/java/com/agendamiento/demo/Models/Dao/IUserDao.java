package com.agendamiento.demo.Models.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendamiento.demo.Models.Entity.User;

public interface IUserDao extends JpaRepository<User, Integer>{
  

    public boolean existsByEmail(String email);

    public User findByEmail(String email);
}
