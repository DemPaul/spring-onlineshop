package onlineshop.spring.service.impl;

import onlineshop.spring.dao.UserDao;
import onlineshop.spring.entity.User;
import onlineshop.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isPresent(String email) {
        return userDao.isPresent(email);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Transactional
    @Override
    public void changeUser(User oldUser, User newUser) {
        userDao.changeUser(oldUser, newUser);

    }

    @Transactional
    @Override
    public void removeUser(User user) {
        userDao.removeUser(user);
    }

}
