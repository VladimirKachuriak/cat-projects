package com.example.CarRent.controller;

import com.example.CarRent.Service.UserService;
import com.example.CarRent.models.Role;
import com.example.CarRent.models.User;
import com.example.CarRent.models.dto.CaptchaResponseDto;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@Log4j
public class RegistrationController {
    private static final String RECAPTCHA_URL="https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    @Value("${recaptcha.secret}")
    private String recaptchaSecret;


    private final UserService userService;
    private final RestTemplate restTemplate;

    @Autowired
    public RegistrationController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user){
        log.debug("return registration form");
        return "registration";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable("code") String code,
                           RedirectAttributes redirectAttributes){
        log.debug("activate user");
        if(userService.activeUser(code)){
            redirectAttributes.addFlashAttribute("message","label.user.profileActivated");
        }else {
            redirectAttributes.addFlashAttribute("message","label.user.incorrectCode");
        }
        return "redirect:/login";
    }
    /**
     *
     * register new user
     */
    @PostMapping("/registration")
    public String addUser(Model model, @ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult,
                          @RequestParam("g-recaptcha-response") String captchaResponse){
        User userDB = userService.findByLogin(user.getLogin());
        String url = String.format(RECAPTCHA_URL,recaptchaSecret,captchaResponse);
        CaptchaResponseDto captchaResponseDto = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        if (!captchaResponseDto.isSuccess()){
            System.out.println("eror");
            model.addAttribute("captchaError","label.user.captcha");
        }
        if(userDB!=null){
            model.addAttribute("loginMessage","label.warning.userAlreadyExist");
            return "registration";
        }
        if(bindingResult.hasErrors()||!captchaResponseDto.isSuccess()){
            return "registration";
        }
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        userService.addUser(user);

        return "redirect:/login";
    }
}
