package xyz.pulse9.sinabro;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatRoom {

    private String RoomName;
    private String receiver;
    private String nickname;
    private String photo;
    private String lastcontents;
    private String time;

    public ChatRoom(String roomName, String receiver, String nickname, String photo, String lastcontents, String time) {
        this.RoomName = roomName;
        this.receiver = receiver;
        this.nickname = nickname;
        this.photo = photo;
        this.lastcontents = lastcontents;
        this.time = time;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLastcontents() {
        return lastcontents;
    }

    public void setLastcontents(String lastcontents) {
        this.lastcontents = lastcontents;
    }

    public String getTime() {
        return time;
    }
    public Date getDate_Time()
    {
        Date date;
        String test = this.time;

        SimpleDateFormat parserSDF = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
        try {
            date = parserSDF.parse(test);
            Log.d("JMTEST", "GETTIME() : " + date.getTime());
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
