package com.pack.common.tests;

import com.pack.base.TestBaseSetup;
import com.pack.common.pageobjects.BasePage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
    public class BasePageTest extends TestBaseSetup {

        private WebDriver driver;

        @BeforeClass
        public void setUp() {
            driver= getDriver();
        }

        @Test
        public void verifyHomePage() {
            System.out.println("go bear page test...");
            BasePage basePage = new BasePage(driver);
            Assert.assertTrue(basePage.verifyBasePageTitle(), "go bear page title doesn't match");
        }

    }
