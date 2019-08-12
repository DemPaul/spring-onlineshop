package onlineshop.spring.dao.impl;

import onlineshop.spring.dao.UserDao;
import onlineshop.spring.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger userDaoLogger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public void addUser(User user) {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            sessionFactory.getCurrentSession().save(user);
            userDaoLogger.info("User " + user + " added to the DataBase");
        } catch (Exception e) {
            userDaoLogger.error("Problem in working with the DataBase, " +
                    "User " + user + " isn't added to the DataBase", e);
        }
    }

    @Override
    public boolean isPresent(String email) {
        try {
            Query query = sessionFactory.getCurrentSession()
                    .createQuery("FROM User where email = :email");
            query.setParameter("email", email);
            User user = (User) query.uniqueResult();
            return user != null;
        } catch (Exception e) {
            userDaoLogger.error("Problem in working with the DataBase", e);
        }
        return false;
    }

    @Override
    public List<User> getAll() {
        try {
            @SuppressWarnings("unchecked")
            TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("FROM User ");
            return query.getResultList();
        } catch (Exception e) {
            userDaoLogger.error("Problem in working with the DataBase", e);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<User> getUserById(long id) {
        try {
            User user = sessionFactory.getCurrentSession().get(User.class, id);
            return Optional.of(user);
        } catch (Exception e) {
            userDaoLogger.error("Problem in working with the DataBase", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        try {
            Query query = sessionFactory.getCurrentSession()
                    .createQuery("FROM User where email = :email");
            query.setParameter("email", email);
            User user = (User) query.uniqueResult();
            return Optional.of(user);
        } catch (Exception e) {
            userDaoLogger.error("Problem in working with the DataBase", e);
        }
        return Optional.empty();
    }

    @Override
    public void changeUser(User oldUser, User newUser) {
        try {
            oldUser.setEmail(newUser.getEmail());
            oldUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            oldUser.setRole(newUser.getRole());
            sessionFactory.getCurrentSession().update(oldUser);
            userDaoLogger.info("User " + oldUser + " changed in DataBase to " + newUser);
        } catch (Exception e) {
            userDaoLogger.error("Problem in working with the DataBase", e);
        }
    }

    @Override
    public void removeUser(User user) {
        try {
            sessionFactory.getCurrentSession().delete(user);
            Query query = sessionFactory.getCurrentSession()
                    .createQuery("delete Basket where user = :user");
            query.setParameter("user", user);
            query.executeUpdate();
            userDaoLogger.info("User " + user + "and all his data removed from DataBase");
        } catch (Exception e) {
            userDaoLogger.error("Problem in working with the DataBase, User "
                    + user + " isn't removed from DataBase", e);
        }
    }
}
