package selenium.structure.sample.login;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Reporter;
public class Login {
    WebDriver driver;
    // 로그인 화면 이동 버튼
    @FindBy(xpath = "//*[@id=\"__next\"]/div/header/div/div[2]/nav/div[2]/ul[2]/li[1]/a")
    WebElement moveLoginPageBtn;

    // 로그인 화면 노출 객체들
    @FindBy(xpath = "//*[@id=\"notion-email-input-1\"]")
    WebElement userID;

    @FindBy(xpath = "//*[@id=\"notion-app\"]/div/div[1]/main/section/div/div/div/div[2]/div[1]/div[3]/form/div[3]")
    WebElement keepEmailBtn;

    @FindBy(xpath = "//*[@id=\"notion-password-input-2\"]")
    WebElement userPW;

    @FindBy(xpath = "//*[@id=\"notion-app\"]/div/div[1]/main/section/div/div/div/div[2]/div[1]/div[3]/form/div[3]")
    WebElement loginBtn;

    @BeforeTest
    public void BeforeTest(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test(priority = 1)
    public void AccessNotionMain(){
        driver.get("https://www.notion.so/ko-kr");
        Reporter.log("access notion success.");
    }

    @Test(priority = 2)
    public void AccessNotionLogin(){
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
        moveLoginPageBtn.click();
        Reporter.log("move Login Page.");
    }

    @Test(priority = 3)
    public void InsertLoginInfomation(){
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
        //TODO - 자신의 노션 이메일을 입력(구글, 애플 계정이 아닌 이메일)
        userID.sendKeys("my_email@naver.com");
        keepEmailBtn.click();
        Reporter.log("Click keep Email Button.");
    }

    @Test(priority = 4)
    public void InsertPasswordInformation(){
        //TODO - 노션 계정의 비밀번호 입력
        userPW.sendKeys("my_password");
        loginBtn.click();
        Reporter.log("login Btn click");
    }

    @Test(priority = 5)
    public void StoreCookies(){
        File dataFile = new File("./browser.data");
        try{
            Thread.sleep(1000);
            dataFile.delete();
            dataFile.createNewFile();
            FileWriter fos = new FileWriter(dataFile);
            BufferedWriter bos = new BufferedWriter(fos);
            for (Cookie ck : driver.manage().getCookies()){
                String name = ck.getName();
                if(name.equals(null) || name.equals("")){
                    name = "null";
                }
                String value = ck.getValue();
                if(value.equals(null) || value.equals("")){
                    value = "null";
                }
                String domain = ck.getDomain();
                if(domain.equals(null) || domain.equals("")){
                    domain = "null";
                }
                String path = ck.getPath();
                if(path.equals(null) || path.equals("")){
                    path = "null";
                }
                bos.write((name+";"+value+";"+domain+";"+path+";"+ck.getExpiry()+";"+ck.isSecure()));
                bos.newLine();
            }
            bos.flush();
            bos.close();
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
            Reporter.log(e.toString());
        }
    }

    @AfterTest
    public void AfterTest(){
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            Reporter.log(e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        driver.quit();
        Reporter.log("login All Clear");
    }
}
