package xyz.pulse9.sinabro;
import java.util.Calendar;


public class Message {
    public String sendDate;
    public String nickName;
    public String contents;

    public Message(String nickName, String contents)
    {
        this.sendDate = Calendar.getInstance().getTime().toString();
        this.nickName = nickName;
        this.contents = contents;
    }

    public String getMessage() {
        return contents;
    }
}


