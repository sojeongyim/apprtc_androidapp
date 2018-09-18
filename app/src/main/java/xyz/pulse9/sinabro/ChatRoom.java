package xyz.pulse9.sinabro;

import android.net.Uri;

import java.util.Date;

public class ChatRoom {

    private String RoomName;
    private String receiver;
    private String title;
    private Uri ProfileUri;
    private String time;



//
//            userDatabase.child(uid).child("rooms").child(chatroomname).child("receiver").setValue(receiveruid);
//            userDatabase.child(uid).child("rooms").child(chatroomname).child("lastContents").setValue(contents);
//            userDatabase.child(uid).child("rooms").child(chatroomname).child("time").setValue(time);
    public ChatRoom(String RoomName, String receiver, String title, String LastDate)
    {
        this.RoomName = RoomName;
        this.receiver = receiver;
        this.title = title;
        this.time=LastDate;
    }



    public ChatRoom(String receiver, String title, String time)
    {
        this.receiver = receiver;
        this.title = title;
        this.time = time;
    }
    public String getRoomName() {
        return RoomName;
    }
    public String getReceiver() {
        return receiver;
    }
    public String getTitle() {
        return title;
    }
    public Uri getProfileUri() {
        return ProfileUri;
    }

    public String getLastDate() {
        return time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReceiver(String receiver) {this.receiver = receiver;}
    public void setTime(String time) {
        this.time = time;
    }
}
