package org.example.Controller;

import org.example.Model.Car;
import org.example.Model.Order;
import org.example.Service.CarService;
import org.example.Service.OrderService;
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

public class AdminOrdersServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private HttpSession session;
    @Mock
    private OrderService orderService;
    @Mock
    private CarService carService;
    @InjectMocks
    private AdminOrdersServlet adminOrdersServlet;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void doGet() throws ServletException, IOException {
        when(request.getRequestDispatcher("/admin_orders.jsp")).thenReturn(dispatcher);
        adminOrdersServlet.doGet(request,response);
        verify(dispatcher,times(1)).forward(request,response);
    }

    @Test
    public void doPost() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("accept");
        when(request.getParameter("idOrder")).thenReturn("11");
        Order order = new Order();
        order.setId(11);
        order.setIdCar(6);
        when(orderService.getOrderById(11)).thenReturn(order);
        when(carService.getCarById(6)).thenReturn(new Car());
        adminOrdersServlet.doPost(request,response);
        verify(response,times(1)).sendRedirect("adminOrders");
    }
    @Test
    public void finishOrder() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("finish");
        when(request.getParameter("idOrder")).thenReturn("11");
        Order order = new Order();
        order.setId(11);
        order.setIdCar(6);
        when(orderService.getOrderById(11)).thenReturn(order);
        when(carService.getCarById(6)).thenReturn(new Car());
        adminOrdersServlet.doPost(request,response);
        verify(response,times(1)).sendRedirect("adminOrders");
        verify(carService,times(1)).updateCar(any(Car.class));
    }
    @Test
    public void damageOrder() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("damage");
        when(request.getParameter("damageAccount")).thenReturn("22");
        when(request.getParameter("idOrder")).thenReturn("11");
        Order order = new Order();
        order.setId(11);
        order.setIdCar(6);
        when(orderService.getOrderById(11)).thenReturn(order);
        when(carService.getCarById(6)).thenReturn(new Car());
        adminOrdersServlet.doPost(request,response);
        verify(response,times(1)).sendRedirect("adminOrders");
    }
    @Test
    public void rejectOrder() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("reject");
        when(request.getParameter("damageAccount")).thenReturn("22");
        when(request.getParameter("idOrder")).thenReturn("11");
        Order order = new Order();
        order.setId(11);
        order.setIdCar(6);
        when(orderService.getOrderById(11)).thenReturn(order);
        when(carService.getCarById(6)).thenReturn(new Car());
        adminOrdersServlet.doPost(request,response);
        verify(response,times(1)).sendRedirect("adminOrders");
    }
    @Test
    public void acceptOrder() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("accept");
        when(request.getParameter("damageAccount")).thenReturn("22");
        when(request.getParameter("idOrder")).thenReturn("11");
        Order order = new Order();
        order.setId(11);
        order.setIdCar(6);
        when(orderService.getOrderById(11)).thenReturn(order);
        when(carService.getCarById(6)).thenReturn(new Car());
        adminOrdersServlet.doPost(request,response);
        verify(response,times(1)).sendRedirect("adminOrders");
    }
}