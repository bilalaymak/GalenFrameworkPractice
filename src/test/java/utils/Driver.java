package utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
public class Driver {
    private Driver() {
    }

    private static ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();
    public static WebDriver getDriver() {

        ChromeOptions chromeOptions = new ChromeOptions();
        EdgeOptions edgeOptions = new EdgeOptions();
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        if (driverPool.get() == null) {
            synchronized (Driver.class) {
                String browserType = System.getProperty("browser") == null ? ConfigReader.getProperty("browser") : System.getProperty("browser");
                switch (browserType) {

                    case "chrome":
                        driverPool.set(new ChromeDriver(chromeOptions));

                        break;
                    case "firefox":
                        driverPool.set(new FirefoxDriver(firefoxOptions));

                        break;
                    case "edge":
                        driverPool.set(new EdgeDriver(edgeOptions));

                        break;

                    case "safari":
                        driverPool.set(new SafariDriver());
                        break;

                    case "chrome-headless":
                        chromeOptions.addArguments("--headless=new");
                        driverPool.set(new ChromeDriver(chromeOptions));
                        break;
                }
            }
        }
//        driverPool.get().manage().window().maximize();
//        driverPool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driverPool.get();
    }

    public static void closeDriver() {
        if (driverPool.get() != null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }
}
