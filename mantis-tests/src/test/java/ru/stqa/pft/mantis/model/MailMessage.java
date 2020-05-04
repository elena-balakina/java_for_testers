package ru.stqa.pft.mantis.model;

public class MailMessage {

    public String to; // кому пришло письмо
    public String text; // текст этого письма

    public MailMessage(String to, String text) {
        this.to = to;
        this.text = text;
    }
}
