package org.example.Service;

import org.apache.log4j.Logger;
import org.example.Model.DAO.UserDAO;
import org.example.Model.User;


import java.util.List;

public class UserServiceImpl implements UserService{
    private static final Logger log = Logger.getLogger(UserServiceImpl.class.getSimpleName());
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getAll() {
        log.info("get list of users");
        return userDAO.getAll();
    }

    @Override
    public User getById(int id) {
        log.debug("get user by id:"+id);
        return userDAO.getById(id);
    }

    @Override
    public boolean create(User user) {
        log.debug("create new user ");
        return userDAO.create(user);
    }

    @Override
    public boolean update(User user) {
        log.debug("update user");
        return userDAO.update(user);
    }

    @Override
    public boolean delete(int id) {
        log.debug("delete user by id:"+id);
        return userDAO.delete(id);
    }

    @Override
    public User getByLogin(String login) {
        log.debug("get user by login: "+login);
        return userDAO.findByLogin(login);
    }
}
