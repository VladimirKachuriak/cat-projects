package com.example.CarRent.controller;

import com.example.CarRent.Service.OrderService;
import com.example.CarRent.Service.UserService;
import com.example.CarRent.models.Car;
import com.example.CarRent.models.Orders;
import com.example.CarRent.models.Role;
import com.example.CarRent.models.User;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
@Log4j
public class AdminController {
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public AdminController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/users")
    public String allUsers(Model model) {
        log.debug("return page with all users");
        model.addAttribute("users", userService.getAllUser());
        return "user_list";
    }

    @GetMapping("/order")
    public String getAllOrders(Model model) {
        log.debug("return page with all orders");
        model.addAttribute("orderList", orderService.getAllOrder());
        return "admin_orders";
    }
    @PostMapping("/order/{id}/accept")
    public String acceptOrder(@PathVariable("id") int id,@RequestParam(value = "orderResponse",defaultValue = "") String message) {
        Orders order = orderService.getOrderById(id);
        order.setMessage(message);
        order.getCar().setState(Car.State.INUSE);
        order.setState(Orders.State.WAIT);
        log.info("order with id:"+id+" is accepted");
        return "redirect:/admin/order";
    }
    @PostMapping("/order/{id}/reject")
    public String rejectOrder(@PathVariable("id") int id,@RequestParam("orderResponse") String message) {
        Orders order = orderService.getOrderById(id);
        order.setMessage(message);
        order.setState(Orders.State.RETURN);
        log.info("order with id:"+id+" is reject");
        return "redirect:/admin/order";
    }
    @PostMapping("/order/{id}/damage")
    public String damageOrder(@PathVariable("id") int id,@RequestParam("orderResponse") String message,@RequestParam("damageAccount") int damageAccount) {
        Orders order = orderService.getOrderById(id);
        order.setMessage(message);
        order.setState(Orders.State.DAMAGED);
        order.getCar().setState(Car.State.DAMAGED);
        order.setAccountDamage(damageAccount);
        log.info("order with id:"+id+" set damage with damage price:"+damageAccount);
        return "redirect:/admin/order";
    }
    @PostMapping("/order/{id}/finish")
    public String finishOrder(@PathVariable("id") int id,@RequestParam("orderResponse") String message) {
        Orders order = orderService.getOrderById(id);
        order.setMessage(message);
        order.setState(Orders.State.FINISH);
        order.getCar().setState(Car.State.AVAIL);
        log.info("order with id:"+id+" is finish");
        return "redirect:/admin/order";
    }
    @PostMapping("/users/{id}/active")
    public String activateUser(@PathVariable("id") int id,RedirectAttributes redirectAttributes) {
        User user = userService.findByID(id);
        if(user.getRoles().contains(Role.ADMIN)){
            redirectAttributes.addFlashAttribute("message","label.message.banAdmin");
            return "redirect:/admin/users";
        }
        if (user.isActive()) {
            redirectAttributes.addFlashAttribute("message","label.message.userBanned");
            user.setActive(false);
        } else {
            redirectAttributes.addFlashAttribute("message","label.message.userUnBanned");
            user.setActive(true);
        }
        log.info("user with id:"+id+" has been blocked");
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/new")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        log.debug("return form new_admin.html");
        return "new_admin";
    }

    @PostMapping("/users/new")
    public String addAdmin(Model model, @ModelAttribute("user")@Valid User user, BindingResult bindingResult, RedirectAttributes redirectMessage) {
        user.setLogin(user.getLogin().trim());
        user.setPassword(user.getPassword().trim());
        User userDB = userService.findByLogin(user.getLogin());
        if(userDB!=null){
            model.addAttribute("loginMessage","label.warning.userAlreadyExist");
            log.debug("user with this login already exist");
            return "new_admin";
        }
        if(bindingResult.hasErrors()){
            log.debug("new_admin form has incorrect some fields");
            return "new_admin";
        }
        redirectMessage.addFlashAttribute("message","label.user.addNewAdmin");
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.ADMIN));
        userService.addUser(user);
        log.debug("admin was created successfully");
        return "redirect:/admin/users";
    }


}
