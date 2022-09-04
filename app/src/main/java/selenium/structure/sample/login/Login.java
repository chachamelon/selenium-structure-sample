package selenium.structure.sample.login;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
public class Login {
    WebDriver driver;

    @FindBy(xpath = "//*[@id=\"account\"]/a")
    WebElement LoginBtn;

    @BeforeTest
    public void BeforeTest(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test(priority = 1)
    public void AccessNaverMain(){
        driver.get("https://www.naver.com");
        Reporter.log("access naver success.");
    }

    @Test(priority = 2)
    public void AccessNaverLogin(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //driver.findElement(By.xpath("//*[@id=\"account\"]/a")).click();
        PageFactory.initElements(driver, this);
        LoginBtn.click();
        Reporter.log("Click Login Button.");
    }

    @AfterTest
    public void AfterTest(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        driver.quit();
        Reporter.log("driver quit");
    }
}
