package xyz.pulse9.sinabro;
import java.util.Calendar;


public class Message {
    public String sendDate;
    public String personA;
    public String personB;
    public String contents;

    public Message(String sender, String contents)
    {
        this.sendDate = Calendar.getInstance().getTime().toString();
        this.personA = sender;
        this.personB = "";
        this.contents = contents;
    }

    public Message(String sender, String receiver, String contents)
    {
        this.sendDate = Calendar.getInstance().getTime().toString();
        this.personA = receiver;
        this.personB = sender;
        this.contents = contents;
    }

    public String getMessage() {
        return contents;
    }

    public String getpersonA()
    {
        return this.personA;
    }
    public String getpersonB()
    {
        return this.personB;
    }
}


