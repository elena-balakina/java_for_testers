package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.MatchResult;

public class ApplicationManager {
    private final Properties properties;
    private String browser;
    private WebDriver driver; // webdrivers лежат в папке C:\Windows\System32
    private RegistrationHelper registrationHelper;
    private ResetPasswordHelper resetPasswordHelper;
    private LoginHelper loginHelper;
    private FTPHelper ftp;
    private MailHelper mailHelper;
    private JamesHelper jamesHelper;
    private SoapHelper soapHelper;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    }

    public void stop() {
        if (driver != null) {
            driver.quit();
        }
    }

    public HTTPSession newSession() {
        return new HTTPSession(this);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public RegistrationHelper registration() {
        if (registrationHelper == null) {
            registrationHelper = new RegistrationHelper(this);
        }
        return registrationHelper;
    }

    public ResetPasswordHelper resetPassword() {
        if (resetPasswordHelper == null) {
            resetPasswordHelper = new ResetPasswordHelper(this);
        }
        return resetPasswordHelper;
    }

    public LoginHelper loginHelper() {
        if (loginHelper == null) {
            loginHelper = new LoginHelper(this);
        }
        return loginHelper;
    }

    public FTPHelper ftp() {
        if (ftp == null) {
            ftp = new FTPHelper(this);
        }
        return ftp;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            if (browser.equals(BrowserType.CHROME)) {
                driver = new ChromeDriver();
            } else if (browser.equals(BrowserType.FIREFOX)) {
                driver = new FirefoxDriver();
            } else if (browser.equals(BrowserType.IE)) {
                driver = new InternetExplorerDriver();
            }

            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            driver.get(properties.getProperty("web.baseUrl"));       //driver.get("http://localhost/mantisbt-2.24.0/");
        }
        return driver;
    }

    public MailHelper mail() {
        if (mailHelper == null) {
            mailHelper = new MailHelper(this);
        }
        return mailHelper;
    }

    public JamesHelper james() {
        if (jamesHelper == null) {
            jamesHelper = new JamesHelper(this);
        }
        return jamesHelper;
    }

    public SoapHelper soap() {
        if (soapHelper == null) {
            soapHelper = new SoapHelper(this);
        }
        return soapHelper;
    }
}
