package com.example.CarRent.Service;

import com.example.CarRent.models.Car;
import com.example.CarRent.repo.CarRepo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j
public class CarService {
    @Autowired
    private CarRepo carRepo;

    public Car getCarById(int id) {
        Car carFromDb = carRepo.findById(id).orElse(null);
        log.debug("find car with id:" + id);
        return carFromDb;
    }

    public boolean addCar(Car car) {
        Car carFromDb = carRepo.findById(car.getId()).orElse(null);

        if (carFromDb != null) {
            return false;
        }
        log.debug("add new car");
        carRepo.save(car);
        return true;
    }

    public boolean updateCar(int id, Car car) {
        Car carFromDb = carRepo.findById(id).orElse(null);
        if (carFromDb == null) {
            return false;
        }
        carFromDb.setBrand(car.getBrand());
        carFromDb.setModel(car.getModel());
        carFromDb.setAutoClass(car.getAutoClass());
        carFromDb.setReleaseDate(car.getReleaseDate());
        carFromDb.setPrice(car.getPrice());
        carRepo.save(carFromDb);
        log.debug("update car with id: "+id);
        return true;
    }

    public List<Car> getAllCar() {
        List<Car> cars = carRepo.findAll();
        log.debug("return all cars");
        return cars;
    }

    public boolean deleteCar(int id) {
        Car carFromDb = carRepo.findById(id).orElse(null);
        if (carFromDb == null) {
            return false;
        }
        carRepo.deleteById(carFromDb.getId());
        log.debug("delete car with id: "+id);
        return true;
    }

    public Page<Car> getAll(Pageable pageable) {
        return carRepo.findAll(pageable);
    }

    public List<String> getAllBrands() {
        return carRepo.getBrands();
    }

    public Page<Car> returnList(String brand, String rate, String sort, String order, int page) {
        Page<Car> cars;
        if (!brand.equals("") && !brand.equals("all") && !rate.equals("") && !rate.equals("all")) {
            if (sort.equals("price")) {
                if (order.equals("DESC")) {
                    cars = carRepo.findByBrandAndAutoClass(PageRequest.of(page, 1, Sort.by("price").descending()), brand, Car.Rate.valueOf(rate));
                } else {
                    cars = carRepo.findByBrandAndAutoClass(PageRequest.of(page, 1, Sort.by("price").ascending()), brand, Car.Rate.valueOf(rate));
                }
            } else {
                if (order.equals("DESC")) {
                    cars = carRepo.findByBrandAndAutoClass(PageRequest.of(page, 1, Sort.by("model").descending()), brand, Car.Rate.valueOf(rate));
                } else {
                    cars = carRepo.findByBrandAndAutoClass(PageRequest.of(page, 1, Sort.by("model").ascending()), brand, Car.Rate.valueOf(rate));
                }
            }
        } else if (!brand.equals("") && !brand.equals("all")) {
            if (sort.equals("price")) {
                if (order.equals("DESC")) {
                    cars = carRepo.findByBrand(PageRequest.of(page, 1, Sort.by("price").descending()), brand);
                } else {
                    cars = carRepo.findByBrand(PageRequest.of(page, 1, Sort.by("price").ascending()), brand);
                }
            } else {
                if (order.equals("DESC")) {
                    cars = carRepo.findByBrand(PageRequest.of(page, 1, Sort.by("model").descending()), brand);
                } else {
                    cars = carRepo.findByBrand(PageRequest.of(page, 1, Sort.by("model").ascending()), brand);
                }
            }
        } else if (!rate.equals("") && !rate.equals("all")) {
            if (sort.equals("price")) {
                if (order.equals("DESC")) {
                    cars = carRepo.findByAutoClass(PageRequest.of(page, 1, Sort.by("price").descending()), Car.Rate.valueOf(rate));
                } else {
                    cars = carRepo.findByAutoClass(PageRequest.of(page, 1, Sort.by("price").ascending()), Car.Rate.valueOf(rate));
                }
            } else {
                if (order.equals("DESC")) {
                    cars = carRepo.findByAutoClass(PageRequest.of(page, 1, Sort.by("model").descending()), Car.Rate.valueOf(rate));
                } else {
                    cars = carRepo.findByAutoClass(PageRequest.of(page, 1, Sort.by("model").ascending()), Car.Rate.valueOf(rate));
                }
            }
        } else {
            if (sort.equals("price")) {
                if (order.equals("DESC")) {
                    cars = carRepo.findAll(PageRequest.of(page, 1, Sort.by("price").descending()));
                } else {
                    cars = carRepo.findAll(PageRequest.of(page, 1, Sort.by("price").ascending()));
                }
            } else {
                if (order.equals("DESC")) {
                    cars = carRepo.findAll(PageRequest.of(page, 1, Sort.by("model").descending()));
                } else {
                    cars = carRepo.findAll(PageRequest.of(page, 1, Sort.by("model").ascending()));
                }
            }
        }
        return cars;
    }

    public static String getFilter(String brand, String rate, String sort, String order) {
        StringBuilder result = new StringBuilder();
        List<String> list = new ArrayList<>();
        if (!brand.equals("") && !brand.equals("all")) {
            result.append("&brand=").append(brand);
        }
        if (!rate.equals("") && !rate.equals("all")) {
            result.append("&rate=").append(rate);
        }
        if (!sort.equals("")) {
            result.append("&sort=").append(sort);
        }
        if (!order.equals("")) {
            result.append("&order=").append(order);
        }
        if (!result.isEmpty()) {
            result.deleteCharAt(0);
            result.insert(0, "?");
        }
        log.debug("generate filter"+result.toString());
        return result.toString();
    }
}
