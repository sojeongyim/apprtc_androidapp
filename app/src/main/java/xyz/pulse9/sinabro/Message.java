package xyz.pulse9.sinabro;
import java.util.Calendar;


public class Message {

    private String type;
    private String chk;
    private String photo;
    private String sendDate;
    private String sender;
    private String contents;
    private String receiver;
    private String date;

    public Message(String type, String sender, String receiver, String sendDate)
    {
        this.type=type;
        this.sender=sender;
        this.receiver=receiver;
        this.sendDate=sendDate;
    }

    public Message(String type, String sender, String receiver)
    {
        this.type=type;
        this.sender=sender;
        this.receiver=receiver;
        this.sendDate = Calendar.getInstance().getTime().toString();
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChk() {
        return chk;
    }

    public void setChk(String chk) {
        this.chk = chk;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

