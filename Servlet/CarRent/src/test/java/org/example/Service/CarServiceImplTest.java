package org.example.Service;

import org.example.Model.Car;
import org.example.Model.DAO.CarDAO;
import org.example.Service.CarService;
import org.example.Service.CarServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CarServiceImplTest {
    @Mock
    private CarDAO carDAO;

    private CarService carService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        carService = new CarServiceImpl(carDAO);
    }
    @Test
    public void addCar() {
        when(carDAO.create(any(Car.class))).thenReturn(true);
        assertEquals(true,carService.addCar(new Car()));
    }

    @Test
    public void deleteCar() {
        Car car = new Car();
        car.setId(3);
        when(carDAO.delete(3)).thenReturn(true);
        assertEquals(true,carService.deleteCar(car));
    }

    @Test
    public void deleteCarById() {
        Car car = new Car();
        car.setId(3);
        when(carDAO.delete(3)).thenReturn(true);
        assertEquals(true,carService.deleteCarById(car.getId()));
    }

    @Test
    public void updateCar() {
        when(carDAO.update(any(Car.class))).thenReturn(true);
        assertEquals(true,carService.updateCar(new Car()));
    }

    @Test
    public void getCarById() {
        Car car = new Car();
        car.setId(4);
        car.setBrand("BMW");
        when(carDAO.getById(4)).thenReturn(car);
        assertEquals("BMW",carService.getCarById(4).getBrand());
    }

    @Test
    public void countAllCars() {
        when(carDAO.countAllCars()).thenReturn(3);
        assertEquals(3,carService.countAllCars());
    }

    @Test
    public void getBrands() {
        when(carDAO.getBrands()).thenReturn(Arrays.asList("BMW","AUDI","FORD"));
        assertEquals("[BMW, AUDI, FORD]",carService.getBrands().toString());
    }

    @Test
    public void getOffset() {
        when(carDAO.offSet(1,3)).thenReturn(Arrays.asList(new Car(),new Car(),new Car()));
        assertEquals(3,carService.getOffset(1,3).size());
    }

    @Test
    public void countMatchBrand() {
        when(carDAO.countMatchBrand("BMW")).thenReturn(12);
        assertEquals(12,carService.countMatchBrand("BMW"));
    }

    @Test
    public void countMatchClass() {
        when(carDAO.countMatchBrand("A")).thenReturn(11);
        assertEquals(11,carService.countMatchBrand("A"));
    }

    @Test
    public void countMatchBrandAndClass() {
        when(carDAO.countMatchBrandAndClass("BMW","A")).thenReturn(3);
        assertEquals(3,carService.countMatchBrandAndClass("BMW","A"));
    }

    @Test
    public void sortByPriceASC() {
        when(carDAO.sortByPriceASC(3,3)).thenReturn(Arrays.asList(new Car.CarBuilder().price(12).build(),new Car.CarBuilder().price(20).build(),new Car.CarBuilder().price(25).build()));
        assertEquals("[Car{id=0, brand='null', model='null', releaseDate=null, state=null, autoClass=null, price=12}, Car{id=0, brand='null', model='null', releaseDate=null, state=null, autoClass=null, price=20}, Car{id=0, brand='null', model='null', releaseDate=null, state=null, autoClass=null, price=25}]",carService.sortByPriceASC(3,3).toString());
    }

    @Test
    public void sortByPriceDESC() {
        when(carDAO.sortByPriceDESC(3,3)).thenReturn(Arrays.asList(new Car.CarBuilder().price(25).build(),new Car.CarBuilder().price(20).build(),new Car.CarBuilder().price(11).build()));
        assertEquals("[Car{id=0, brand='null', model='null', releaseDate=null, state=null, autoClass=null, price=25}, Car{id=0, brand='null', model='null', releaseDate=null, state=null, autoClass=null, price=20}, Car{id=0, brand='null', model='null', releaseDate=null, state=null, autoClass=null, price=11}]",carService.sortByPriceDESC(3,3).toString());

    }

    @Test
    public void sortByNameASC() {
        when(carDAO.sortByNameASC(3,3)).thenReturn(Arrays.asList(new Car.CarBuilder().model("A").build(),new Car.CarBuilder().model("B").build(),new Car.CarBuilder().model("C").build()));
        assertEquals("[Car{id=0, brand='null', model='A', releaseDate=null, state=null, autoClass=null, price=0}, Car{id=0, brand='null', model='B', releaseDate=null, state=null, autoClass=null, price=0}, Car{id=0, brand='null', model='C', releaseDate=null, state=null, autoClass=null, price=0}]",carService.sortByNameASC(3,3).toString());

    }

    @Test
    public void sortByNameDESC() {
        when(carDAO.sortByNameDESC(3,3)).thenReturn(Arrays.asList(new Car.CarBuilder().model("C").build(),new Car.CarBuilder().model("B").build(),new Car.CarBuilder().model("A").build()));
        assertEquals("[Car{id=0, brand='null', model='C', releaseDate=null, state=null, autoClass=null, price=0}, Car{id=0, brand='null', model='B', releaseDate=null, state=null, autoClass=null, price=0}, Car{id=0, brand='null', model='A', releaseDate=null, state=null, autoClass=null, price=0}]",carService.sortByNameDESC(3,3).toString());

    }

    @Test
    public void sortByPriceMatchBrandASC() {
        when(carDAO.sortByPriceMatchBrandASC("BMW",2,3)).thenReturn(Arrays.asList(new Car.CarBuilder().brand("BMW").price(11).build(),new Car.CarBuilder().brand("BMW").price(22).build(),new Car.CarBuilder().brand("BMW").price(25).build()));
        assertEquals("[Car{id=0, brand='BMW', model='null', releaseDate=null, state=null, autoClass=null, price=11}, " +
                "Car{id=0, brand='BMW', model='null', releaseDate=null, state=null, autoClass=null, price=22}, " +
                "Car{id=0, brand='BMW', model='null', releaseDate=null, state=null, autoClass=null, price=25}]",
                carService.sortByPriceMatchBrandASC("BMW",2,3).toString());

    }

    @Test
    public void sortByPriceMatchBrandDESC() {
        when(carDAO.sortByPriceMatchBrandDESC("BMW",2,3)).thenReturn(Arrays.asList(new Car.CarBuilder().brand("BMW").price(25).build(),new Car.CarBuilder().brand("BMW").price(22).build(),new Car.CarBuilder().brand("BMW").price(11).build()));
        assertEquals("[Car{id=0, brand='BMW', model='null', releaseDate=null, state=null, autoClass=null, price=25}, " +
                        "Car{id=0, brand='BMW', model='null', releaseDate=null, state=null, autoClass=null, price=22}, " +
                        "Car{id=0, brand='BMW', model='null', releaseDate=null, state=null, autoClass=null, price=11}]",
                carService.sortByPriceMatchBrandDESC("BMW",2,3).toString());
    }

    @Test
    public void sortByNameMatchBrandASC() {
        when(carDAO.sortByNameMatchBrandASC("BMW",2,3)).thenReturn(Arrays.asList(new Car.CarBuilder().brand("BMW").model("A").build(),new Car.CarBuilder().brand("BMW").model("B").build(),new Car.CarBuilder().brand("BMW").model("C").build()));
        assertEquals("[Car{id=0, brand='BMW', model='A', releaseDate=null, state=null, autoClass=null, price=0}, " +
                        "Car{id=0, brand='BMW', model='B', releaseDate=null, state=null, autoClass=null, price=0}, " +
                        "Car{id=0, brand='BMW', model='C', releaseDate=null, state=null, autoClass=null, price=0}]",
                carService.sortByNameMatchBrandASC("BMW",2,3).toString());
    }

    @Test
    public void sortByNameMatchBrandDESC() {
        when(carDAO.sortByNameMatchBrandDESC("BMW",2,3)).thenReturn(Arrays.asList(new Car.CarBuilder().brand("BMW").model("C").build(),new Car.CarBuilder().brand("BMW").model("B").build(),new Car.CarBuilder().brand("BMW").model("A").build()));
        assertEquals("[Car{id=0, brand='BMW', model='C', releaseDate=null, state=null, autoClass=null, price=0}, " +
                        "Car{id=0, brand='BMW', model='B', releaseDate=null, state=null, autoClass=null, price=0}, " +
                        "Car{id=0, brand='BMW', model='A', releaseDate=null, state=null, autoClass=null, price=0}]",
                carService.sortByNameMatchBrandDESC("BMW",2,3).toString());
    }

    @Test
    public void sortByPriceMatchRateASC() {
        when(carDAO.sortByPriceMatchRateASC("B",2,3)).thenReturn(Arrays.asList(new Car.CarBuilder().price(10).state(Car.Class.B).build(),new Car.CarBuilder().price(20).state(Car.Class.B).build(),new Car.CarBuilder().price(30).state(Car.Class.B).build()));
        assertEquals("[Car{id=0, brand='null', model='null', releaseDate=null, state=null, autoClass=B, price=10}, " +
                        "Car{id=0, brand='null', model='null', releaseDate=null, state=null, autoClass=B, price=20}, " +
                        "Car{id=0, brand='null', model='null', releaseDate=null, state=null, autoClass=B, price=30}]",
                carService.sortByPriceMatchRateASC("B",2,3).toString());
    }

    @Test
    public void sortByPriceMatchRateDESC() {
        when(carDAO.sortByPriceMatchRateDESC("B",2,3)).thenReturn(Arrays.asList(new Car.CarBuilder().price(30).state(Car.Class.B).build(),new Car.CarBuilder().price(20).state(Car.Class.B).build(),new Car.CarBuilder().price(10).state(Car.Class.B).build()));
        assertEquals("[Car{id=0, brand='null', model='null', releaseDate=null, state=null, autoClass=B, price=30}, " +
                        "Car{id=0, brand='null', model='null', releaseDate=null, state=null, autoClass=B, price=20}, " +
                        "Car{id=0, brand='null', model='null', releaseDate=null, state=null, autoClass=B, price=10}]",
                carService.sortByPriceMatchRateDESC("B",2,3).toString());
    }

    @Test
    public void sortByNameMatchRateASC() {
        when(carDAO.sortByNameMatchRateASC("B",2,3)).thenReturn(Arrays.asList(new Car.CarBuilder().model("A").state(Car.Class.B).build(),new Car.CarBuilder().model("B").state(Car.Class.B).build(),new Car.CarBuilder().model("C").state(Car.Class.B).build()));
        assertEquals("[Car{id=0, brand='null', model='A', releaseDate=null, state=null, autoClass=B, price=0}, " +
                        "Car{id=0, brand='null', model='B', releaseDate=null, state=null, autoClass=B, price=0}, " +
                        "Car{id=0, brand='null', model='C', releaseDate=null, state=null, autoClass=B, price=0}]",
                carService.sortByNameMatchRateASC("B",2,3).toString());
    }

    @Test
    public void sortByNameMatchRateDESC() {
        when(carDAO.sortByNameMatchRateDESC("B",2,3)).thenReturn(Arrays.asList(new Car.CarBuilder().model("C").state(Car.Class.B).build(),new Car.CarBuilder().model("B").state(Car.Class.B).build(),new Car.CarBuilder().model("A").state(Car.Class.B).build()));
        assertEquals("[Car{id=0, brand='null', model='C', releaseDate=null, state=null, autoClass=B, price=0}, " +
                        "Car{id=0, brand='null', model='B', releaseDate=null, state=null, autoClass=B, price=0}, " +
                        "Car{id=0, brand='null', model='A', releaseDate=null, state=null, autoClass=B, price=0}]",
                carService.sortByNameMatchRateDESC("B",2,3).toString());
    }

    @Test
    public void sortByPriceMatchBrandAndClassASC() {
        when(carDAO.sortByPriceMatchBrandAndClassASC("BMW","C",2,3)).thenReturn(Arrays.asList(new Car.CarBuilder().price(10).state(Car.Class.C).build(),new Car.CarBuilder().price(20).state(Car.Class.C).build(),new Car.CarBuilder().price(30).state(Car.Class.C).build()));
        assertEquals("[Car{id=0, brand='null', model='null', releaseDate=null, state=null, autoClass=C, price=10}, " +
                        "Car{id=0, brand='null', model='null', releaseDate=null, state=null, autoClass=C, price=20}, " +
                        "Car{id=0, brand='null', model='null', releaseDate=null, state=null, autoClass=C, price=30}]",
                carService.sortByPriceMatchBrandAndClassASC("BMW","C",2,3).toString());
    }

    @Test
    public void sortByPriceMatchBrandAndClassDESC() {
        when(carDAO.sortByPriceMatchBrandAndClassDESC("BMW","C",2,3)).thenReturn(Arrays.asList(new Car.CarBuilder().price(30).state(Car.Class.C).build(),new Car.CarBuilder().price(20).state(Car.Class.C).build(),new Car.CarBuilder().price(10).state(Car.Class.C).build()));
        assertEquals("[Car{id=0, brand='null', model='null', releaseDate=null, state=null, autoClass=C, price=30}, " +
                        "Car{id=0, brand='null', model='null', releaseDate=null, state=null, autoClass=C, price=20}, " +
                        "Car{id=0, brand='null', model='null', releaseDate=null, state=null, autoClass=C, price=10}]",
                carService.sortByPriceMatchBrandAndClassDESC("BMW","C",2,3).toString());
    }

    @Test
    public void sortByNameMatchBrandAndClassASC() {
        when(carDAO.sortByNameMatchBrandAndClassASC("BMW","C",2,3)).thenReturn(Arrays.asList(new Car.CarBuilder().model("A").model("BMW").state(Car.Class.C).state(Car.Class.C).build(),new Car.CarBuilder().model("B").model("BMW").state(Car.Class.C).build(),new Car.CarBuilder().model("C").model("BMW").state(Car.Class.C).build()));
        assertEquals("[Car{id=0, brand='null', model='BMW', releaseDate=null, state=null, autoClass=C, price=0}, " +
                        "Car{id=0, brand='null', model='BMW', releaseDate=null, state=null, autoClass=C, price=0}, " +
                        "Car{id=0, brand='null', model='BMW', releaseDate=null, state=null, autoClass=C, price=0}]",
                carService.sortByNameMatchBrandAndClassASC("BMW","C",2,3).toString());
    }

    @Test
    public void sortByNameMatchBrandAndClassDESC() {
        when(carDAO.sortByNameMatchBrandAndClassDESC("BMW","C",2,3)).thenReturn(Arrays.asList(new Car.CarBuilder().model("C").model("BMW").state(Car.Class.C).state(Car.Class.C).build(),new Car.CarBuilder().model("B").model("BMW").state(Car.Class.C).build(),new Car.CarBuilder().model("A").model("BMW").state(Car.Class.C).build()));
        assertEquals("[Car{id=0, brand='null', model='BMW', releaseDate=null, state=null, autoClass=C, price=0}, " +
                        "Car{id=0, brand='null', model='BMW', releaseDate=null, state=null, autoClass=C, price=0}, " +
                        "Car{id=0, brand='null', model='BMW', releaseDate=null, state=null, autoClass=C, price=0}]",
                carService.sortByNameMatchBrandAndClassDESC("BMW","C",2,3).toString());
    }

    @Test
    public void getAllCar() {
        when(carDAO.getAll()).thenReturn(Arrays.asList(new Car(),new Car(),new Car(),new Car()));
        assertEquals(4,carService.getAllCar().size());
    }
}