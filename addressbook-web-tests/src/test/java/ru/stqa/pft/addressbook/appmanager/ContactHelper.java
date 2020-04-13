package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver driver) {
        super(driver);
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("email"), contactData.getEmail());

        if (creation) {
            new Select(driver.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void submitContactCreation() {
        click(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void initContactModification(int index) {
        driver.findElements(By.xpath("//td[8]/a/img")).get(index).click();
    }

    public void initContactModificationById(int id) {
        driver.findElement(By.xpath("//a[@href=\"edit.php?id=" + id + "\"]")).click();
        //driver.findElement(By.xpath(String.format("//a[@href=\"edit.php?id=%s\"]", id))).click();
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void selectContactById(int id) {
        driver.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void deleteSelectedContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void create(ContactData contact, boolean creation) {
        click(By.linkText("add new"));
        fillContactForm(contact, creation);
        submitContactCreation();
        click(By.linkText("home"));
    }

    public void modify(ContactData contact) {
        initContactModificationById(contact.getId());
        fillContactForm(contact, false);
        submitContactModification();
        click(By.linkText("home"));
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContact();
        driver.switchTo().alert().accept();
        click(By.linkText("home"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getContactCount() {
        return driver.findElements(By.name("selected[]")).size();
    }

    public Contacts all() {
        Contacts contacts = new Contacts();
        List<WebElement> elements = driver.findElements(By.cssSelector("[name=\"entry\"]"));

        for (WebElement element : elements) {
            String lastName = element.findElement(By.xpath("td[2]")).getText();
            String firstName = element.findElement(By.xpath("td[3]")).getText();
            String address = element.findElement(By.xpath("td[4]")).getText();
            String email = element.findElement(By.xpath("td[5]")).getText();
            //String mobilePhone = element.findElement(By.xpath("td[6]")).getText();
            String[] phones = element.findElement(By.xpath("td[6]")).getText().split("\n");

            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));

            ContactData contact = new ContactData().withId(id).withFirstName(firstName).withLastName(lastName).
                    withAddress(address).withHomePhone(phones[0]).withMobilePhone(phones[1]).
                    withWorkPhone(phones[2]).withEmail(email);
            contacts.add(contact);
        }
        return contacts;
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String lastName = driver.findElement(By.name("lastname")).getAttribute("value");
        String firstName = driver.findElement(By.name("firstname")).getAttribute("value");
        String home = driver.findElement(By.name("home")).getAttribute("value");
        String mobile = driver.findElement(By.name("mobile")).getAttribute("value");
        String work = driver.findElement(By.name("work")).getAttribute("value");

        driver.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstName(firstName).withLastName(lastName).
                withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
    }
}
