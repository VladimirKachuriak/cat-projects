package com.example.CarRent.controller;

import com.example.CarRent.Service.CarService;
import com.example.CarRent.Service.OrderService;
import com.example.CarRent.models.Car;
import com.example.CarRent.models.Orders;
import com.example.CarRent.models.Role;
import com.example.CarRent.models.User;
import com.example.CarRent.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "user")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;
    @MockBean
    private OrderService orderService;

    @Test
    void orders() throws Exception {
        this.mockMvc.perform(get("/user/order")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("user")));
    }

    @Test
    void topUpAccount() throws Exception {
        this.mockMvc.perform(post("/user/topUp")
                        .param("money", "25")
                        .with(csrf())).andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void newOrder() throws Exception {
        this.mockMvc.perform(get("/user/order/3/new"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>new order</title>")));

    }

    @Test
    void payOrder() throws Exception {
        Car car =new Car();
        car.setId(1);
        car.setPrice(1);
        User user = new User();
        user.setAccount(100);
        Orders order = new Orders();
        order.setId(3);
        order.setCar(car);
        order.setUser(user);
        order.setState(Orders.State.WAIT);
        when(orderService.getOrderById(3)).thenReturn(order);
        this.mockMvc.perform(post("/user/order/3/pay").with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void addOrder() throws Exception {
        Car car =new Car();
        car.setId(1);
        car.setPrice(1);
        when(carService.getCarById(3)).thenReturn(car);
        this.mockMvc.perform(post("/user/order/3/new")
                        .param("passportSerial","AA1234")
                        .param("withDriver","false")
                        .param("expire","2022-07-12")
                        .param("startDate","2022-07-11")
                        .param("endDate","2022-07-12")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        verify(orderService,times(1)).addOrder(any(Orders.class));
    }
}