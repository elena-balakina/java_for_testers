package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class ResetPasswordHelper extends HelperBase {

    public ResetPasswordHelper(ApplicationManager app) {
        super(app);
    }

    public void openManageUsersPage() {
        click(By.xpath("//div[@id='sidebar']/ul/li[6]/a"));
        click(By.xpath("//a[contains(@href, '/mantisbt-2.24.0/manage_user_page.php')]"));
    }

    public void selectUser(String username) {
        click(By.linkText(username));
    }

    public void resetPassword() {
        click(By.cssSelector("input[value='Reset Password']"));
    }

    public void finish(String confirmationLink, String password) {
        driver.get(confirmationLink);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.cssSelector("button[type='submit']"));
    }
}
