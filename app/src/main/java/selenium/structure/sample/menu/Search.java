package selenium.structure.sample.menu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.Assert;
import org.testng.Reporter;

public class Search {
    WebDriver driver;
    @BeforeTest
    public void BeforeTest(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.notion.so/ko-kr");
    }

    @Test(priority = 1)
    public void LoadCookies() {
        try {
            File dataFile = new File("./browser.data");
            FileReader fr = new FileReader(dataFile);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine())!= null){
                StringTokenizer str = new StringTokenizer(line,";");
                while(str.hasMoreTokens()){
                    String name = str.nextToken();
                    String value = str.nextToken();
                    String domain = str.nextToken();
                    String path = str.nextToken();
                    Date expiry = null;
                    String dt;
                    if(!(dt = str.nextToken()).equals("null")){
                        //Mon Oct 09 20:25:03 KST 2023
                        System.out.println("dt:"+dt);
                        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
                        expiry = formatter.parse(dt);
                    }
                    boolean isSecure = new Boolean(str.nextToken()).booleanValue();
                    Cookie ck = new Cookie(name,value,domain,path,expiry,isSecure);
                    driver.manage().addCookie(ck);
                }
            }
            br.close();
            fr.close();
            driver.get("https://www.notion.so/login");
            Reporter.log("load cookies success");
        } catch (Exception e) {
            Assert.fail(e.getMessage());
            System.out.println(e.toString());
        }
    }

    @AfterTest
    public void AfterTest(){
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Reporter.log(e.getMessage());
        }
        driver.quit();
        Reporter.log("login All Clear");
    }
}
