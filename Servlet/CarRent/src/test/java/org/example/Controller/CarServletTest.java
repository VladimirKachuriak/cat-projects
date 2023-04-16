package org.example.Controller;

import org.example.Model.Car;
import org.example.Service.CarService;
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

public class CarServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private HttpSession session;
    @Mock
    private CarService carService;
    @InjectMocks
    private CarServlet carServlet;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doGet() throws ServletException, IOException {
        when(request.getRequestDispatcher("/list_car.jsp")).thenReturn(dispatcher);
        carServlet.doGet(request,response);
        verify(dispatcher,times(1)).forward(request, response);
    }

    @Test
    public void doPost() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("remove");
        when(request.getParameter("idCar")).thenReturn("11");
        when(request.getSession()).thenReturn(session);
        Car car = new Car();
        car.setId(11);
        car.setState(Car.State.AVAIL);
        when(carService.getCarById(11)).thenReturn(car);
        carServlet.doPost(request,response);
        verify(response,times(1)).sendRedirect("car");
    }
    @Test
    public void editCar() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("edit");
        when(request.getParameter("idCar")).thenReturn("11");
        when(request.getParameter("date")).thenReturn("2007-07-22");
        when(request.getParameter("price")).thenReturn("22");
        when(request.getParameter("auto_class")).thenReturn("B");
        when(request.getSession()).thenReturn(session);
        Car car = new Car();
        car.setId(11);
        car.setState(Car.State.AVAIL);
        when(carService.getCarById(11)).thenReturn(car);
        carServlet.doPost(request,response);
        verify(response,times(1)).sendRedirect("car");
    }

    @Test
    public void addCar() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("add");
        when(request.getParameter("idCar")).thenReturn("11");
        when(request.getParameter("date")).thenReturn("2007-07-22");
        when(request.getParameter("price")).thenReturn("22");
        when(request.getParameter("auto_class")).thenReturn("B");
        when(request.getSession()).thenReturn(session);
        when(carService.addCar(any(Car.class))).thenReturn(true);
        Car car = new Car();
        car.setId(11);
        car.setState(Car.State.AVAIL);
        when(carService.getCarById(11)).thenReturn(car);
        carServlet.doPost(request,response);

        verify(response,times(1)).sendRedirect("car");
    }

    @Test
    public void getNewCar() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("editCarById");
        when(request.getParameter("idCar")).thenReturn("11");
        when(request.getRequestDispatcher("/new_car.jsp")).thenReturn(dispatcher);
        carServlet.doGet(request,response);

        verify(dispatcher,times(1)).forward(request,response);
    }
}