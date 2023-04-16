package com.example.CarRent.controller;

import com.example.CarRent.Service.CarService;
import com.example.CarRent.Service.OrderService;
import com.example.CarRent.Service.UserService;
import com.example.CarRent.models.Car;
import com.example.CarRent.models.Orders;
import com.example.CarRent.models.User;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('USER')")
@Log4j
public class UserController {
    @Autowired
    private CarService carService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @GetMapping("/order")
    public String orders(@AuthenticationPrincipal User authUser, Model model) {
        User user = userService.findByID(authUser.getId());
        model.addAttribute("user", user);
        log.debug("return all orders list for user with login: " + authUser.getLogin());
        return "user_orders";
    }

    @PostMapping("/topUp")
    public String topUpAccount(@AuthenticationPrincipal User authUser, @RequestParam("money") int money, RedirectAttributes redirectAttributes) {
        ;
        User user = userService.findByID(authUser.getId());
        redirectAttributes.addFlashAttribute("message", "label.message.topUpAccount");
        user.setAccount(user.getAccount() + money);
        log.debug("top up account for user with login: " + authUser.getLogin());
        return "redirect:/user/order";
    }

    @GetMapping("/order/{id}/new")
    public String newOrder(Model model, @PathVariable("id") int carId, @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        System.out.println(user);
        Set<Orders> orders = userService.findByID(user.getId()).getOrders();
        if (orders.stream().anyMatch(x -> (!x.getState().equals(Orders.State.FINISH) && !x.getState().equals(Orders.State.RETURN)))) {
            redirectAttributes.addFlashAttribute("message", "label.order.notFinishRent");
            return "redirect:/catalog";
        }
        model.addAttribute("idCar", carId);
        if (!model.containsAttribute("order")) {
            model.addAttribute("order", new Orders());
        }
        return "new_order";
    }

    /**
     * make payment for the user order
     */
    @PostMapping("/order/{id}/pay")
    public String payOrder(@PathVariable("id") int order_id, RedirectAttributes redirectAttributes) {
        Orders order = orderService.getOrderById(order_id);
        /**
         * if order is WAIT and user have enough money then we can pay for that
         */
        if (order.getState().equals(Orders.State.WAIT)) {
            if (order.getUser().getAccount() < order.getAccount()) {
                redirectAttributes.addFlashAttribute("message", "label.message.notEnoughMoney");
                return "redirect:/user/order";
            }
            order.getUser().setAccount(order.getUser().getAccount() - order.getAccount());
            redirectAttributes.addFlashAttribute("message", "label.message.paidSuccess");
            order.setState(Orders.State.PAID);
            log.debug("user pay  for the order");
            return "redirect:/user/order";
        }
        /**
         *
         * if order state is DAMAGED and user have enough money then we can pay for repair
         */
        if (order.getState().equals(Orders.State.DAMAGED)) {
            if (order.getUser().getAccount() < order.getAccountDamage()) {
                redirectAttributes.addFlashAttribute("message", "label.message.notEnoughMoney");
                return "redirect:/user/order";
            }
            order.getUser().setAccount(order.getUser().getAccount() - order.getAccountDamage());
            order.setState(Orders.State.FINISH);
            order.getCar().setState(Car.State.AVAIL);
            log.debug("user finish  order");
            return "redirect:/user/order";
        }
        if (order.getState().equals(Orders.State.FINISH) || order.getState().equals(Orders.State.RETURN)) {
            redirectAttributes.addFlashAttribute("message", "label.message.yourOrderFinish");
            return "redirect:/user/order";
        }
        redirectAttributes.addFlashAttribute("message", "label.message.waitUntilAdminProcess");
        return "redirect:/user/order";
    }
    /**
     *
     * create new order
     */
    @PostMapping("/order/{id}/new")
    public String addOrder(@AuthenticationPrincipal User user, HttpServletRequest req, @PathVariable("id") int id,
                           @ModelAttribute("order") @Valid Orders order, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) throws ParseException {
        redirectAttributes.addFlashAttribute("order", order);
        String expireDate = req.getParameter("expire");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        redirectAttributes.addFlashAttribute("expireDate", expireDate);
        redirectAttributes.addFlashAttribute("startDate", startDate);
        redirectAttributes.addFlashAttribute("endDate", endDate);
        if (bindingResult.hasErrors()) {
            //redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orders", bindingResult);
            redirectAttributes.addFlashAttribute("order", order);
            redirectAttributes.addFlashAttribute("message", "incorrect.passport");
            return "redirect:/user/order/" + id + "/new";
        }

        Date expire = new SimpleDateFormat("yyyy-MM-dd").parse(expireDate);
        Date start = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date end = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
        if (expire.compareTo(end) < 0) {
            redirectAttributes.addFlashAttribute("message", "label.warning.expireDate");
            return "redirect:/user/order/" + id + "/new";
        }
        if (endDate.compareTo(startDate) <= 0) {
            redirectAttributes.addFlashAttribute("message", "label.warning.endDate");
            return "redirect:/user/order/" + id + "/new";
        }

        if (start.compareTo(new Date()) < 0) {
            redirectAttributes.addFlashAttribute("message", "label.warning.startDate");
            return "redirect:/user/order/" + id + "/new";
        }
        int countOfDay = (int) ((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24));
        Car car = carService.getCarById(id);
        if (order.isWithDriver()) {
            order.setAccount((int) (((float) countOfDay * car.getPrice()) * 1.5));
        } else {
            order.setAccount(countOfDay * car.getPrice());
        }
        order.setPassportExpireDate(expire);
        order.setStart_date(start);
        order.setEnd_date(end);
        order.setAccountDamage(0);
        order.setUser(user);
        order.setCar(car);
        order.setState(Orders.State.SEND);
        order.setMessage("");
        orderService.addOrder(order);
        log.debug("create new order for user with login: " + user.getLogin());
        return "redirect:/catalog";
    }

}
