package com.example.CarRent.controller;

import com.example.CarRent.Service.CarService;
import com.example.CarRent.models.Car;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin")
class CarsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CarService carService;

    @Test
        //@WithMockUser(username = "user", password = "1", authorities = "ADMIN")
    void allCars() throws Exception {
        this.mockMvc.perform(get("/admin/car")).andDo(print()).andExpect(status().isOk());
        verify(carService,times(1)).getAllCar();
    }

    @Test
    void newCar() throws Exception {
        mockMvc.perform(get("/admin/car/new")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>new car</title>")));
    }

    @Test
    void editCar() throws Exception {
        when(carService.getCarById(3)).thenReturn(new Car());
        mockMvc.perform(get("/admin/car/3/edit")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>edit car</title>")));
        verify(carService,times(1)).getCarById(3);
    }

    @Test
    void addCar() throws Exception {
        mockMvc.perform(post("/admin/car")
                        .param("brand","BMW")
                        .param("model","x5")
                        .param("releaseDate","2005-04-12")
                        .param("price","22")
                        .param("autoClass","B")
                        .with(csrf())).andDo(print())
                .andExpect(status().is3xxRedirection());
        verify(carService,times(1)).addCar(any(Car.class));
    }

    @Test
    void deleteTest() throws Exception {
        Car car = new Car();
        car.setState(Car.State.AVAIL);
        car.setId(3);
        when(carService.getCarById(3)).thenReturn(car);
        mockMvc.perform(delete("/admin/car/3")
                        .with(csrf())).andDo(print())
                .andExpect(status().is3xxRedirection());
        verify(carService,times(1)).getCarById(3);
        verify(carService,times(1)).deleteCar(3);
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(patch("/admin/car/3")
                        .param("brand","BMW")
                        .param("model","x5")
                        .param("releaseDate","2005-04-12")
                        .param("price","22")
                        .param("autoClass","B")
                        .with(csrf())).andDo(print())
                .andExpect(status().is3xxRedirection());
        verify(carService,times(1)).updateCar(eq(3),any(Car.class));
    }
}