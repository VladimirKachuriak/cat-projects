package org.example.Controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LogoutServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void doGet() throws ServletException, IOException {
        LogoutServlet logoutServlet = new LogoutServlet();
        when(request.getSession()).thenReturn(session);
        logoutServlet.doGet(request,response);
        verify(session,times(2)).removeAttribute(anyString());
        verify(response,times(1)).sendRedirect("login");
    }

    @Test
    public void doPost() throws ServletException, IOException {
        LogoutServlet logoutServlet = new LogoutServlet();
        when(request.getSession()).thenReturn(session);
        logoutServlet.doPost(request,response);
    }
}