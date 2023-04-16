package org.example.Model;

import java.util.Date;

public class Order {
    private int id;
    private int idCar;
    private int idUser;
    private Date start_date;
    private Date end_date;
    private boolean withDriver;
    private int account;
    private int accountDamage;
    private String passportSerial;
    private Date passportExpireDate;
    private State state;
    private String message;

    public enum State {
        SEND, RETURN, WAIT, PAID,  DAMAGED, FINISH
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public boolean isWithDriver() {
        return withDriver;
    }

    public void setWithDriver(boolean withDriver) {
        this.withDriver = withDriver;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public int getAccountDamage() {
        return accountDamage;
    }

    public void setAccountDamage(int accountDamage) {
        this.accountDamage = accountDamage;
    }

    public String getPassportSerial() {
        return passportSerial;
    }

    public void setPassportSerial(String passportSerial) {
        this.passportSerial = passportSerial;
    }

    public Date getPassportExpireDate() {
        return passportExpireDate;
    }

    public void setPassportExpireDate(Date passportExpireDate) {
        this.passportExpireDate = passportExpireDate;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", idCar=" + idCar +
                ", idUser=" + idUser +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", withDriver=" + withDriver +
                ", account=" + account +
                ", accountDamage=" + accountDamage +
                ", passportSerial='" + passportSerial + '\'' +
                ", passportExpireDate=" + passportExpireDate +
                ", state=" + state +
                ", message='" + message + '\'' +
                '}';
    }
}
