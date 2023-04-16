package org.example.Service;

import org.example.Model.DAO.OrderDAO;
import org.example.Model.Order;
import org.example.Service.OrderService;
import org.example.Service.OrderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {
    @Mock
    private OrderDAO orderDAO;

    private OrderService orderService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        orderService = new OrderServiceImpl(orderDAO);
    }
    @Test
    public void addOrder() {
        when(orderDAO.create(any(Order.class))).thenReturn(true);
        assertEquals(true,orderService.addOrder(new Order()));
    }

    @Test
    public void deleteOrder() {
        when(orderDAO.delete(3)).thenReturn(true);
        Order order = new Order();
        order.setId(3);
        assertEquals(true,orderService.deleteOrder(order));
    }

    @Test
    public void deleteOrderById() {
        when(orderDAO.delete(3)).thenReturn(true);
        Order order = new Order();
        order.setId(3);
        assertEquals(true,orderService.deleteOrderById(order.getId()));

    }

    @Test
    public void deleteOrderByCarId() {
        when(orderDAO.deleteOrderByCarId(7)).thenReturn(true);
        assertEquals(true,orderService.deleteOrderByCarId(7));
    }

    @Test
    public void updateOrder() {
        when(orderDAO.update(any(Order.class))).thenReturn(true);
        assertEquals(true,orderService.updateOrder(new Order()));
    }

    @Test
    public void getAllOrder() {
        when(orderDAO.getAll()).thenReturn(Arrays.asList(new Order(),new Order()));
        assertEquals(2,orderService.getAllOrder().size());
    }

    @Test
    public void getAllOrderByUserId() {
        Order order1 = new Order();
        order1.setId(1);
        order1.setIdUser(2);
        Order order2 = new Order();
        order2.setId(2);
        order2.setIdUser(2);
        when(orderDAO.getAllOrderByUserId(2)).thenReturn(Arrays.asList(order1,order2));
        assertEquals("[Order{id=1, idCar=0, idUser=2, start_date=null, end_date=null, withDriver=false, account=0, accountDamage=0, passportSerial='null', passportExpireDate=null, state=null, message='null'}, Order{id=2, idCar=0, idUser=2, start_date=null, end_date=null, withDriver=false, account=0, accountDamage=0, passportSerial='null', passportExpireDate=null, state=null, message='null'}]",orderService.getAllOrderByUserId(2).toString());
    }

    @Test
    public void getOrderById() {
        Order order = new Order();
        order.setId(3);
        when(orderDAO.getById(4)).thenReturn(order);
        assertEquals("Order{id=3, idCar=0, idUser=0, start_date=null, end_date=null, withDriver=false, account=0, accountDamage=0, passportSerial='null', passportExpireDate=null, state=null, message='null'}",orderService.getOrderById(4).toString());
    }

    @Test
    public void countAllOrders() {
        when(orderDAO.countAllOrders()).thenReturn(7);
        assertEquals(7,orderService.countAllOrders());
    }
}