package com.example.CarRent.Service;

import com.example.CarRent.models.Car;
import com.example.CarRent.repo.CarRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CarServiceTest {
    @Autowired
    private CarService carService;
    @MockBean
    private CarRepo carRepo;
    @Test
    void getCarById() {
        Car car = new Car();
        car.setId(3);
        car.setBrand("AUDI");
        car.setModel("RS8");
        Mockito.when(carRepo.findById(3)).thenReturn(Optional.of(car));
        Car carTest =carService.getCarById(3);
        assertEquals("Car(id=3, brand=AUDI, model=RS8, releaseDate=null, state=null, autoClass=null, price=0, orders=null)",carTest.toString());
        Mockito.verify(carRepo,Mockito.times(1)).findById(3);
    }

    @Test
    void addCar() {
        Car car = new Car();
        car.setId(4);
        boolean isCarAdded = carService.addCar(car);
        Mockito.verify(carRepo,Mockito.times(1)).save(car);
        Mockito.verify(carRepo,Mockito.times(1)).findById(4);
        assertEquals(true,isCarAdded);
    }

    @Test
    void updateCar() {
        Car car = new Car();
        car.setId(3);
        Mockito.when(carRepo.findById(3)).thenReturn(Optional.of(new Car()));
        boolean isCarUpdated = carService.updateCar(3,car);
        assertEquals(true,isCarUpdated);
        Mockito.verify(carRepo,Mockito.times(1)).findById(3);
        Mockito.verify(carRepo,Mockito.times(1)).save(Mockito.any(Car.class));

    }

    @Test
    void getAllCar() {
        Mockito.when(carRepo.findAll()).thenReturn(Arrays.asList(new Car(),new Car(),new Car()));
        List<Car> cars =carService.getAllCar();
        assertEquals(3,cars.size());
        Mockito.verify(carRepo,Mockito.times(1)).findAll();
    }

    @Test
    void deleteCar() {
        Car car = new Car();
        car.setId(3);
        Mockito.when(carRepo.findById(car.getId())).thenReturn(Optional.of(car));
        boolean isCarDelete = carService.deleteCar(3);
        assertEquals(true,isCarDelete);
        Mockito.verify(carRepo,Mockito.times(1)).findById(car.getId());
        Mockito.verify(carRepo,Mockito.times(1)).deleteById(car.getId());
    }

    @Test
    void getAll() {
        Car car = new Car();
        car.setId(3);
        Mockito.when(carRepo.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<Car>(Arrays.asList(new Car(),new Car())));
        Page<Car> carPage = carService.getAll(PageRequest.of(1,2, Sort.by("price")));
        assertEquals(2,carPage.getTotalElements());
        Mockito.verify(carRepo,Mockito.times(1)).findAll(Mockito.any(Pageable.class));

    }

    @Test
    void getAllBrands() {
        Car car = new Car();
        car.setId(3);
        Mockito.when(carRepo.getBrands()).thenReturn(Arrays.asList("Audi","BMW","VW"));
        List<String> brands = carService.getAllBrands();
        assertEquals("[Audi, BMW, VW]", brands.toString());
        Mockito.verify(carRepo,Mockito.times(1)).getBrands();
    }

    @Test
    void returnList() {
        carService.returnList("AUDI","A","price","ASC",1);
        Mockito.verify(carRepo,Mockito.times(1)).findByBrandAndAutoClass(PageRequest.of(1, 1, Sort.by("price").ascending()), "AUDI", Car.Rate.valueOf("A"));

        carService.returnList("AUDI","A","price","DESC",1);
        Mockito.verify(carRepo,Mockito.times(1)).findByBrandAndAutoClass(PageRequest.of(1, 1, Sort.by("price").descending()), "AUDI", Car.Rate.valueOf("A"));

        carService.returnList("AUDI","A","model","ASC",1);
        Mockito.verify(carRepo,Mockito.times(1)).findByBrandAndAutoClass(PageRequest.of(1, 1, Sort.by("model").ascending()), "AUDI", Car.Rate.valueOf("A"));

        carService.returnList("AUDI","A","model","DESC",1);
        Mockito.verify(carRepo,Mockito.times(1)).findByBrandAndAutoClass(PageRequest.of(1, 1, Sort.by("model").descending()), "AUDI", Car.Rate.valueOf("A"));

        carService.returnList("AUDI","all","price","ASC",1);
        Mockito.verify(carRepo,Mockito.times(1)).findByBrand(PageRequest.of(1, 1, Sort.by("price").ascending()), "AUDI");

        carService.returnList("AUDI","all","price","DESC",1);
        Mockito.verify(carRepo,Mockito.times(1)).findByBrand(PageRequest.of(1, 1, Sort.by("price").descending()), "AUDI");

        carService.returnList("AUDI","all","model","ASC",1);
        Mockito.verify(carRepo,Mockito.times(1)).findByBrand(PageRequest.of(1, 1, Sort.by("model").ascending()), "AUDI");

        carService.returnList("AUDI","all","model","DESC",1);
        Mockito.verify(carRepo,Mockito.times(1)).findByBrand(PageRequest.of(1, 1, Sort.by("model").descending()), "AUDI");

        carService.returnList("all","A","price","ASC",1);
        Mockito.verify(carRepo,Mockito.times(1)).findByAutoClass(PageRequest.of(1, 1, Sort.by("price").ascending()), Car.Rate.valueOf("A"));

        carService.returnList("all","A","price","DESC",1);
        Mockito.verify(carRepo,Mockito.times(1)).findByAutoClass(PageRequest.of(1, 1, Sort.by("price").descending()), Car.Rate.valueOf("A"));

        carService.returnList("all","A","model","ASC",1);
        Mockito.verify(carRepo,Mockito.times(1)).findByAutoClass(PageRequest.of(1, 1, Sort.by("model").ascending()), Car.Rate.valueOf("A"));

        carService.returnList("all","A","model","DESC",1);
        Mockito.verify(carRepo,Mockito.times(1)).findByAutoClass(PageRequest.of(1, 1, Sort.by("model").descending()), Car.Rate.valueOf("A"));

        carService.returnList("all","all","model","ASC",1);
        Mockito.verify(carRepo,Mockito.times(1)).findAll(PageRequest.of(1, 1, Sort.by("model").ascending()));

        carService.returnList("all","all","model","DESC",1);
        Mockito.verify(carRepo,Mockito.times(1)).findAll(PageRequest.of(1, 1, Sort.by("model").descending()));

        carService.returnList("all","all","price","ASC",1);
        Mockito.verify(carRepo,Mockito.times(1)).findAll(PageRequest.of(1, 1, Sort.by("price").ascending()));

        carService.returnList("all","all","price","DESC",1);
        Mockito.verify(carRepo,Mockito.times(1)).findAll(PageRequest.of(1, 1, Sort.by("price").descending()));

    }

    @Test
    void getFilter() {
        String filter = CarService.getFilter("AUDI","A","price","ASC");
        assertEquals("?brand=AUDI&rate=A&sort=price&order=ASC",filter);
    }
}