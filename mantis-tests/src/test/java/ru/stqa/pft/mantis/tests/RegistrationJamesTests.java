package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationJamesTests extends TestBase {

    //@BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testRegistration() throws IOException, MessagingException {
        long now = System.currentTimeMillis();

        String email = String.format("user%s@localhost.localdomain", now);
        String user = "user" + now;
        String password = "password";
        //String email = String.format("user%s@localhost", now);

        // создаем пользователя на почтовом сервере
        app.james().createUser(user, password);

        // регистрируем юзера с созданным емэйлом
        app.registration().start(user, email);

        // получаем письма из внешнего почтового сервера James
        List<MailMessage> mailMessages = app.james().waitForMail(user, password, 60000);

        String confirmationLink = findConfirmationLink(mailMessages, email);
        app.registration().finish(confirmationLink, user, password);

        // проверяем, что юзер вошел в систему
        assertTrue(app.newSession().login(user, password));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();

        // строим регулярное выражение:
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();

        // возвращает тот кусок текста, который соответствует построенному рег выражению
        return regex.getText(mailMessage.text);
    }

    //@AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
