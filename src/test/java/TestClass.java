import net.bytebuddy.asm.Advice;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TestClass {
    private static WebDriver driver;
    private static URL page;

    //////////////////////////////////////////////////////////////////////////////////////////////

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws MalformedURLException {
        page = new URL("https://demoqa.com/books");
        System.out.println("Starting Test On Firefox Browser");
        firefoxSession();
        //chromeSession();
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
        //caso practica basica
        driver.navigate().to(page);
        String testTitle = "ToolsQA";
        String originalTitle = driver.getTitle();
        Assert.assertEquals(originalTitle, testTitle);
    }

    @Test (priority=1)
    public void Case1(){
        driver.navigate().to(page);
        WebElement element = driver.findElement(By.id("searchBox"));
        element.sendKeys("Learning JavaScript Design Patterns");
        Duration time = Duration.ofMillis(8000);
        WebDriverWait wait = new WebDriverWait(driver, time);
        WebElement lib = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Learning JavaScript Design Patterns")));
        lib.click();
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        List<WebElement> webList = driver.findElements(By.id("userName-value"));

        String iSBNExpected = "9781449331818";
        String titleExpected = "Learning JavaScript Design Patterns";
        String subTitleExpected = "A JavaScript and jQuery Developer's Guide";
        String AuthorExpected = "Addy Osmani";
        String PublisherExpected = "O'Reilly Media";
        String TotalPagesExpected = "254";
        String DescriptionExpected = "With Learning JavaScript Design Patterns, you'll learn how to write beautiful, structured, and maintainable JavaScript by applying classical and modern design patterns to the language. If you want to keep your code efficient, more manageable, and up-to-da";

        Assert.assertEquals(iSBNExpected, webList.get(0).getText());
        Assert.assertEquals(titleExpected, webList.get(1).getText());
        Assert.assertEquals(subTitleExpected, webList.get(2).getText());
        Assert.assertEquals(AuthorExpected, webList.get(3).getText());
        Assert.assertEquals(PublisherExpected, webList.get(4).getText());
        Assert.assertEquals(TotalPagesExpected, webList.get(5).getText());
        Assert.assertEquals(DescriptionExpected, webList.get(6).getText());
    }

    @Test (priority=2)

    public void Case2(){

        driver.navigate().to(page);
        Duration time = Duration.ofMillis(8000);
        WebDriverWait wait = new WebDriverWait(driver, time);
        //////////////////////////////////////////////////////////////
        //Login
        WebElement element = driver.findElement(By.id("login"));
        element.click();

        ////////////////////////////////////////////////////////////
        //Registrar Usuario
        WebElement newUser = driver.findElement(By.id("newUser"));
        newUser.click();

        WebElement firstNameForm = driver.findElement(By.id("firstname"));
        WebElement lastNameForm = driver.findElement(By.id("lastname"));
        WebElement userNameForm = driver.findElement(By.id("userName"));
        WebElement passForm = driver.findElement(By.id("password"));


        ///////////////////////////////////////////////////////////////////////////////////////////////
        //Se le Agregan numeros segun el momento cuando se corra el test (evitar usuarios repetidos)
        DateTimeFormatter FOMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String id = FOMATTER.format(localDateTime);

        firstNameForm.sendKeys("Usuario"+id);
        lastNameForm.sendKeys("Test");
        userNameForm.sendKeys("User"+id);
        passForm.sendKeys("Pass1234");

        Duration time2 = Duration.ofMillis(8000);
        WebDriverWait wait2 = new WebDriverWait(driver, time);
        new WebDriverWait(driver, time).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[starts-with(@name, 'a-') and starts-with(@src, 'https://www.google.com/recaptcha')]")));
        new WebDriverWait(driver, time).until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.rc-anchor-content"))).click();
        driver.switchTo().defaultContent();

        WebElement register =driver.findElement(By.id("register"));
        register.click();

    }

    @Test (priority=3)
    public void loginFailed(){
        driver.navigate().to(page);
        Duration time = Duration.ofMillis(8000);
        WebDriverWait wait = new WebDriverWait(driver, time);

        //////////////////////////////////////////////////////////////
        //Login
        WebElement element = driver.findElement(By.id("login"));
        element.click();

        //////////////////////////////////////////////////////////////
        //Imputs
        WebElement userName = driver.findElement(By.id("userName"));
        WebElement pass = driver.findElement(By.id("password"));

        userName.sendKeys("UsuarioTest");
        pass.sendKeys("contraseña1234");
        element = driver.findElement(By.id("login"));
        element.click();

        /////////////////////////////////////////////////////////////
        //Mensaje de error si el usuario llegara a tener acceso
        try {
            WebElement error = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        }catch (Exception e){
            System.out.println("El Usuario No deberia Tener Acceso");
        }
    }
}