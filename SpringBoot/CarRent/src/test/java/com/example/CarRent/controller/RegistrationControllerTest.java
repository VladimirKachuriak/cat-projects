package com.example.CarRent.controller;

import com.example.CarRent.Service.UserService;
import com.example.CarRent.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
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
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void registration() throws Exception {
        this.mockMvc.perform(get("/registration")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("registration")));
    }

    @Test
    void addUser() throws Exception {
        when(userService.findByLogin(any(String.class))).thenReturn(null);
        this.mockMvc.perform(post("/registration").
                        param("login","user5").
                        param("password","12345").
                        param("firstname","Kiril").
                        param("lastname","Nestor").
                        param("email","my@gmail.com").
                        param("phoneNumber","+1234567").
                        param("g-recaptcha-response","afsd")
                        .with(csrf())).
                andDo(print()).andExpect(status().isOk());
        verify(userService,times(1)).findByLogin("user5");
    }
}