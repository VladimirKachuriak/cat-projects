package com.example.CarRent.Service;

import com.example.CarRent.models.Role;
import com.example.CarRent.models.User;
import com.example.CarRent.repo.UserRepo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Log4j
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final MailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepo userRepo, MailSender mailSender, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }


    public boolean addUser(User user) {
        User userFromDb = userRepo.findUserByLogin(user.getUsername());

        if (userFromDb != null) {
            return false;
        }
        if(user.getRoles().contains(Role.USER)) {
            user.setActivationCode(UUID.randomUUID().toString());
            mailSender.send(user.getEmail(),"Activatoin code","Activation code link: http://localhost:8080/activate/"+user.getActivationCode());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        log.debug("add new user: " + user);
        return true;
    }

    public boolean updateUser(User user) {
        User userFromDb = userRepo.findUserByLogin(user.getUsername());

        if (userFromDb == null) {
            return false;
        }

        userRepo.save(user);
        log.debug("update user: " + user);
        return true;
    }

    public User findByID(int id) {
        User userFromDb = userRepo.getReferenceById(id);
        log.debug("find user with id: " + id);
        return userFromDb;
    }

    public User findByLogin(String login) {
        User userFromDb = userRepo.findUserByLogin(login);
        log.debug("find user with login: " + login);
        return userFromDb;
    }

    public List<User> getAllUser() {
        List<User> users = userRepo.findAll();
        log.debug("get all users");
        return users;
    }

    public boolean deleteByLogin(String login) {
        if (userRepo.findUserByLogin(login) == null) return false;
        userRepo.deleteByLogin(login);
        log.debug("delete user by login: " + login);
        return true;
    }

    public boolean activeUser(String code) {
        User user = userRepo.findUserByActivationCode(code);

        if (user == null) {
            return false;
        }
        user.setActive(true);
        user.setActivationCode(null);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepo.findUserByLogin(login);
    }
}
