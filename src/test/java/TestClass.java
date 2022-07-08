import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class TestClass {
    private static WebDriver driver;
    private static URL page;

    //////////////////////////////////////////////////////////////////////////////////////////////

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws MalformedURLException {
        page = new URL("https://demoqa.com/books");
        System.out.println("Starting Test On Firefox Browser");
        //firefoxSession();
        chromeSession();
    }

    @AfterClass
    public void close() {
        System.out.println("Finished Test On Firefox Browser");
        driver.close();
    }

    public void firefoxSession(){
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    public void chromeSession() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void titleNameTest(){
        //caso ejemplo
        driver.navigate().to(page);
        String testTitle = "ToolsQA";
        String originalTitle = driver.getTitle();
        Assert.assertEquals(originalTitle, testTitle);
        //https://www.toolsqa.com/selenium-webdriver/webelement-commands/
    }

    @Test
    public void Case1(){
        driver.navigate().to(page);
        //TODO
        //https://www.toolsqa.com/selenium-webdriver/webelement-commands/
    }

    @Test
    public void Case2(){
        driver.navigate().to(page);
        //TODO
        //https://www.toolsqa.com/selenium-webdriver/webelement-commands/
    }
}