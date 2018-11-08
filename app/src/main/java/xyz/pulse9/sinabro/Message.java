package xyz.pulse9.sinabro;

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

    public String type;
    public String chk;
    public String sender;
    public String receiver;
    public String receivertoken;
    public String senddate;
    public String contents;
    public String photo;
    public String date;
    public String chatroomname;
    public String messagename;
    public String vidroomname;
    public String callTime;
    public String roomcnt;
    public String sendernick;



//    public String getTime() throws Exception {
//        String url = "https://time.is/Unix_time_now";
//        Document doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
//        String[] tags = new String[] {
//                "div[id=time_section]",
//                "div[id=clock0_bg]"
//        };
//        Elements elements= doc.select(tags[0]);
//        for (int i = 0; i <tags.length; i++) {
//            elements = elements.select(tags[i]);
//        }
//        return elements.text();
//    }

    public Message(String type, String sender, String receiver, String receiverToken)
    {
        this.type=type;
        this.sender=sender;
        this.receiver=receiver;
        this.senddate = String.valueOf(System.currentTimeMillis() / 1000L);
        this.receivertoken=receiverToken;
//        try {
//            this.sendDate = getTime();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    public Message()
    {

    }
    public Message(String type, String callTime)
    {
        this.type=type;
        this.callTime=callTime;
        this.senddate = String.valueOf(System.currentTimeMillis() / 1000L);
//        try {
//            this.sendDate = getTime();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        this.contents = "Video Call End";
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceivertoken() {
        return receivertoken;
    }

    public void setReceivertoken(String receivertoken) {
        this.receivertoken = receivertoken;
    }

    public String getSenddate() {
        return senddate;
    }

    public void setSenddate(String senddate) {
        this.senddate = senddate;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChatroomname() {
        return chatroomname;
    }

    public void setChatroomname(String chatroomname) {
        this.chatroomname = chatroomname;
    }

    public String getMessagename() {
        return messagename;
    }

    public void setMessagename(String messagename) {
        this.messagename = messagename;
    }

    public String getVidroomname() {
        return vidroomname;
    }

    public void setVidroomname(String vidroomname) {
        this.vidroomname = vidroomname;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getRoomcnt() {
        return roomcnt;
    }

    public void setRoomcnt(String roomcnt) {
        this.roomcnt = roomcnt;
    }

    public String getSendernick() {
        return sendernick;
    }

    public void setSendernick(String sendernick) {
        this.sendernick = sendernick;
    }
}


