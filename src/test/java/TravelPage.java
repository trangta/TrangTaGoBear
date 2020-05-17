import atu.testrecorder.exceptions.ATUTestRecorderException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import atu.testrecorder.ATUTestRecorder;
public class TravelPage {
    WebDriver driver;
    ATUTestRecorder recorder;

    @Before
    public void setup() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
        Date date = new Date();
        //Created object of ATUTestRecorder
        //Provide path to store videos and file name format.
        recorder = new ATUTestRecorder("src\\main\\resources\\ScriptVideos\\","TestVideo-"+dateFormat.format(date),false);
        //To start video recording.
        recorder.start();
        System.setProperty("webdriver.chrome.driver","src\\main\\resources\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //go to URL
        driver.get("https://www.gobear.com/ph?x_session_type=UAT");
    }
    @Test
    public void verifyTravel()
    {
        WebElement btnShowMyResults;
        WebElement tabInsurance;
        WebDriverWait wait=new WebDriverWait(driver,20);
        tabInsurance=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(@data-gb-name,'Insurance')]")));
        // Go to Travel section
        tabInsurance.click();
        btnShowMyResults=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@name,'product-form-submit')]/link")));
        // Go to the Travel results page
        btnShowMyResults.click();

        List<WebElement> listCard;
        listCard= wait.until((ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'card-brand')]"))));

        int minNumber=3;
        //verify at least 3 cards are being displayed
        Assert.assertTrue("At least 3 cards are being displayed",listCard.size()>=minNumber);
        //verify sort function
        WebElement lblSort=driver.findElement(By.id("headingTwo"));
        Assert.assertEquals("Sort display","SORT",lblSort.getText());

        WebElement optPriceDes=driver.findElement(By.xpath("//label[contains(text(),'Price: High to low')]/parent::div"));
        Actions actions = new Actions(driver);
        actions.moveToElement(optPriceDes);
        actions.perform();
        optPriceDes.click();
        //verify sort price
        List<WebElement> listPrice;
        listPrice=wait.until((ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='policy-price']/span"))));
        verifySort(listPrice);

    }


    public void verifySort(List<WebElement> listPrice)
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


    @After
    public void tearDown()
    {
        //To stop video recording.

        driver.quit();
        try {
            recorder.stop();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
