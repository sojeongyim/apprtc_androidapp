package xyz.pulse9.sinabro;

public class Alarm {
    Long date;
    String roomname;
    String receiverUID;
    String senderUID;

    public Alarm (Long date, String roomname, String receiverUID, String senderUID) {
        this.date = date;
        this.roomname = roomname;
        this.receiverUID = receiverUID;
        this.senderUID = senderUID;
    }
    public Alarm()
    {
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getReceiverUID() {
        return receiverUID;
    }

    public void setReceiverUID(String receiverUID) {
        this.receiverUID = receiverUID;
    }

    public String getSenderUID() {
        return senderUID;
    }

    public void setSenderUID(String senderUID) {
        this.senderUID = senderUID;
    }
}
