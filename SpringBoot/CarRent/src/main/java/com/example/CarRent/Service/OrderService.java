package com.example.CarRent.Service;

import com.example.CarRent.models.Car;
import com.example.CarRent.models.Orders;
import com.example.CarRent.repo.OrderRepo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j
public class OrderService {
   private final OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }
    public List<Orders> getAllOrder(){
        List<Orders> orders = orderRepo.findAll();
        log.debug("return all orders");
        return orders;
    }
    public boolean addOrder(Orders order){
        orderRepo.save(order);
        log.debug("add new order");
        return true;
    }
    public Orders getOrderById(int id){
        Orders order = orderRepo.findById(id).orElse(null);
        log.debug("get order by id:"+id);
        return order;
    }
    public boolean delete(Orders orders){
        orderRepo.delete(orders);
        log.debug("delete order by id:"+orders.getId());
        return true;
    }
    public boolean deleteById(int id){
        orderRepo.deleteById(id);
        log.debug("delete order by id:"+id);
        return true;
    }
}
