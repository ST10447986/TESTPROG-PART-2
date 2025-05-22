package com.mycompany.st10447986_prog5121_poe;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {
    private Login login;

    @Before
    public void setUp() {
        login = new Login();
    }

    // Username tests
    @Test
    public void testCorrectUsernameFormat() {
        login.setUsername("kyl_1");
        assertTrue(login.checkUserName());
    }

    @Test
    public void testIncorrectUsernameFormat() {
        login.setUsername("kyle!!!!!!");
        assertFalse(login.checkUserName());
    }

    // Password tests
    @Test
    public void testPasswordMeetsComplexity() {
        login.setPassword("Ch&&sec@ke99!");
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testPasswordFailsComplexity() {
        login.setPassword("password");
        assertFalse(login.checkPasswordComplexity());
    }

    // Cell phone tests
    @Test
    public void testCorrectCellNumberFormat() {
        login.setCellNumber("+27835965976");
        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    public void testIncorrectCellNumberFormat() {
        login.setCellNumber("08966553");
        assertFalse(login.checkCellPhoneNumber());
    }

    // Login behavior tests
    @Test
    public void testLoginSuccess() {
        login.setUsername("kyl_1");
        login.setPassword("Ch&&sec@ke99!");
        login.setCellNumber("+27835965976");
        login.setName("Kyle");
        login.setSurname("Smith");
        login.registerUser(); // This sets loginStatus = true
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailure() {
        login.setUsername("kyl_1");
        login.setPassword("Ch&&sec@ke99!");
        login.setCellNumber("+27835965976");
        login.registerUser(); // loginStatus = true

        assertFalse(login.loginUser("wronguser", "wrongpass"));
    }
}