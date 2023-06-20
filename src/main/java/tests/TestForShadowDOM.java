package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestForShadowDOM {

    WebDriver mDriver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        mDriver = new ChromeDriver();
        mDriver.get("http://watir.com/examples/shadow_dom.html");
        mDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterMethod
    public void tearDown() {
        mDriver.quit();
    }

    @Test
    public void getShadowDomElementUsingGetShadowRoot() {
        System.out.println("text from getShadowDomElementUsingGetShadowRoot is " +
                mDriver.findElement(By.id("shadow_host"))
                        .getShadowRoot()
                        .findElement(By.className("info"))
                        .getText());

    }

    @Test
    public void getNestedShadowDomElementUsingGetShadowRoot() {
        System.out.println("text from getNestedShadowDomElementUsingGetShadowRoot is " +
                mDriver.findElement(By.id("shadow_host"))
                        .getShadowRoot()
                        .findElement(By.cssSelector("#nested_shadow_host"))
                        .getShadowRoot()
                        .findElement(By.cssSelector("#nested_shadow_content > div"))
                        .getText());
    }


    @Test
    public void getShadowDomElementUsingJSExecutor() {
        System.out.println("text from getShadowDomElementUsingJSExecutor is : " +
                expandRootElement(mDriver.findElement(By.id("shadow_host")), mDriver)
                        .findElement(By.className("info"))
                        .getText());
    }

    @Test
    public void getNestedShadowDomElementUsingJSExecutor() {
        SearchContext first = expandRootElement(mDriver.findElement(By.id("shadow_host")), mDriver);
        System.out.println("text from getNestedShadowDomElementUsingJSExecutor is : " +
                expandRootElement(first.findElement(By.cssSelector("#nested_shadow_host")), mDriver)
                .findElement(By.cssSelector("#nested_shadow_content > div")).getText());


    }


    public SearchContext expandRootElement(WebElement element, WebDriver driver) {
        return (SearchContext) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].shadowRoot", element);
    }


}
