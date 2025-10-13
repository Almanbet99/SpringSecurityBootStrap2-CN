package ru.rishaleva.springBootSecurity.Dao;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rishaleva.springBootSecurity.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    public User findByUserEmail(String email) {
        String query = "select distinct u from User AS u left join fetch u.roles where u.email = :email";
        User user = entityManager.createQuery(query, User.class).setParameter("email", email).getSingleResult();
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " not found");
        }
        return user;
    }

    @Transactional
    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class)
                .getResultList();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void removeUser(Long id) {
        entityManager.remove(getUser(id));
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
    }

}
