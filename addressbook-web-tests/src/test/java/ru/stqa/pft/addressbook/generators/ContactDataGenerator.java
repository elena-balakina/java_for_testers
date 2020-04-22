package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

    @Parameter(names = "-c", description = "Contact count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;

    @Parameter(names = "-d", description = "Data format")
    public String format;

    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException ex) {
            jCommander.usage();
            return;
        }

        generator.run();
    }

    private void run() throws IOException {
        List<ContactData> contact = generateContacts(count);

        if (format.equals("csv")) {
            saveAsCSV(contact, new File(file));
        } else if (format.equals("xml")) {
            saveAsXML(contact, new File(file));
        } else if (format.equals("json")) {
            saveAsJson(contact, new File(file));
        } else {
            System.out.println("Unrecognized format");
        }
    }

    private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        try (Writer writer = new FileWriter(file)) {
            writer.write(json);
        }
    }

    private void saveAsXML(List<ContactData> contacts, File file) throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(ContactData.class);

        String xml = xstream.toXML(contacts);
        try (Writer writer = new FileWriter(file)) {
            writer.write(xml);
        }
    }

    private void saveAsCSV(List<ContactData> contacts, File file) throws IOException {
        System.out.println(new File(".").getAbsolutePath());
        try (Writer writer = new FileWriter(file)) {
            for (ContactData contact : contacts) {
                writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n",
                        contact.getFirstName(), contact.getLastName(), contact.getAddress(),
                        contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone(),
                        contact.getEmail(), contact.getEmail2(), contact.getEmail3(), contact.getGroup()));
            }
        }
    }

    private static List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        //File photo = new File("src/test/resources/tiger.png");

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
                    withEmail3(String.format("mail%s@gmail.com", i)).
                    withGroup("test1"));
        }

        return contacts;
    }
}