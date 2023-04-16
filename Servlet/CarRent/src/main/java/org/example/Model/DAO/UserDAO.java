package org.example.Model.DAO;

import org.example.Model.User;

public interface UserDAO extends Entity<User>{
    User findByLogin(String login);
}
