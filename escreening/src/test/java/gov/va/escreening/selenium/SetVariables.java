package gov.va.escreening.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class SetVariables extends SeleniumTest {

    @Test
    public void testSetVariables() throws Exception {
        String testInstanceUrl = "http://54.235.74.13/escreening-int";
        String date = "08/26/2015";
    }

}
