package com.example.CarRent.controller;

import com.example.CarRent.Service.CarService;
import com.example.CarRent.Service.OrderService;
import com.example.CarRent.Service.UserService;
import com.example.CarRent.models.Car;
import com.example.CarRent.models.Orders;
import com.example.CarRent.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin")
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @MockBean
    private CarService carService;
    @MockBean
    private OrderService orderService;

    @Test
    void allUsers() throws Exception {
        mockMvc.perform(get("/admin/users")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>users</title>")));
    }

    @Test
    void getAllOrders() throws Exception {
        mockMvc.perform(get("/admin/order")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>admin orders</title>")));
        verify(orderService, times(1)).getAllOrder();
    }

    @Test
    void acceptOrder() throws Exception {
        Orders order = new Orders();
        order.setCar(new Car());
        when(orderService.getOrderById(3)).thenReturn(order);
        mockMvc.perform(post("/admin/order/3/accept")
                        .param("orderResponse", "all okay")
                        .with(csrf()))
                .andDo(print()).andExpect(status().is3xxRedirection());
        verify(orderService, times(1)).getOrderById(3);
    }

    @Test
    void rejectOrder() throws Exception {
        Orders order = new Orders();
        order.setCar(new Car());
        when(orderService.getOrderById(3)).thenReturn(order);
        mockMvc.perform(post("/admin/order/3/reject")
                        .param("orderResponse", "all okay")
                        .with(csrf()))
                .andDo(print()).andExpect(status().is3xxRedirection());
        verify(orderService, times(1)).getOrderById(3);
    }

    @Test
    void damageOrder() throws Exception {
        Orders order = new Orders();
        order.setCar(new Car());
        when(orderService.getOrderById(3)).thenReturn(order);
        mockMvc.perform(post("/admin/order/3/damage")
                        .param("orderResponse", "all okay")
                        .param("damageAccount", "23")
                        .with(csrf()))
                .andDo(print()).andExpect(status().is3xxRedirection());
        verify(orderService, times(1)).getOrderById(3);
    }

    @Test
    void finishOrder() throws Exception {
        Orders order = new Orders();
        order.setCar(new Car());
        when(orderService.getOrderById(3)).thenReturn(order);
        mockMvc.perform(post("/admin/order/3/finish")
                        .param("orderResponse", "all okay")
                        .with(csrf()))
                .andDo(print()).andExpect(status().is3xxRedirection());
        verify(orderService, times(1)).getOrderById(3);
    }

    @Test
    void activateUser() throws Exception {
        mockMvc.perform(post("/admin/users/2/active")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void addUser() throws Exception {
        mockMvc.perform(get("/admin/users/new")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>create admin</title>")));
    }

    @Test
    @Transactional
    void addAdmin() throws Exception {
        userService.deleteByLogin("admin2");
        this.mockMvc.perform(post("/admin/users/new").
                        param("login", "admin2").
                        param("password", "12345").
                        param("firstname", "Kiril").
                        param("lastname", "Nestor").
                        param("email", "my@gmail.com").
                        param("phoneNumber", "+1234567")
                        .with(csrf())).
                andDo(print()).andExpect(status().is3xxRedirection());
    }
}