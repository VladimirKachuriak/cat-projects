package org.example.Service;

import org.apache.log4j.Logger;
import org.example.Model.DAO.OrderDAO;
import org.example.Model.Order;


import java.util.List;

public class OrderServiceImpl implements OrderService{
    private static final Logger log = Logger.getLogger(OrderServiceImpl.class.getSimpleName());
    private final OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public boolean addOrder(Order order) {
        log.info("add new order");
        return orderDAO.create(order);
    }

    @Override
    public boolean deleteOrder(Order order) {
        log.info("delete order");
        return orderDAO.delete(order.getId());
    }

    @Override
    public boolean deleteOrderById(int id) {
        log.info("delete order by id:"+id);
        return orderDAO.delete(id);
    }

    @Override
    public boolean deleteOrderByCarId(int id) {
        log.info("delete order by car id: "+id);
        return orderDAO.deleteOrderByCarId(id);
    }

    @Override
    public boolean updateOrder(Order order) {
        log.info("update order");
        return orderDAO.update(order);
    }

    @Override
    public List<Order> getAllOrder() {
        log.info("get all orders");
        return orderDAO.getAll();
    }

    @Override
    public List<Order> getAllOrderByUserId(int id) {
        log.debug("get all order by user id"+id);
        return orderDAO.getAllOrderByUserId(id);
    }

    @Override
    public Order getOrderById(int id) {
        log.debug("get order by id"+id);
        return orderDAO.getById(id);
    }

    @Override
    public int countAllOrders() {
        return orderDAO.countAllOrders();
    }
}
