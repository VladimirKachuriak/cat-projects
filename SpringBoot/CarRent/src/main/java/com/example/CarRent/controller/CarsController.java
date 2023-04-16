package com.example.CarRent.controller;

import com.example.CarRent.Service.CarService;
import com.example.CarRent.Service.OrderService;
import com.example.CarRent.models.Car;
import com.example.CarRent.models.Orders;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin/car")
@Log4j
public class CarsController {
    private final CarService carService;
    private final OrderService orderService;

    @Autowired
    public CarsController(CarService carService, OrderService orderService) {
        this.carService = carService;
        this.orderService = orderService;
    }

    @GetMapping("")
    public String allCars(Model model) throws ParseException {
        model.addAttribute("cars", carService.getAllCar());
        log.debug("return list with all cars");
        return "car_list";
    }
    @GetMapping("/new")
    public String newCar(Model model){
        model.addAttribute("car", new Car());
        log.debug("return new_car form");
        return "new_car";
    }
    @GetMapping("/{id}/edit")
    public String editCar(Model model,@PathVariable("id") int id){
        Car car = carService.getCarById(id);
        if(car == null){
            return "redirect:/admin/car";
        }
        if(!model.containsAttribute("car")){
            model.addAttribute("car", car);
        }
        log.debug("return form edit_car");
        return "edit_car";
    }
    @PostMapping("")
    public String addCar(@ModelAttribute("car") @Valid Car car, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "new_car";
        }
        car.setState(Car.State.AVAIL);
        carService.addCar(car);
        redirectAttributes.addFlashAttribute("message","label.car.added");
        log.info("edit car with id: "+car.getId());
        return "redirect:/admin/car";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id,RedirectAttributes redirectAttributes) {
        Car car  = carService.getCarById(id);
        if(car==null){
            return "redirect:/admin/car";
        }
        if(!car.getState().equals(Car.State.AVAIL)){
            redirectAttributes.addFlashAttribute("message","label.car.inuseCar");
            return "redirect:/admin/car";
        }
        carService.deleteCar(car.getId());
        redirectAttributes.addFlashAttribute("message","label.car.delete");
        log.debug("delete car with id"+id);
        return "redirect:/admin/car";
    }
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,Model model, @ModelAttribute("car") @Valid Car car, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            /*redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.car", bindingResult);
            redirectAttributes.addFlashAttribute("car",car);
            return "redirect:/admin/car/"+id+"/edit";*/
            return "edit_car";
        }
        carService.updateCar(id,car);
        redirectAttributes.addFlashAttribute("message","label.car.edit");
        log.debug("delete car successfully with id"+id);
        return "redirect:/admin/car";
    }
}
