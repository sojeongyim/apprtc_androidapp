package xyz.pulse9.sinabro;
import java.util.Calendar;


public class Message {
    public String sendDate;
    public String sender;
    public String receiver;
    public String contents;

    public Message(String sender, String contents)
    {
        this.sendDate = Calendar.getInstance().getTime().toString();
        this.receiver = "";
        this.sender = sender;
        this.contents = contents;
    }

    public Message(String sender, String receiver, String contents)
    {
        this.sendDate = Calendar.getInstance().getTime().toString();
        this.receiver = receiver;
        this.sender = sender;
        this.contents = contents;
    }

    public String getMessage() {
        return contents;
    }

    public String getSender()
    {
        return this.sender;
    }
    public String getReceiver()
    {
        return this.sender;
    }
}


