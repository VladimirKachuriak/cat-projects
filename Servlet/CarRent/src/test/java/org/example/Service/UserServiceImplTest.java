package org.example.Service;

import org.example.Model.DAO.UserDAO;
import org.example.Model.User;
import org.example.Service.UserService;
import org.example.Service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserDAO userDAO;

    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userDAO);
    }
    @Test
    public void getAll() {
        when(userDAO.getAll()).thenReturn(Arrays.asList(new User(),new User()));
        assertEquals(2,userService.getAll().size());
    }

    @Test
    public void getById() {
        User user = new User();
        user.setId(3);
        user.setLogin("admin");
        when(userDAO.getById(3)).thenReturn(user);
        assertEquals(user.toString(), userService.getById(3).toString());
    }

    @Test
    public void create() {
        when(userDAO.create(any(User.class))).thenReturn(true);
        assertTrue(userService.create(new User()));
    }

    @Test
    public void update() {
        when(userDAO.update(any(User.class))).thenReturn(true);
        assertTrue(userService.update(new User()));
    }

    @Test
    public void delete() {
        when(userDAO.delete(4)).thenReturn(true);
        assertTrue(userService.delete(4));
    }

    @Test
    public void getByLogin() {
        User user = new User();
        user.setId(3);
        user.setLogin("admin");
        when(userDAO.findByLogin(user.getLogin())).thenReturn(user);
        assertEquals(user.toString(), userService.getByLogin("admin").toString());
    }
}