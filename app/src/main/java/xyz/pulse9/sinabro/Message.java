package xyz.pulse9.sinabro;
import com.google.firebase.database.ServerValue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
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



    private String getTime() throws Exception {
        String url = "https://time.is/Unix_time_now";
        Document doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
        String[] tags = new String[] {
                "div[id=time_section]",
                "div[id=clock0_bg]"
        };
        Elements elements= doc.select(tags[0]);
        for (int i = 0; i <tags.length; i++) {
            elements = elements.select(tags[i]);
        }
        return elements.text();
    }

    public Message(String type, String sender, String receiver)
    {
        this.type=type;
        this.sender=sender;
        this.receiver=receiver;
        this.sendDate = String.valueOf(System.currentTimeMillis() / 1000L);
        try {
            this.sendDate = getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Message()
    {

    }
    public Message(String type, String callTime)
    {
        this.type=type;
        this.callTime=callTime;
        this.sendDate = String.valueOf(System.currentTimeMillis() / 1000L);
        this.contents = "Video Call End";
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
    public void setSender(String sender) {
        this.sender = sender;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }
    public void setVidRoomName(String vidRoomName) {
        this.vidRoomName = vidRoomName;
    }
    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }
    public String getRoomCnt() {
        return roomCnt;
    }
    public void setRoomCnt(String roomCnt) {
        this.roomCnt = roomCnt;
    }
}


