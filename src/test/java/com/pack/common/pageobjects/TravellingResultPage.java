package com.pack.common.pageobjects;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

public class TravellingResultPage {
    private WebDriver driver;
    private  By cardNames= By.xpath("//div[contains(@class,'card-brand')]");
    private By sortLabel=By.id("headingTwo");
    private By priceDesOption=By.xpath("//label[contains(text(),'Price: High to low')]/parent::div");
    private By priceList=By.xpath("//div[@class='policy-price']/span");


    public TravellingResultPage(WebDriver driver)
    {
        this.driver=driver;

    }
    public void verifyAtLeast3Cards()
    {
        WebDriverWait wait=new WebDriverWait(driver,20);
        List<WebElement> listCard;
        listCard= wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cardNames));
        int minNumber=3;
        //verify at least 3 cards are being displayed
        Assert.assertTrue("At least 3 cards are being displayed",listCard.size()>=minNumber);

    }
    public void verifySortLabel()
    {
        WebElement lblSort=driver.findElement(sortLabel);
        Assert.assertEquals("Sort display","SORT",lblSort.getText());
    }
    public void verifySortPrice()
    {
        WebElement optPriceDes=driver.findElement(priceDesOption);
        Actions actions = new Actions(driver);
        actions.moveToElement(optPriceDes);
        actions.perform();
        optPriceDes.click();
        //verify sort price
        List<WebElement> listPrice;
        WebDriverWait wait=new WebDriverWait(driver,20);
        listPrice=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(priceList));
        verifySortDes(listPrice);
    }
    public void verifySortDes(List<WebElement> listPrice)
    {
        try {
            double previousPrice = DecimalFormat.getNumberInstance().parse(listPrice.get(0).getText()).doubleValue();
            for (int i = 1; i < listPrice.size() - 1; i++) {

                Assert.assertFalse("not sort", DecimalFormat.getNumberInstance().parse(listPrice.get(0).getText()).doubleValue() > previousPrice);
                break;
            }
        }catch (ParseException e){
            e.printStackTrace();
        };
    }

}
