package com.agendamiento.demo.Models.Dao;

import java.util.List;

import com.agendamiento.demo.Models.Entity.User;

public interface IUserDao {
    public List<User> findAll();
    public void save(User user);
    public User findOne(Long id);
    public void delete(Long id);
}
