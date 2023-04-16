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

public class CatalogServletTest {
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
    @Mock
    private CarService carService;
    @Mock
    private OrderService orderService;
    @InjectMocks
    private CatalogServlet catalogServlet;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doGet() throws ServletException, IOException {
        when(request.getRequestDispatcher("/catalog.jsp")).thenReturn(dispatcher);
        catalogServlet.doGet(request,response);
        verify(dispatcher,times(1)).forward(request,response);
    }
    @Test
    public void filterTestBrandAndRate() throws ServletException, IOException {
        when(request.getRequestDispatcher("/catalog.jsp")).thenReturn(dispatcher);
        when(request.getParameter("brand")).thenReturn("BMW");
        when(request.getParameter("rate")).thenReturn("A");
        catalogServlet.doGet(request,response);
        verify(dispatcher,times(1)).forward(request,response);
    }
    @Test
    public void filterTestBrand() throws ServletException, IOException {
        when(request.getRequestDispatcher("/catalog.jsp")).thenReturn(dispatcher);
        when(request.getParameter("brand")).thenReturn("BMW");
        catalogServlet.doGet(request,response);
        verify(dispatcher,times(1)).forward(request,response);
    }
    @Test
    public void filterTestRate() throws ServletException, IOException {
        when(request.getRequestDispatcher("/catalog.jsp")).thenReturn(dispatcher);
        when(request.getParameter("rate")).thenReturn("A");
        catalogServlet.doGet(request,response);
        verify(dispatcher,times(1)).forward(request,response);
    }
    @Test
    public void filterTestSortPrice() throws ServletException, IOException {
        when(request.getRequestDispatcher("/catalog.jsp")).thenReturn(dispatcher);
        when(request.getParameter("sort")).thenReturn("byPrice");
        catalogServlet.doGet(request,response);
        verify(dispatcher,times(1)).forward(request,response);
    }
    @Test
    public void filterTestSortModel() throws ServletException, IOException {
        when(request.getRequestDispatcher("/catalog.jsp")).thenReturn(dispatcher);
        when(request.getParameter("sort")).thenReturn("model");
        catalogServlet.doGet(request,response);
        verify(dispatcher,times(1)).forward(request,response);
    }
    @Test
    public void doPost() throws ServletException, IOException {
        when(request.getRequestDispatcher("/catalog.jsp")).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(User.Role.USER);
        when(session.getAttribute("login")).thenReturn("user45");
        when(request.getParameter("idCar")).thenReturn("6");
        when(request.getParameter("action")).thenReturn("makeOrder");
        when(request.getRequestDispatcher("/new_order.jsp")).thenReturn(dispatcher);
        when(userService.getByLogin("user45")).thenReturn(new User());

        catalogServlet.doPost(request,response);
        verify(dispatcher,times(1)).forward(request,response);
    }
    @Test
    public void createOrder() throws ServletException, IOException {
        Car car = new Car();
        car.setPrice(23);
        when(request.getRequestDispatcher("/catalog.jsp")).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(User.Role.USER);
        when(session.getAttribute("login")).thenReturn("user45");
        when(request.getParameter("idCar")).thenReturn("6");
        when(request.getParameter("action")).thenReturn("createOrder");
        when(request.getParameter("expire")).thenReturn("2022-07-27");
        when(request.getParameter("startDate")).thenReturn("2022-07-26");
        when(request.getParameter("endDate")).thenReturn("2022-07-27");
        when(request.getParameter("serial")).thenReturn("AB1234");
        when(carService.getCarById(6)).thenReturn(car);
        when(request.getRequestDispatcher("/new_order.jsp")).thenReturn(dispatcher);
        when(userService.getByLogin("user45")).thenReturn(new User());

        catalogServlet.doPost(request,response);
        verify(response,times(1)).sendRedirect("userOrders");
        verify(orderService,times(1)).addOrder(any(Order.class));
    }
}