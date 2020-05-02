package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

import java.io.File;

public class TestBase {

    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

    //System.getProperty("browser")
    //BrowserType.CHROME
    //BrowserType.IE
    //BrowserType.FIREFOX

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
        //app.ftp().upload(new File("src/test/resources/config_inc.php"),
        //"config_inc.php", "config_inc.php.bak");

        app.ftp().upload(new File("src/test/resources/config_inc.php"),
                "config/config_inc.php", "config/config_inc.php.bak");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        //app.ftp().restore("config_inc.php.bak","config_inc.php");
        app.ftp().restore("config/config_inc.php.bak", "config/config_inc.php");
        app.stop();
    }


}

// $g_crypto_master_salt     = 'UEYla15Z1dYa1H0K7Ymgx7S8RuMHdhJ8sWrO6UMvngw=';
