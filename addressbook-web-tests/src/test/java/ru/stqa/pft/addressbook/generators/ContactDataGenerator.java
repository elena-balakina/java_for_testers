package ru.stqa.pft.addressbook.generators;

import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
    public static void main(String[] args) throws IOException {
        int count = Integer.parseInt(args[0]);
        File file = new File(args[1]);

        List<ContactData> contact = generateContacts(count);
        saveAsCSV(contact, file);
    }

    private static void saveAsCSV(List<ContactData> contacts, File file) throws IOException {
        System.out.println(new File(".").getAbsolutePath());
        Writer writer = new FileWriter(file);

        for (ContactData contact : contacts) {
            writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s\n",
                    contact.getFirstName(), contact.getLastName(), contact.getAddress(),
                    contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone(),
                    contact.getEmail(), contact.getEmail2(), contact.getEmail3()));
        }

        writer.close();
    }

    private static List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();

        for (int i = 0; i < count; i++) {
            contacts.add(new ContactData().
                    withFirstName(String.format("First name %s", i)).
                    withLastName(String.format("Last name %s", i)).
                    withAddress(String.format("Address %s", i)).
                    withHomePhone(String.format("%s%s%s-%s%s-%s%s", i, i, i, i, i, i, i)).
                    withMobilePhone(String.format("+7913111112%s", i)).
                    withWorkPhone(String.format("%s%s%s-%s%s-%s%s", i, i, i, i, i, i, i)).
                    withEmail(String.format("mail%s@gmail.com", i)).
                    withEmail2(String.format("mail%s@gmail.com", i)).
                    withEmail3(String.format("mail%s@gmail.com", i)));
        }

        return contacts;
    }
}
