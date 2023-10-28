package com.agendamiento.demo.Models.Dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.agendamiento.demo.Models.Entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class UserDaoImp implements IUserDao {
    @PersistenceContext
    private EntityManager em; // Interfaz que proporciona metodos para manipular la BD

    @Override
    @Transactional(readOnly = true) // transacción. Si algo falla durante la actualización del usuario, se revierte
                                    // la transacción y se asegura que los cambios no se guarden en la base de
                                    // datos.
    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        return em.createQuery("from User").getResultList();
    }

    @Override // sobrescribiendo un metodo de una super clase (IUsersDao)
    @Transactional
    public void save(User user) {

        if (user.getId() != null && user.getId() > 0) {
            em.merge(user); // Entidad existente, hay id, actualizar - cambios
        } else {
            em.persist(user); // Nueva entidad, no hay id
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = findOne(id);
        em.remove(user);
    }
}
