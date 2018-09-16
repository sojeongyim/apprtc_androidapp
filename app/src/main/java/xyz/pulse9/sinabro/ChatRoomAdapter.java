package xyz.pulse9.sinabro;

import android.content.Context;

import android.graphics.Color;

import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.ArrayAdapter;

import android.widget.LinearLayout;
import android.widget.TextView;

import xyz.pulse9.sinabro.Message;

import java.util.ArrayList;

import java.util.List;

public class ChatRoomAdapter extends ArrayAdapter {

    List ChatRooms = new ArrayList();

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


    public void refresh(String roomName, String Title, String Time)
    {


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
        // Array List에 들어 있는 채팅 문자열을 읽어
        ChatRoom chatRoom = (ChatRoom) ChatRooms.get(position);

        // Inflater를 이용해서 생성한 View에, ChatMessage를 삽입한다.
        TextView msgText = (TextView) row.findViewById(R.id.firstLine);
        TextView msgText2 = (TextView) row.findViewById(R.id.secondLine);
        TextView msgText3 = (TextView) row.findViewById(R.id.textView2);
        msgText.setText(chatRoom.getReceiver());
        msgText2.setText(chatRoom.getTitle());
        msgText3.setText(chatRoom.getLastDate());
        boolean message_left = true;
        LinearLayout chatMessageContainer = (LinearLayout)row.findViewById(R.id.textLinear);

        return row;
    }
}



