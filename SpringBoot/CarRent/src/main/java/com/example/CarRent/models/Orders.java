package com.example.CarRent.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;
    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_date;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end_date;
    private boolean withDriver;
    private int account;
    private int accountDamage;
    @Pattern(regexp = "^[A-Z]{2}[1-9]{4}$", message = "{incorrect.passport}")
    private String passportSerial;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date passportExpireDate;
    @Enumerated(EnumType.STRING)
    private State state;
    private String message;

    public enum State {
        SEND, RETURN, WAIT, PAID, DAMAGED, FINISH
    }
}
