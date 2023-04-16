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

public class UserServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private HttpSession session;
    @Mock
    private UserService userService;
    @InjectMocks
    private UserServlet userServlet;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doGet() throws ServletException, IOException {
        when(request.getRequestDispatcher("/list_user.jsp")).thenReturn(dispatcher);
        userServlet.doGet(request,response);
        verify(dispatcher,times(1)).forward(request,response);
    }

    @Test
    public void doPost() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("addAdmin");
        when(request.getParameter("email")).thenReturn("mymail@gmail.com");
        when(request.getParameter("phone")).thenReturn("+123456");
        when(userService.create(any(User.class))).thenReturn(true);
        userServlet.doPost(request,response);
        verify(response,times(1)).sendRedirect("users");
    }
    @Test
    public void changeUserStatus() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("changeStatus");
        when(request.getParameter("idUser")).thenReturn("3");
        when(request.getSession()).thenReturn(session);
        User user = new User();
        user.setId(3);
        user.setStatus(User.Status.BANNED);
        user.setRole(User.Role.USER);
        when(userService.getById(3)).thenReturn(user);
        userServlet.doPost(request,response);
        verify(response,times(1)).sendRedirect("users");
        verify(userService,times(1)).update(any(User.class));
        verify(userService,times(1)).getById(3);
        verify(request,times(1)).getSession();
    }
}