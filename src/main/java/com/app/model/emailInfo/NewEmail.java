package com.app.model.emailInfo;

public class NewEmail {

    private String type;

    private String text;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    private String to;

    private String from;

    public NewEmail() {
    }

    public NewEmail(String type, String text, String to, String from) {
        this.type = type;
        this.text = text;
        this.to = to;
        this.from = from;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
