package org.example.Model;

import java.util.Date;

public class Car {
    private int id;
    private String brand;
    private String model;
    private Date releaseDate;
    private State state;
    private Class autoClass;
    private int price;

    public enum State {
        AVAIL, INUSE, DAMAGED
    }
    public enum Class {
        A, B, C,
    }

   public Car(){}
    private Car(CarBuilder builder) {
        this.id = builder.id;
        this.brand = builder.brand;
        this.model = builder.model;
        this.releaseDate = builder.releaseDate;
        this.state = builder.state;
        this.autoClass = builder.autoClass;
        this.price = builder.price;
    }
    public static class CarBuilder {
        private int id;
        private String brand;
        private String model;
        private Date releaseDate;
        private State state;
        private Class autoClass;
        private int price;

        public CarBuilder id(int id) {
            this.id = id;
            return this;
        }
        public CarBuilder brand(String brand) {
            this.brand = brand;
            return this;
        }
        public CarBuilder model(String model) {
            this.model = model;
            return this;
        }
        public CarBuilder date(Date date) {
            this.releaseDate = date;
            return this;
        }
        public CarBuilder state(Car.State state) {
            this.state = state;
            return this;
        }
        public CarBuilder state(Car.Class autoClass) {
            this.autoClass = autoClass;
            return this;
        }
        public CarBuilder price(int  price) {
            this.price = price;
            return this;
        }
        public Car build(){
            Car car = new Car(this);
            return car;
        }
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Class getAutoClass() {
        return autoClass;
    }

    public void setAutoClass(Class autoClass) {
        this.autoClass = autoClass;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", releaseDate=" + releaseDate +
                ", state=" + state +
                ", autoClass=" + autoClass +
                ", price=" + price +
                '}';
    }
}
