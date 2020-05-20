package com.pack.common.pageobjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TravellingPage {
    private WebDriver driver;
    private final By showMyResultsButton=By.xpath("//button[contains(@name,'product-form-submit')]/link");
    public TravellingPage(WebDriver driver)
    {
        this.driver=driver;

    }
    public TravellingResultPage clickShowMyResults() {
        System.out.println("clicking on Show My Results button");
        WebDriverWait wait;
        wait=new WebDriverWait(driver,20);
        WebElement showMyResultsElement=wait.until(ExpectedConditions.visibilityOfElementLocated(showMyResultsButton));
        if(showMyResultsElement.isDisplayed()||showMyResultsElement.isEnabled())
            showMyResultsElement.click();
        else System.out.println("Element not found");
        return new TravellingResultPage(driver);
    }
}
