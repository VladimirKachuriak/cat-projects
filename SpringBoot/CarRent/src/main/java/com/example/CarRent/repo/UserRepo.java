package com.example.CarRent.repo;

import com.example.CarRent.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findUserByLogin(String login);
    void deleteByLogin(String login);

    User findUserByActivationCode(String code);
}
