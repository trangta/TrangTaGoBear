package com.pack.base;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileBrowserType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestBaseSetup {
    private WebDriver driver;
    static String driverPath = "src\\main\\resources\\driver\\";
    private String browserType;
    private String appURL;
    ATUTestRecorder recorder;
    private AndroidDriver androidDriver;
    public WebDriver getDriver() {
        return driver;
    }
    public AndroidDriver getAndroidDriver() {
        return androidDriver;
    }

    protected void setDriver(String browserType, String appURL) {
        switch (browserType) {
            case "chrome":
                driver = initChromeDriver(appURL);
                break;
            case "firefox":
                driver = initFirefoxDriver(appURL);
                break;
            case "android":
                try {
                    androidDriver = initAndroid(appURL);
                }catch (MalformedURLException  e)
                {
                    System.out.println ("error");
                }
                break;
            default:
                System.out.println("browser : " + browserType
                        + " is invalid, Launching Firefox as browser of choice..");
                driver = initChromeDriver(appURL);
        }
    }
    public AndroidDriver initAndroid(String appURL) throws MalformedURLException {
        String ACCESS_KEY = System.getenv("SEETEST_IO_ACCESS_KEY");
        String CLOUD_URL = "https://cloud.seetest.io:443/wd/hub";
        String TITLE = "Testing Website on Android Chrome with Java";
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability("testName", TITLE);
        dc.setCapability("accessKey", ACCESS_KEY);
        dc.setBrowserName(MobileBrowserType.CHROME);
        androidDriver = new AndroidDriver(new URL(CLOUD_URL), dc);
        androidDriver.get(appURL);
        return androidDriver;
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

