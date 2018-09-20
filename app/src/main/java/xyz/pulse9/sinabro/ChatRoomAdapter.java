package xyz.pulse9.sinabro;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
        TextView msgText2 = (TextView) row.findViewById(R.id.secondLine);
        TextView msgText3 = (TextView) row.findViewById(R.id.textView2);

        msgText.setText(chatRoom.getNickname());
        msgText2.setText(chatRoom.getLastcontents());
        msgText3.setText(chatRoom.getTime());
        return row;
    }
}