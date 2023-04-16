package org.example.Model.DAO;

import org.example.Model.Order;

import java.util.List;

public interface OrderDAO extends Entity<Order>{
    boolean deleteOrderByCarId(int id);
    List<Order> getAllOrderByUserId(int id);
    List<Order> getAllOrderByCarId();
    int countAllOrders();
}
