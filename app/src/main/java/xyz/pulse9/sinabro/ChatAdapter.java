package xyz.pulse9.sinabro;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends ArrayAdapter {

    private static final int ITEM_VIEW_TYPE_MSG = 0;
    private static final int ITEM_VIEW_TYPE_CALL = 1;

    List<Message> msgs = new ArrayList();

    public ChatAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    //@Override
    public void add(Message object) {

        msgs.add(object);
        Log.d("test", "Its adds " +object.getType());

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
    public int getItemViewType(int position) {
        return msgs.get(position).getType();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        LayoutInflater inflater;
        inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        Message msg = msgs.get(position);
        Log.d("test", "Its type isaaa " + viewType);

        switch (viewType) {
            case ITEM_VIEW_TYPE_MSG:
                final FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();

                                // Array List에 들어 있는 채팅 문자열을 읽어
                boolean message_left = true;
                convertView = inflater.inflate(R.layout.chat_message,
                        parent, false);
                TextView titleTextView = (TextView) convertView.findViewById(R.id.contentsTxt);
                titleTextView.setText(msg.getContents());

                Log.d("ChatAdapter", "mgs - Get Sender : " + msg.getSender());
                Log.d("ChatAdapter", "curuser Get Uid : " + curuser.getUid());

                if(msg.getSender().equals(curuser.getUid()))
                {
                    message_left=true;
                }
                else
                {
                    message_left=false;
                }

                titleTextView.setBackground(this.getContext().getResources().getDrawable((message_left ? R.drawable.word_resize2 : R.drawable.transpose2)));
                titleTextView.setTextColor(Color.parseColor("#000000"));

                LinearLayout chatMessageContainer = (LinearLayout)convertView.findViewById(R.id.textLinear);

                int align;

                // Inflater를 이용해서 생성한 View에, ChatMessage를 삽입한다.
                if(message_left) {
                    TextView msgText = (TextView) convertView.findViewById(R.id.contentsTxt);
                    align = Gravity.LEFT;
                }else{
                    align = Gravity.RIGHT;
                }
                chatMessageContainer.setGravity(align);
                break;

            case ITEM_VIEW_TYPE_CALL:
                Log.d("test", "in Switch " + viewType);

                convertView = inflater.inflate(R.layout.chat_videocall,
                        parent, false);
                TextView caller = convertView.findViewById(R.id.VideoCall);
                TextView date = convertView.findViewById(R.id.dataTime);

                caller.setText(msg.getCaller());
                date.setText(msg.getDate());

                break;
        }
        return convertView;
    }
}


//
//
//    Message msg = (Message) msgs.get(position);
//
//    // Inflater를 이용해서 생성한 View에, ChatMessage를 삽입한다.
//    TextView msgText = (TextView) row.findViewById(R.id.contentsTxt);
//        msgText.setText(msg.getMessage());
//
//                // Array List에 들어 있는 채팅 문자열을 읽어
//                boolean message_left = true;
//
//                Message msg = (Message) msgs.get(position);
//                msgText.setBackground(this.getContext().getResources().getDrawable( (message_left ? R.drawable.word_resize2 : R.drawable.transpose2 )));
//
//                LinearLayout chatMessageContainer = (LinearLayout)row.findViewById(R.id.textLinear);
//
//                int align;
//
//                // Inflater를 이용해서 생성한 View에, ChatMessage를 삽입한다.
//                if(message_left) {
//
//                TextView msgText = (TextView) row.findViewById(R.id.contentsTxt);
//                align = Gravity.LEFT;
//                msgText.setText(msg.getMessage());
//                message_left = false;
//                }else{
//                align = Gravity.RIGHT;
//                message_left=true;
//                }
//                chatMessageContainer.setGravity(align);
//                msgText.setTextColor(Color.parseColor("#000000"));
//
//                return row;
