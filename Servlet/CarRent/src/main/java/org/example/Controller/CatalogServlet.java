package org.example.Controller;

import org.example.Model.*;
import org.example.Model.DAO.CarDAOImpl;
import org.example.Model.DAO.OrderDAOImpl;
import org.example.Model.DAO.UserDAOImpl;
import org.example.Service.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/catalog")
public class CatalogServlet extends HttpServlet {
    private CarService carService = new CarServiceImpl(new CarDAOImpl());
    private UserService userService = new UserServiceImpl(new UserDAOImpl());
    private OrderService orderService = new OrderServiceImpl(new OrderDAOImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //List<Car> cars = filterCar(CarDAO.getAll(), req.getParameter("sort"), req.getParameter("order"), req.getParameter("brand"), req.getParameter("rate"));

        List<Car> cars = new ArrayList<>();
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        String brand = req.getParameter("brand");
        String rate = req.getParameter("rate");
        req.setAttribute("brands", carService.getBrands());
        int page = 1;
        int recordsPerPage = 2;
        if (req.getParameter("page") != null) page = Integer.parseInt(req.getParameter("page"));
        int noOfRecords;
        int noOfPages;
        if (rate != null && !rate.equals("all") && brand != null && !brand.equals("all")) {
            noOfRecords = carService.countMatchBrandAndClass(brand, rate);
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            if (sort != null && !sort.equals("price")) {
                if (order != null && order.equals("DESC")) {
                    cars = carService.sortByPriceMatchBrandAndClassDESC(brand, rate, (page - 1) * recordsPerPage, recordsPerPage);
                } else {
                    cars = carService.sortByPriceMatchBrandAndClassASC(brand, rate, (page - 1) * recordsPerPage, recordsPerPage);
                }
            } else {
                if (order != null && order.equals("DESC")) {
                    cars = carService.sortByNameMatchBrandAndClassDESC(brand, rate, (page - 1) * recordsPerPage, recordsPerPage);
                } else {
                    cars = carService.sortByNameMatchBrandAndClassASC(brand, rate, (page - 1) * recordsPerPage, recordsPerPage);
                }
            }
        } else if (brand != null && !brand.equals("all")) {
            noOfRecords = carService.countMatchBrand(brand);
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            if (sort != null && !sort.equals("price")) {
                if (order != null && order.equals("DESC")) {
                    cars = carService.sortByPriceMatchBrandDESC(brand, (page - 1) * recordsPerPage, recordsPerPage);
                } else {
                    cars = carService.sortByPriceMatchBrandASC(brand, (page - 1) * recordsPerPage, recordsPerPage);
                }
            } else {
                if (order != null && order.equals("DESC")) {
                    cars = carService.sortByNameMatchBrandDESC(brand, (page - 1) * recordsPerPage, recordsPerPage);
                } else {
                    cars = carService.sortByNameMatchBrandASC(brand, (page - 1) * recordsPerPage, recordsPerPage);
                }
            }
        } else if (rate != null && !rate.equals("all")) {
            noOfRecords = carService.countMatchClass(rate);
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            if (sort != null && !sort.equals("price")) {
                if (order != null && order.equals("DESC")) {
                    cars = carService.sortByPriceMatchRateDESC(rate, (page - 1) * recordsPerPage, recordsPerPage);
                } else {
                    cars = carService.sortByPriceMatchRateASC(rate, (page - 1) * recordsPerPage, recordsPerPage);
                }
            } else {
                if (order != null && order.equals("DESC")) {
                    cars = carService.sortByNameMatchRateDESC(rate, (page - 1) * recordsPerPage, recordsPerPage);
                } else {
                    cars = carService.sortByNameMatchRateASC(rate, (page - 1) * recordsPerPage, recordsPerPage);
                }
            }
        }else if (sort != null && sort.equals("price")) {
            noOfRecords = carService.countAllCars();
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            if (order != null && order.equals("DESC")) {
                cars = carService.sortByPriceDESC((page - 1) * recordsPerPage, recordsPerPage);
            } else {
                cars = carService.sortByPriceASC((page - 1) * recordsPerPage, recordsPerPage);
            }
        } else if (sort != null && sort.equals("model")) {
            noOfRecords = carService.countAllCars();
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            if (order != null && order.equals("DESC")) {
                cars = carService.sortByNameDESC((page - 1) * recordsPerPage, recordsPerPage);
            } else {
                cars = carService.sortByNameASC((page - 1) * recordsPerPage, recordsPerPage);
            }
        } else {
            noOfRecords = carService.countAllCars();
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            cars = carService.getOffset((page - 1) * recordsPerPage, recordsPerPage);
        }


        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        forwardListCar(req, resp, cars);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("role") != User.Role.USER) {
            //System.out.println("ROLE:" + req.getSession().getAttribute("role"));
            resp.sendRedirect("login");
            return;
        }
        int userid = userService.getByLogin((String) req.getSession().getAttribute("login")).getId();
        List<Order> orders = orderService.getAllOrderByUserId(userid);
        if (orders.stream().anyMatch(x -> (!x.getState().equals(Order.State.FINISH) && !x.getState().equals(Order.State.RETURN)))) {
            req.getSession().setAttribute("message", "label.order.notFinishRent");
            resp.sendRedirect("catalog");
            return;
        }
        int idCar = Integer.parseInt(req.getParameter("idCar"));
        String action = req.getParameter("action");
        switch (action) {
            case "makeOrder":
                req.setAttribute("idCar", idCar);
                req.getRequestDispatcher("/new_order.jsp").forward(req, resp);
                break;
            case "createOrder":
                makeOrder(req, resp);
                break;
        }

    }


    private void makeOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String serialNumber = req.getParameter("serial");
        String expireDate = req.getParameter("expire");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        Map<String, String> viewAttributes = new HashMap<>();
        req.setAttribute("idCar", req.getParameter("idCar"));
        viewAttributes.put("serialNumber", serialNumber);
        viewAttributes.put("expireDate", expireDate);
        viewAttributes.put("startDate", startDate);
        viewAttributes.put("endDate", endDate);
        try {
            Date expire = new SimpleDateFormat("yyyy-MM-dd").parse(expireDate);
            Date start = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            Date end = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
            if (!Validator.drivingLicence(serialNumber)) {
                viewAttributes.put("message", "label.warning.licence");
                passErrorToView(req, resp, viewAttributes);
                return;
            }
            if (expire.compareTo(end) < 0) {
                viewAttributes.put("message", "label.warning.expireDate");
                passErrorToView(req, resp, viewAttributes);
                return;
            }
            if (endDate.compareTo(startDate) <= 0) {
                viewAttributes.put("message", "label.warning.endDate");
                passErrorToView(req, resp, viewAttributes);
                return;
            }

            if (start.compareTo(new Date()) < 0) {
                viewAttributes.put("message", "label.warning.startDate");
                passErrorToView(req, resp, viewAttributes);
                return;
            }
            int countOfDay = (int) ((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24));
            Order order = new Order();
            order.setIdUser(userService.getByLogin((String) req.getSession().getAttribute("login")).getId());
            order.setIdCar(Integer.parseInt(req.getParameter("idCar")));
            order.setStart_date(start);
            order.setEnd_date(end);
            order.setPassportExpireDate(expire);
            order.setWithDriver(Boolean.parseBoolean(req.getParameter("check")));
            order.setState(Order.State.SEND);
            order.setPassportSerial(req.getParameter("serial"));
            if (order.isWithDriver()) {
                order.setAccount(countOfDay * (int) (carService.getCarById(Integer.parseInt(req.getParameter("idCar"))).getPrice() * 1.5));
            } else {
                order.setAccount(countOfDay * (carService.getCarById(Integer.parseInt(req.getParameter("idCar"))).getPrice()));
            }
            order.setAccountDamage(0);
            order.setMessage("");
            orderService.addOrder(order);
            resp.sendRedirect("userOrders");
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void forwardListCar(HttpServletRequest req, HttpServletResponse resp, List<Car> cars)
            throws ServletException, IOException {
        String nextJSP = "/catalog.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(nextJSP);
        req.setAttribute("carList", cars);
        dispatcher.forward(req, resp);
    }

    private void passErrorToView(HttpServletRequest request, HttpServletResponse response, Map<String, String> viewAttributes) throws ServletException, IOException {
        for (Map.Entry<String, String> entry : viewAttributes.entrySet())
            request.setAttribute(entry.getKey(), entry.getValue());
        request.getRequestDispatcher("new_order.jsp").forward(request, response);
    }
}
