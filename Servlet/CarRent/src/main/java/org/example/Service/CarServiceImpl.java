package org.example.Service;

import org.apache.log4j.Logger;
import org.example.Model.Car;
import org.example.Model.DAO.CarDAO;

import java.util.List;

public class CarServiceImpl implements CarService {
    private static final Logger log = Logger.getLogger(CarServiceImpl.class.getSimpleName());
    private final CarDAO carDao;

    public CarServiceImpl(CarDAO carDao) {
        this.carDao = carDao;
    }

    public boolean addCar(Car car) {
        log.info("add new car");
        return carDao.create(car);
    }

    public boolean deleteCar(Car car) {
        log.debug("delete car");
        return carDao.delete(car.getId());
    }

    @Override
    public boolean deleteCarById(int id) {
        log.debug("delete car by id: "+id);
        return carDao.delete(id);
    }

    public boolean updateCar(Car car) {
        log.info("update car");
        return carDao.update(car);
    }

    public Car getCarById(int id) {
        log.info("get car by id:"+id);
        return carDao.getById(id);
    }

    @Override
    public int countAllCars() {
        return carDao.countAllCars();
    }

    @Override
    public List<String> getBrands() {
        return carDao.getBrands();
    }

    @Override
    public List<Car> getOffset(int start, int limit) {
        return carDao.offSet(start, limit);
    }

    @Override
    public int countMatchBrand(String brand) {
        return carDao.countMatchBrand(brand);
    }

    @Override
    public int countMatchClass(String rate) {
        return carDao.countMatchClass(rate);
    }

    @Override
    public int countMatchBrandAndClass(String brand, String rate) {
        return carDao.countMatchBrandAndClass(brand, rate);
    }

    @Override
    public List<Car> sortByPriceASC(int start, int limit) {
        return carDao.sortByPriceASC(start, limit);
    }

    @Override
    public List<Car> sortByPriceDESC(int start, int limit) {
        return carDao.sortByPriceDESC(start, limit);
    }

    @Override
    public List<Car> sortByNameASC(int start, int limit) {
        return carDao.sortByNameASC(start, limit);
    }

    @Override
    public List<Car> sortByNameDESC(int start, int limit) {
        return carDao.sortByNameDESC(start, limit);
    }

    @Override
    public List<Car> sortByPriceMatchBrandASC(String brand, int start, int limit) {
        return carDao.sortByPriceMatchBrandASC(brand, start, limit);
    }

    @Override
    public List<Car> sortByPriceMatchBrandDESC(String brand, int start, int limit) {
        return carDao.sortByPriceMatchBrandDESC(brand, start, limit);
    }

    @Override
    public List<Car> sortByNameMatchBrandASC(String brand, int start, int limit) {
        return carDao.sortByNameMatchBrandASC(brand, start, limit);
    }

    @Override
    public List<Car> sortByNameMatchBrandDESC(String brand, int start, int limit) {
        return carDao.sortByNameMatchBrandDESC(brand, start, limit);
    }

    @Override
    public List<Car> sortByPriceMatchRateASC(String rate, int start, int limit) {
        return carDao.sortByPriceMatchRateASC(rate, start, limit);
    }

    @Override
    public List<Car> sortByPriceMatchRateDESC(String rate, int start, int limit) {
        return carDao.sortByPriceMatchRateDESC(rate, start, limit);
    }

    @Override
    public List<Car> sortByNameMatchRateASC(String rate, int start, int limit) {
        return carDao.sortByNameMatchRateASC(rate, start, limit);
    }

    @Override
    public List<Car> sortByNameMatchRateDESC(String rate, int start, int limit) {
        return carDao.sortByNameMatchRateDESC(rate, start, limit);
    }

    @Override
    public List<Car> sortByPriceMatchBrandAndClassASC(String brand, String auto_class, int start, int limit) {
        return carDao.sortByPriceMatchBrandAndClassASC(brand, auto_class, start, limit);
    }

    @Override
    public List<Car> sortByPriceMatchBrandAndClassDESC(String brand, String auto_class, int start, int limit) {
        return carDao.sortByPriceMatchBrandAndClassDESC(brand, auto_class, start, limit);
    }

    @Override
    public List<Car> sortByNameMatchBrandAndClassASC(String brand, String auto_class, int start, int limit) {
        return carDao.sortByNameMatchBrandAndClassASC(brand, auto_class, start, limit);
    }

    @Override
    public List<Car> sortByNameMatchBrandAndClassDESC(String brand, String auto_class, int start, int limit) {
        return carDao.sortByNameMatchBrandAndClassDESC(brand, auto_class, start, limit);
    }

    public List<Car> getAllCar() {
        return carDao.getAll();
    }
}
