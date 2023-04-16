package com.example.CarRent.controller;

import com.example.CarRent.Service.CarService;
import com.example.CarRent.Service.MailSender;
import com.example.CarRent.models.Car;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.IntStream;

@Controller
@Log4j
public class CatalogController {
    private final CarService carService;
    @Autowired
    private MailSender mailSender;

    @Autowired
    public CatalogController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/catalog")
    public String allCars(Model model,
                          @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                          @RequestParam(value = "rate", required = false, defaultValue = "") String rate,
                          @RequestParam(value = "brand", required = false, defaultValue = "") String brand,
                          @RequestParam(value = "sort", required = false, defaultValue = "") String sort,
                          @RequestParam(value = "order", required = false, defaultValue = "") String order) {
        String filter = CarService.getFilter(brand,rate,sort,order);
        Page<Car> cars;
        cars = carService.returnList(brand,rate,sort,order,page);
        model.addAttribute("brands", carService.getAllBrands());
        model.addAttribute("cars", cars);
        model.addAttribute("numbers", IntStream.range(0, cars.getTotalPages()).toArray());
        model.addAttribute("filter",filter);
        log.debug("return catalog.html page");
        return "catalog";
    }

    @GetMapping("/")
    public String redirect(Model model) {
        log.debug("redirect to the /catalog page");
        return "redirect:/catalog";
    }
}
