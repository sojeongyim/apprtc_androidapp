package xyz.pulse9.sinabro;
import java.util.Calendar;

//General Message
//1. Type
//2. Sender
//3. Receiver
//4. SenderPhoto
//5. SendDate
//6. Contents
//
//Date Message
//1. Type
//2. TodayDate
//
//Offer Plan Message
//1. Type
//2. Chk
//3. Sender
//4. SenderPhoto
//5. Receiver
//6. Contents = "Planning Confference"
//7. Date
//8. SendDate
//9. MessageName
//
//Start Conferrence Message
//1. Type
//2. Conference Room Name

public class Message {

    private String type;
    private String chk;
    private String sender;
    private String receiver;
    private String sendDate;
    private String contents;
    private String photo;
    private String date;
    private String chatroomname;
    private String MessageName;
    private String vidRoomName;
    private String callTime;
    private String roomCnt;

    public Message(String type, String sender, String receiver)
    {
        this.type=type;
        this.sender=sender;
        this.receiver=receiver;
        this.sendDate = String.valueOf(System.currentTimeMillis() / 1000L);
    }
    public Message()
    {

    }
    public Message(String type, String callTime)
    {
        this.type=type;
        this.callTime=callTime;
    }

    public String getMessageName() {
        return MessageName;
    }
    public void setMessageName(String messageName) {
        MessageName = messageName;
    }
    public String getChatroomname() {
        return chatroomname;
    }
    public void setChatroomname(String chatroomname) {
        this.chatroomname = chatroomname;
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
    public String getSender() {
        return sender;
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
    public String getVidRoomName() {
        return vidRoomName;
    }
    public String getCallTime() {
        return callTime;
    }
}


