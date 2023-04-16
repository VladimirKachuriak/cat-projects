package org.example.Service;

public class Validator {
    public static boolean validatePhone(String phone){
        return phone.matches("^\\+\\d{6,10}$");
    }
    public static boolean validateEmail(String email){
        return email.matches("^[a-zA-Z0-9]+@[a-zA-Z0-9.-]+$");
    }

    public static boolean drivingLicence(String licence){
        return licence.matches("^[A-Z]{2}[1-9]{4}$");
    }
}
