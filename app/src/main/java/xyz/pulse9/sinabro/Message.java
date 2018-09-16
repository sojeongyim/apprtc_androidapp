package xyz.pulse9.sinabro;
import java.util.Calendar;


public class Message {
    public String sendDate;
    public String sender;
    public String contents;

    public Message(String sender, String contents)
    {
        this.sender = sender;
        this.contents = contents;
        this.sendDate = Calendar.getInstance().getTime().toString();
    }

    public String getMessage() {
        return contents;
    }
    public String getSender() {
        return sender;
    }
}


