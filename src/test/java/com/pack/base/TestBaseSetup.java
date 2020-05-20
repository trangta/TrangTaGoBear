package com.pack.base;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestBaseSetup {
    private WebDriver driver;
    static String driverPath = "src\\main\\resources\\driver\\";
    private String browserType;
    private String appURL;
    ATUTestRecorder recorder;
    public WebDriver getDriver() {
        return driver;
    }

    protected void setDriver(String browserType, String appURL) {
        switch (browserType) {
            case "chrome":
                driver = initChromeDriver(appURL);
                break;
            case "firefox":
                driver = initFirefoxDriver(appURL);
                break;
            default:
                System.out.println("browser : " + browserType
                        + " is invalid, Launching Firefox as browser of choice..");
                driver = initChromeDriver(appURL);
        }
    }

    private static WebDriver initChromeDriver(String appURL) {
        System.out.println("Launching google chrome with new profile..");
        System.setProperty("webdriver.chrome.driver", driverPath
                + "chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        return driver;
    }

    private static WebDriver initFirefoxDriver(String appURL) {
        System.out.println("Launching Firefox browser..");
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        return driver;
    }

    @Parameters({"browserType", "appURL"})
    @BeforeClass
    protected void initializeTestBaseSetup(String browserType, String appURL) throws ATUTestRecorderException {
        this.browserType = browserType;
        this.appURL = appURL;
        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
        Date date = new Date();
        //Created object of ATUTestRecorder
        //Provide path to store videos and file name format.
        recorder = new ATUTestRecorder("src\\main\\resources\\ScriptVideos\\","TestVideo-"+dateFormat.format(date),false);
        //To start video recording.
        recorder.start();
        try {
            setDriver(browserType, appURL);

        } catch (Exception e) {
            System.out.println("Error....." + e.getStackTrace());
        }
    }

    @AfterClass
    public void tearDown() {
       driver.quit();
        try {
            recorder.stop();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

