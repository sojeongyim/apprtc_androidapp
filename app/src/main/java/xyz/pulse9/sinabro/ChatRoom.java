package xyz.pulse9.sinabro;

import android.net.Uri;

import java.util.Date;

public class ChatRoom {

    private String Receiver;
    private String Title;
    private Uri ProfileUri;
    private Date LastDate;

    public ChatRoom(String receiver, String title, Uri profileUri)
    {
        this.Receiver = receiver;
        this.Title = title;
        this.ProfileUri = profileUri;
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

    public Date getLastDate() {
        return LastDate;
    }
}
