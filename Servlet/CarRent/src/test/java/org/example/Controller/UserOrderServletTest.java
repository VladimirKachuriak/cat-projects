package org.example.Controller;

import org.example.Model.Car;
import org.example.Model.Order;
import org.example.Service.CarService;
import org.example.Service.OrderService;
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

public class UserOrderServletTest {
    @Mock
    private UserService userService;
    @Mock
    private OrderService orderService;
    @Mock
    private CarService carService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private HttpSession session;
    @InjectMocks
    private UserOrderServlet userOrderServlet;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doGet() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("login")).thenReturn("user22");
        User user = new User();
        user.setId(1);
        when(userService.getByLogin("user22")).thenReturn(user);
        when(request.getRequestDispatcher("/user_orders.jsp")).thenReturn(dispatcher);
        userOrderServlet.doGet(request, response);
        verify(dispatcher,times(1)).forward(request,response);
    }

    @Test
    public void doPost() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("pay");
        when(request.getParameter("idOrder")).thenReturn("25");
        when(request.getSession()).thenReturn(session);
        Order order = new Order();
        order.setIdUser(6);
        order.setState(Order.State.FINISH);
        User user = new User();
        user.setId(6);
        when(orderService.getOrderById(25)).thenReturn(order);
        when(userService.getById(order.getIdUser())).thenReturn(user);
        userOrderServlet.doPost(request,response);
        verify(response,times(1)).sendRedirect("userOrders");
    }
    @Test
    public void makePayment() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("pay");
        when(request.getParameter("idOrder")).thenReturn("25");
        when(request.getSession()).thenReturn(session);
        Order order = new Order();
        order.setIdUser(6);
        order.setState(Order.State.WAIT);
        User user = new User();
        user.setId(6);
        when(orderService.getOrderById(25)).thenReturn(order);
        when(userService.getById(order.getIdUser())).thenReturn(user);
        userOrderServlet.doPost(request,response);
        verify(response,times(1)).sendRedirect("userOrders");
    }
    @Test
    public void makePaymentDamaged() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("pay");
        when(request.getParameter("idOrder")).thenReturn("25");
        when(request.getSession()).thenReturn(session);
        when(carService.getCarById(8)).thenReturn(new Car());
        Order order = new Order();
        order.setIdUser(6);
        order.setIdCar(8);
        order.setState(Order.State.DAMAGED);
        User user = new User();
        user.setId(6);
        when(orderService.getOrderById(25)).thenReturn(order);
        when(userService.getById(order.getIdUser())).thenReturn(user);
        userOrderServlet.doPost(request,response);
        verify(response,times(1)).sendRedirect("userOrders");
        verify(carService,times(1)).getCarById(8);
    }
    @Test
    public void topUp() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("topUpMoney");
        when(request.getParameter("money")).thenReturn("33");
        when(userService.getByLogin("user")).thenReturn(new User());
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("login")).thenReturn("user");
        userOrderServlet.doPost(request,response);
        verify(response,times(1)).sendRedirect("userOrders");
        verify(userService,times(1)).getByLogin("user");
    }
}