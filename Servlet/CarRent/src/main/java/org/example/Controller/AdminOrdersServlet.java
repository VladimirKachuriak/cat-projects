package org.example.Controller;

import org.apache.log4j.Logger;
import org.example.Model.Car;
import org.example.Model.DAO.CarDAOImpl;
import org.example.Model.DAO.OrderDAOImpl;
import org.example.Model.Order;
import org.example.Service.CarService;
import org.example.Service.CarServiceImpl;
import org.example.Service.OrderService;
import org.example.Service.OrderServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/adminOrders")
public class AdminOrdersServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(AdminOrdersServlet.class.getSimpleName());
    private OrderService orderService = new OrderServiceImpl(new OrderDAOImpl());
    private CarService carService = new CarServiceImpl(new CarDAOImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orders = orderService.getAllOrder();
        forwardListOrders(req, resp, orders);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "reject":
                rejectOrder(req, resp);
                break;
            case "accept":
                acceptOrder(req, resp);
                break;
            case "damage":
                damageOrder(req, resp);
                break;
            case "finish":
                finishOrder(req, resp);
                break;
        }
    }

    private void finishOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Order order = orderService.getOrderById(Integer.parseInt(req.getParameter("idOrder")));
        order.setState(Order.State.FINISH);
        order.setMessage(req.getParameter("orderResponse"));
        orderService.updateOrder(order);
        Car car = carService.getCarById(order.getIdCar());
        car.setState(Car.State.AVAIL);
        carService.updateCar(car);
        resp.sendRedirect("adminOrders");
    }

    private void damageOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("return order with damage status");
        if (req.getParameter("damageAccount").equals("")) {
            req.getSession().setAttribute("message", "label.car.damageAccount");
            resp.sendRedirect("adminOrders");
            return;
        }
        Order order = orderService.getOrderById(Integer.parseInt(req.getParameter("idOrder")));
        order.setState(Order.State.DAMAGED);
        order.setAccountDamage(Integer.parseInt(req.getParameter("damageAccount")));
        order.setMessage(req.getParameter("orderResponse"));
        orderService.updateOrder(order);
        Car car = carService.getCarById(order.getIdCar());
        car.setState(Car.State.AVAIL);
        carService.updateCar(car);
        resp.sendRedirect("adminOrders");
    }

    private void acceptOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("accept order");
        Order order = orderService.getOrderById(Integer.parseInt(req.getParameter("idOrder")));
        order.setState(Order.State.WAIT);
        order.setMessage(req.getParameter("orderResponse"));
        orderService.updateOrder(order);
        Car car = carService.getCarById(order.getIdCar());
        car.setState(Car.State.INUSE);
        carService.updateCar(car);
        resp.sendRedirect("adminOrders");
    }

    private void rejectOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("reject order");
        Order order = orderService.getOrderById(Integer.parseInt(req.getParameter("idOrder")));
        order.setState(Order.State.RETURN);
        order.setMessage(req.getParameter("orderResponse"));
        orderService.updateOrder(order);
        resp.sendRedirect("adminOrders");
    }

    private void forwardListOrders(HttpServletRequest req, HttpServletResponse resp, List<Order> orders)
            throws ServletException, IOException {
        log.debug("return admin_orders.jsp");
        String nextJSP = "/admin_orders.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(nextJSP);
        req.setAttribute("orderList", orders);
        dispatcher.forward(req, resp);
    }
}
