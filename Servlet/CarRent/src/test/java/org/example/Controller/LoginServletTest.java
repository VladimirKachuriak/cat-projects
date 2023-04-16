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
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class LoginServletTest {
    @Mock
    private UserService userService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private HttpSession session;
    @InjectMocks
    private LoginServlet loginServlet;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doGet() throws ServletException, IOException {
        when(request.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);
        loginServlet.doGet(request, response);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doPost() throws ServletException, IOException {
        when(request.getParameter("login")).thenReturn("user21");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);

        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        user.setStatus(User.Status.ACTIVE);
        when(userService.getByLogin("user21")).thenReturn(user);
        loginServlet.doPost(request, response);
        verify(response).sendRedirect("catalog");
    }
}