package com.pack.common.tests;

import com.pack.base.TestBaseSetup;
import com.pack.common.pageobjects.BasePage;
import com.pack.common.pageobjects.TravellingPage;
import com.pack.common.pageobjects.TravellingResultPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TravellingPageTest extends TestBaseSetup {
    private WebDriver driver;
    private TravellingPage travellingPage;
    private BasePage basePage;
    private TravellingResultPage travellingResultPage;
    @BeforeClass
    public void setUp() {
        driver=getDriver();
    }
    @Test
    public void verifyInsuranceFunction() {
        System.out.println("Insurance functionality details...");
        BasePage basePage = new BasePage(driver);
        travellingPage=basePage.clickInsuranceTab();
        travellingResultPage=travellingPage.clickShowMyResults();
        travellingResultPage.verifyAtLeast3Cards();
        travellingResultPage.verifySortLabel();
        travellingResultPage.verifySortPrice();
    }

}
