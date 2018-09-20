package xyz.pulse9.sinabro;
import java.util.Calendar;


public class Message {

    private int type;

    private String sendDate;
    private String sender;
    private String contents;
    private String receiver;



    private String Caller;
    private String Date;
    private int chk;

    public int getChk(){return chk;}

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        receiver = receiver;
    }

    public Message(String sender, String receiver, String contents)
    {
        this.type=0;
        this.receiver = receiver;
        this.sender = sender;
        this.contents = contents;
        this.sendDate = Calendar.getInstance().getTime().toString();
    }
    public Message(int type, String Caller, String DateTime)
    {
        this.Caller=Caller;
        this.type=type;
        this.Date=DateTime;
        this.chk=0;
    }
    public Message(int type)
    {
        this.type=1;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getCaller() {
        return Caller;
    }

    public void setCaller(String caller) {
        Caller = caller;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setChk(int chk) {
        this.chk = chk;
    }
}