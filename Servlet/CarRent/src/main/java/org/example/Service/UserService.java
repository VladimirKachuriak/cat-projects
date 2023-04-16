package org.example.Service;

import org.example.Model.DAO.Entity;
import org.example.Model.User;

public interface UserService extends Entity<User> {
    User getByLogin(String login);
}
