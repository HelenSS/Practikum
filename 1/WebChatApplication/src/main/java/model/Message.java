package model;

import java.util.Date;

public class Message {
    private int id;
    private String user;
    private String text;
    private Date date;

    public Message (int id, String user, String text, Date date) {
        System.out.println(id + " " + user + " " + text + " " + date);
        this.id = id;
        this.user = user;
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return  id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user_id) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText (String text) {
        this.text = text;
    }

    public Date getDate () { return date; }

    public void setDate (Date date) { this.date = date; }

    public String toString() {
        return "{\"id\":\"" + this.id + "\",\"user\":\"" + this.user + "\",\"text\":\"" +
                this.text + "\",\"date\":\"" + this.date + "\"}";
    }
}
