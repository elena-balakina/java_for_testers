package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.HTTPSession;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ResetPasswordTests extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testResetPassword() throws IOException, MessagingException {

        // 1. Администратор входит в систему
        app.loginHelper().login("administrator", "root");

        //Выбираем английский язык
        //app.loginHelper().switchLanguageToEnglish();

        // переходит на страницу управления пользователями
        app.resetPassword().openManageUsersPage();

        // выбирает заданного пользователя и нажимает кнопку Reset Password
        // взяли заранее созданного юзера user4 - password
        String user = "user4";
        String email = "";

        //получаем email из БД

        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bugtracker?user=root&password=&&serverTimezone=UTC");
            Statement st = conn.createStatement();
            // аналог коллекции - набор строк, которые извлекаются из таблицы
            // rs - текущая строка, курсор
            ResultSet rs = st.executeQuery("SELECT email FROM mantis_user_table WHERE username='" + user + "'");

            while (rs.next()) {
                email = rs.getString("email");
            }

            rs.close();
            st.close();
            conn.close();

            System.out.println("Email: " + email);

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }


        app.resetPassword().selectUser(user);
        app.resetPassword().resetPassword();

        //Отправляется письмо на адрес пользователя
        //тесты должны получить это письмо
        //извлечь из него ссылку для смены пароля
        //пройти по этой ссылке и изменить пароль
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = findConfirmationLink(mailMessages, email);

        String newPassword = "NEWpassword";
        app.registration().finish(confirmationLink, newPassword);


        // Затем тесты должны проверить, что пользователь может войти в систему с новым паролем
        app.loginHelper().login(user, newPassword);

        // проверяем, что юзер вошел в систему
        assertTrue(app.loginHelper().isLoggedInAs(user));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();

        // строим регулярное выражение:
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();

        // возвращает тот кусок текста, который соответствует построенному рег выражению
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
