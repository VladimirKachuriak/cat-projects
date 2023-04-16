package com.example.CarRent.Service;

import com.example.CarRent.models.Role;
import com.example.CarRent.models.User;
import com.example.CarRent.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepo userRepo;
    @MockBean MailSender mailSender;

    @Test
    void addUser() {
        User user = new User();
        user.setEmail("my@gmail.com");
        user.setLogin("user");
        user.setPassword("password");
        user.setRoles(Collections.singleton(Role.USER));
        boolean isUserAdded = userService.addUser(user);
        assertEquals(true, isUserAdded);
        Mockito.verify(userRepo, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(userRepo, Mockito.times(1)).findUserByLogin(Mockito.anyString());
        Mockito.verify(mailSender, Mockito.times(1)).send(Mockito.eq("my@gmail.com"),Mockito.anyString(),Mockito.anyString());
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setLogin("user");
        Mockito.when(userRepo.findUserByLogin(Mockito.anyString())).thenReturn(user);
        boolean isUserUpdated = userService.updateUser(user);
        assertEquals(true, isUserUpdated);
        Mockito.verify(userRepo, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(userRepo, Mockito.times(1)).findUserByLogin(Mockito.anyString());

    }

    @Test
    void findByID() {
        Mockito.when(userRepo.getReferenceById(3)).thenReturn(new User());
        User user = userService.findByID(3);
        assertEquals(true, user!=null);
        Mockito.verify(userRepo, Mockito.times(1)).getReferenceById(3);
    }

    @Test
    void findByLogin() {
        Mockito.when(userRepo.findUserByLogin("user")).thenReturn(new User());
        User user = userService.findByLogin("user");
        assertEquals(true, user!=null);
        Mockito.verify(userRepo, Mockito.times(1)).findUserByLogin("user");
    }

    @Test
    void getAllUser() {
        Mockito.when(userRepo.findAll()).thenReturn(Arrays.asList(new User(),new User(),new User()));
        List<User> userList = userService.getAllUser();
        assertEquals(3,userList.size());
        Mockito.verify(userRepo, Mockito.times(1)).findAll();
    }

    @Test
    void loadUserByUsername() {
        Mockito.when(userRepo.findUserByLogin("user")).thenReturn(new User());
        UserDetails userDetails = userService.loadUserByUsername("user");
        assertEquals(true, userDetails!=null);
        Mockito.verify(userRepo, Mockito.times(1)).findUserByLogin("user");
    }
}