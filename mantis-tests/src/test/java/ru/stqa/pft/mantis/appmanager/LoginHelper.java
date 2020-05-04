package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

import java.io.IOException;

public class LoginHelper extends HelperBase {
    public LoginHelper(ApplicationManager app) {
        super(app);
    }

    public void login(String username, String password) {
        driver.get(app.getProperty("web.baseUrl") + "/login_page.php");
        type(By.name("username"), username);
        click(By.cssSelector("input[type='submit']"));
        type(By.name("password"), password);
        click(By.cssSelector("input[type='submit']"));
    }

    public void switchLanguageToEnglish() {
        click(By.cssSelector("span.user-info"));
        click(By.linkText("My Account"));
        click(By.linkText("Preferences"));
        selectByVisibleText(By.id("language"), "english");
    }

    public boolean isLoggedInAs(String username) throws IOException {
        return driver.getPageSource().contains
                (String.format("<a href=\"/mantisbt-2.24.0/account_page.php\">%s ( %s ) </a>", username, username));
    }


}
