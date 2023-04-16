package org.example.Service;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorTest {

    @Test
    public void validatePhone() {
        assertEquals(false, Validator.validatePhone("1234"));
        assertEquals(true, Validator.validatePhone("+1234456"));
    }

    @Test
    public void validateEmail() {
        assertEquals(false, Validator.validateEmail("fdaf@"));
        assertEquals(true, Validator.validateEmail("mail@com"));
    }

    @Test
    public void drivingLicence() {
        assertEquals(false, Validator.drivingLicence("aa1234"));
        assertEquals(false, Validator.drivingLicence("AB12345"));
        assertEquals(true, Validator.drivingLicence("AA4442"));
    }
}