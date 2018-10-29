package xyz.pulse9.sinabro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ChatRoomAdapter extends ArrayAdapter {
    List<ChatRoom> ChatRooms = new ArrayList<ChatRoom>();

    public ChatRoomAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }
    public void add(ChatRoom object){
        ChatRooms.add(object);
        super.add(object);
    }
    public void delete(ChatRoom object){
        ChatRooms.remove(object);
        super.remove(object);
    }
    public void refresh(String roomName, String title, String Time) {
        for(ChatRoom k : ChatRooms)
        {
            if(k.getRoomName().equals(roomName))
            {
                k.setLastcontents(title);
                k.setTime(Time);
            }
        }
    }
    public void sortList() {
        Comparator<ChatRoom> Asc = new Comparator<ChatRoom>() {
            @Override
            public int compare(ChatRoom item1, ChatRoom item2) {
                return -(item1.getTime().compareTo(item2.getTime()));
            }
        };
        Collections.sort(ChatRooms, Asc);
    }
    @Override
    public int getCount() {
        return ChatRooms.size();
    }
    @Override
    public ChatRoom getItem(int index) {
        return (ChatRoom) ChatRooms.get(index);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            // inflator를 생성하여, chatting_message.xml을 읽어서 View객체로 생성한다.
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.chat_room, parent, false);
        }

        final ChatRoom chatRoom = ChatRooms.get(position);
        final TextView msgText = (TextView) row.findViewById(R.id.firstLine);

        ImageView userpic = row.findViewById(R.id.userpic);
        Picasso.get().load(chatRoom.getPhoto())
                .transform(new CropCircleTransformation())
                .into(userpic);
        TextView msgText2 = (TextView) row.findViewById(R.id.secondLine);
        TextView msgText3 = (TextView) row.findViewById(R.id.thirdLine);
        msgText.setText(chatRoom.getNickname());
        msgText2.setText(chatRoom.getLastcontents());
        String time = chatRoom.getTime();


        Date confDate2 = new Date(Long.parseLong(chatRoom.getTime())*1000L);
        Calendar cal2 = Calendar.getInstance();
        TimeZone tz2 = cal2.getTimeZone();
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        sdf2.setTimeZone(tz2);
        time = sdf2.format(confDate2);

        msgText3.setText(time);
        return row;
    }
}