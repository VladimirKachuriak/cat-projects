package com.example.CarRent.Service;

import com.example.CarRent.models.Orders;
import com.example.CarRent.repo.OrderRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @MockBean
    private OrderRepo orderRepo;

    @Test
    void getAllOrder() {
        when(orderRepo.findAll()).thenReturn(List.of(new Orders(), new Orders()));
        List<Orders> orders = orderService.getAllOrder();
        assertEquals(2, orders.size());
        verify(orderRepo, times(1)).findAll();
    }

    @Test
    void addOrder() {
        boolean result = orderService.addOrder(new Orders());
        assertEquals(true, result);
        verify(orderRepo, times(1)).save(any(Orders.class));
    }

    @Test
    void getOrderById() {
        boolean result = orderService.deleteById(1);
        assertEquals(true, result);
        verify(orderRepo, times(1)).deleteById(1);
    }

    @Test
    void delete() {
        Orders order = new Orders();
        order.setId(3);
        boolean result = orderService.delete(order);
        assertEquals(true, result);
        verify(orderRepo, times(1)).delete(order);
    }

    @Test
    void deleteById() {
        boolean result = orderService.deleteById(1);
        assertEquals(true, result);
        verify(orderRepo, times(1)).deleteById(1);
    }
}