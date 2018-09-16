package xyz.pulse9.sinabro;

import android.net.Uri;

import java.util.Date;

public class ChatRoom {

    private String RoomName;
    private String Receiver;
    private String Title;
    private Uri ProfileUri;
    private String LastDate;


    public ChatRoom(String RoomName, String receiver, String title, String LastDate)
    {
        this.RoomName = RoomName;
        this.Receiver = receiver;
        this.Title = title;
        this.LastDate=LastDate;
    }

    public ChatRoom(String receiver, String title, Uri profileUri)
    {
        this.Receiver = receiver;
        this.Title = title;
        this.ProfileUri = profileUri;
    }

    public ChatRoom(String receiver, String title)
    {
        this.Receiver = receiver;
        this.Title = title;
        this.ProfileUri = null;
    }
    public String getRoomName() {
        return RoomName;
    }

    public String getReceiver() {
        return Receiver;
    }


    public String getTitle() {
        return Title;
    }


    public Uri getProfileUri() {
        return ProfileUri;
    }

    public String getLastDate() {
        return LastDate;
    }
}
