package com.pack.common.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    protected WebDriver driver;
    private final By insuranceTab = By.xpath("//li[contains(@data-gb-name,'Insurance')]");
    public BasePage(WebDriver driver) {
        this.driver = driver;

    }

    public TravellingPage clickInsuranceTab() {
        System.out.println("clicking on Insurance tab");
        WebDriverWait wait;
        wait=new WebDriverWait(driver,20);
        WebElement insuranceTabElement=wait.until(ExpectedConditions.visibilityOfElementLocated(insuranceTab));
        if(insuranceTabElement.isDisplayed()||insuranceTabElement.isEnabled())
            insuranceTabElement.click();
        else System.out.println("Element not found");
        return new TravellingPage(driver);
    }


    public String getPageTitle(){
        String title = driver.getTitle();
        return title;
    }

    public boolean verifyBasePageTitle() {
        String expectedPageTitle="Compare the best insurance, credit cards, or loans in the Philippines | GoBear";
        return getPageTitle().contains(expectedPageTitle);
    }
}
