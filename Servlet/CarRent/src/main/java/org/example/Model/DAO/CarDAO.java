package org.example.Model.DAO;

import org.example.Model.Car;

import java.util.List;

public interface CarDAO extends Entity<Car>{
    int countAllCars();
    List<String> getBrands();
    List<Car> offSet(int start, int limit);
    int countMatchBrand(String brand);
    int countMatchClass(String rate);
    int countMatchBrandAndClass(String brand,String rate);

    List<Car> sortByPriceASC(int start, int limit);
    List<Car> sortByPriceDESC(int start, int limit);
    List<Car> sortByNameASC(int start, int limit);
    List<Car> sortByNameDESC(int start, int limit);

    List<Car> sortByPriceMatchBrandASC(String brand,int start, int limit);
    List<Car> sortByPriceMatchBrandDESC(String brand,int start, int limit);
    List<Car> sortByNameMatchBrandASC(String brand,int start, int limit);
    List<Car> sortByNameMatchBrandDESC(String brand,int start, int limit);

    List<Car> sortByPriceMatchRateASC(String rate,int start, int limit);
    List<Car> sortByPriceMatchRateDESC(String rate,int start, int limit);
    List<Car> sortByNameMatchRateASC(String rate,int start, int limit);
    List<Car> sortByNameMatchRateDESC(String rate,int start, int limit);

    List<Car> sortByPriceMatchBrandAndClassASC(String brand,String auto_class,int start, int limit);
    List<Car> sortByPriceMatchBrandAndClassDESC(String brand,String auto_class,int start, int limit);
    List<Car> sortByNameMatchBrandAndClassASC(String brand,String auto_class,int start, int limit);
    List<Car> sortByNameMatchBrandAndClassDESC(String brand,String auto_class,int start, int limit);
}
