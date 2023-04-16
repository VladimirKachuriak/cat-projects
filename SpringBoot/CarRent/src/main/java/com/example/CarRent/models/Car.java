package com.example.CarRent.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "car")
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "{label.empty}")
    @Size(min = 2,max = 20,message = "{incorrect.size.brand}")
    private String brand;
    private String model;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;
    @Enumerated(EnumType.STRING)
    private State state;
    @Enumerated(EnumType.STRING)
    private Rate autoClass;
    private int price;
    @OneToMany(mappedBy = "car",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Orders> orders;

    public enum State {
        AVAIL, INUSE, DAMAGED
    }
    public enum Rate {
        A, B, C,
    }
}
