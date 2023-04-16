package org.example.Controller;

import org.apache.log4j.Logger;
import org.example.Model.Car;
import org.example.Model.DAO.CarDAOImpl;
import org.example.Model.DAO.OrderDAOImpl;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@WebServlet("/car")
public class CarServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(CarServlet.class.getSimpleName());
    private CarService carService = new CarServiceImpl(new CarDAOImpl());
    private OrderService orderService = new OrderServiceImpl(new OrderDAOImpl());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        log.info("action: "+action);
        if(action!=null) {
            log.info("edit car by id");
            if(action.equals("editCarById"))
            editCarById(req,resp);
        }else {
            forwardLisUsers(req, resp, carService.getAllCar());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        log.info("method post action: "+action);
        switch (action) {
            case "add":
                addCarAction(req, resp);
                break;
            case "edit":
                editCarAction(req, resp);
                break;
            case "remove":
                removeCarAction(req, resp);
                break;
        }
    }

    private void removeCarAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("remove car");
        String id = req.getParameter("idCar");
        if(!carService.getCarById(Integer.parseInt(id)).getState().equals(Car.State.AVAIL)){
            req.getSession().setAttribute("message","label.car.inuseCar");
            resp.sendRedirect("car");
            return;
        }
        orderService.deleteOrderByCarId(Integer.parseInt(id));
        boolean result = carService.deleteCarById(Integer.parseInt(id));
        if(result)req.getSession().setAttribute("message","label.car.delete");
        resp.sendRedirect("car");
    }

    private void addCarAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("add car");
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String date = req.getParameter("date");
        String price = req.getParameter("price");
        String auto_class = req.getParameter("auto_class");
        Date date1 = new Date();
        try {
             date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //boolean flag = CarDAO.addCar(brand, model, date1, auto_class,Integer.parseInt(price));
        boolean flag = carService.addCar(new Car.CarBuilder().brand(brand).model(model).date(date1).
                state(Car.Class.valueOf(auto_class)).state(Car.State.valueOf("AVAIL")).
                price(Integer.parseInt(price)).build());
        if(flag) resp.sendRedirect("car");
    }

    private void forwardLisUsers(HttpServletRequest req, HttpServletResponse resp, List<Car> cars)
            throws ServletException, IOException {
        String nextJSP = "/list_car.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(nextJSP);
        req.setAttribute("carList", cars);
        dispatcher.forward(req, resp);
    }
    private void editCarAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("edit car");
        String id = req.getParameter("idCar");
        Car car = carService.getCarById(Integer.parseInt(id));
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String date = req.getParameter("date");
        String price = req.getParameter("price");
        String auto_class = req.getParameter("auto_class");
        Date date1 = new Date();
        try {
            date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        car.setBrand(brand);
        car.setModel(model);
        car.setReleaseDate(date1);
        car.setPrice(Integer.parseInt(price));
        car.setAutoClass(Car.Class.valueOf(auto_class));
        if(carService.updateCar(car))req.getSession().setAttribute("message","label.car.editCar");
        resp.sendRedirect("car");
    }
    private void editCarById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("idCar"));
        Car car = null;
        try {
            car = carService.getCarById(id);
        } catch (Exception e) {

        }
        req.setAttribute("car", car);
        req.setAttribute("action", "edit");
        String nextJSP = "/new_car.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(nextJSP);
        dispatcher.forward(req, resp);
    }
}
