package com.example.CarRent.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CatalogControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void allCars() throws Exception {
        mockMvc.perform(get("/catalog")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>catalog</title>")));
    }

    @Test
    void redirect() throws Exception {
        mockMvc.perform(get("/")).andDo(print()).andExpect(status().is3xxRedirection());
    }
}