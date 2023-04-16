package org.example.Service;

import org.example.Model.Car;
import org.example.Model.Order;

import java.util.List;

public interface OrderService{
    boolean addOrder(Order order);
    boolean deleteOrder(Order order);
    boolean deleteOrderById(int id);
    boolean deleteOrderByCarId(int id);
    boolean updateOrder(Order order);
    List<Order> getAllOrder();
    List<Order> getAllOrderByUserId(int id);
    Order getOrderById(int id);
    int countAllOrders();

}
