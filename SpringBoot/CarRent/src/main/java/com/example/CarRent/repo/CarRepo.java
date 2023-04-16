package com.example.CarRent.repo;

import com.example.CarRent.models.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepo extends JpaRepository<Car,Integer> {

    Page<Car> findAll(Pageable pageable);
    Page<Car> findByBrandAndAutoClass(Pageable pageable,@Param("brand") String brand,@Param("auto_class") Car.Rate rate);
    Page<Car> findByBrand(Pageable pageable,@Param("brand") String brand);
    Page<Car> findByAutoClass(Pageable pageable,@Param("auto_class") Car.Rate rate);

    /*Page<Car> find(Pageable pageable);*/
    @Query("SELECT DISTINCT brand FROM Car")
    List<String> getBrands();
}
