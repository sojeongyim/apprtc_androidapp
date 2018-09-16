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

public class ChatAdapter extends ArrayAdapter {



    List msgs = new ArrayList();



    public ChatAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    //@Override
    public void add(Message object){
        msgs.add(object);
        super.add(object);
    }


    @Override
    public int getCount() {
        return msgs.size();
    }

    @Override
    public Message getItem(int index) {
        return (Message) msgs.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            // inflator를 생성하여, chatting_message.xml을 읽어서 View객체로 생성한다.
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.chat_message, parent, false);
        }

        // Array List에 들어 있는 채팅 문자열을 읽어
        Message msg = (Message) msgs.get(position);

        // Inflater를 이용해서 생성한 View에, ChatMessage를 삽입한다.
        TextView msgText = (TextView) row.findViewById(R.id.contentsTxt);
        msgText.setText(msg.getMessage());

        boolean message_left = true;

        msgText.setBackground(this.getContext().getResources().getDrawable( (message_left ? R.drawable.word_resize2 : R.drawable.transpose2 )));

        LinearLayout chatMessageContainer = (LinearLayout)row.findViewById(R.id.textLinear);

        int align;

        if(message_left) {

            align = Gravity.LEFT;

            message_left = false;

        }else{

            align = Gravity.RIGHT;

            message_left=true;

        }

        chatMessageContainer.setGravity(align);


        msgText.setTextColor(Color.parseColor("#000000"));
        return row;
    }
}



