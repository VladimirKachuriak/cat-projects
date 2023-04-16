package org.example.Controller;

import org.example.Service.UserService;
import org.example.Model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegistrationServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private UserService userService;
    @InjectMocks
    private RegistrationServlet registrationServlet;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doGet() throws ServletException, IOException {
        when(request.getRequestDispatcher("/registration.jsp")).thenReturn(dispatcher);
        registrationServlet.doGet(request, response);
        verify(dispatcher,times(1)).forward(request, response);
    }

    @Test
    public void doPost() throws ServletException, IOException {
        when(request.getParameter("phone")).thenReturn("+123456");
        when(request.getParameter("email")).thenReturn("myemail@gmail.com");
        when(request.getParameter("login")).thenReturn("user");
        when(userService.create(any(User.class))).thenReturn(true);
        when(userService.getByLogin("user")).thenReturn(new User());
        registrationServlet.doPost(request, response);
        verify(userService, times(1)).create(any(User.class));
        verify(userService, times(1)).getByLogin("user");
        verify(response).sendRedirect("login");
    }

}