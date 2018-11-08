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
    private long cnt;

    public ChatRoom()
    {

    }

    public ChatRoom(String Roomname, String Receiver)
    {
        this.RoomName = Roomname;
        this.receiver = Receiver;
    }

    public ChatRoom(String roomName, String receiver, String nickname, String photo, String lastcontents, String time) {
        this.RoomName = roomName;
        this.receiver = receiver;
        this.nickname = nickname;
        this.photo = photo;
        this.lastcontents = lastcontents;
        this.time = time;
        this.cnt = 0;
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
    public void setTime(String time) {
        this.time = time;
    }

    public long getCnt() {
        return cnt;
    }

    public void setCnt(long cnt) {
        this.cnt = cnt;
    }
}
