package xyz.pulse9.sinabro;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.webrtc.ContextUtils.getApplicationContext;

public class ChatAdapter extends ArrayAdapter {

    private static final int ITEM_VIEW_TYPE_MSG = 0;
    private static final int ITEM_VIEW_TYPE_CALL = 1;
    private String DateTime = null;

    List<Message> msgs = new ArrayList();

    public ChatAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    //@Override
    public void add(Message object) {

        msgs.add(object);
        Log.d("test", "Its adds " + object.getType());

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

        final FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();

        final Message msg = msgs.get(position);
        Log.d("test", "Its type isaaa " + viewType);



        switch (viewType) {
            case ITEM_VIEW_TYPE_MSG:

                // Array List에 들어 있는 채팅 문자열을 읽어
                boolean message_left = true;
                convertView = inflater.inflate(R.layout.chat_message,
                        parent, false);
                TextView titleTextView = (TextView) convertView.findViewById(R.id.contentsTxt);
                titleTextView.setText(msg.getContents());

                if (msg.getSender().equals(curuser.getUid())) {
                    message_left = true;
                } else {
                    message_left = false;
                }


                titleTextView.setBackground(this.getContext().getResources().getDrawable((message_left ? R.drawable.inbox_out_shot_res2: R.drawable.inbox_in_shot_res2 )));

                titleTextView.setTextColor(Color.parseColor(message_left ? "#0f2013" : "#c7c7c7")); //sinabro_black  &  sinabro_gray

                LinearLayout chatMessageContainer = (LinearLayout) convertView.findViewById(R.id.textLinear);

                int align;

                // Inflater를 이용해서 생성한 View에, ChatMessage를 삽입한다.
                if (message_left) {
                    TextView msgText = (TextView) convertView.findViewById(R.id.contentsTxt);
                    align = Gravity.LEFT;
                } else {
                    align = Gravity.RIGHT;
                }
                chatMessageContainer.setGravity(align);
                break;

            case ITEM_VIEW_TYPE_CALL:
                convertView = inflater.inflate(R.layout.chat_videocall, parent, false);
                final DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference("users");

                final Button acceptBtn = convertView.findViewById(R.id.acceptBtn);
                final Button denyBtn = convertView.findViewById(R.id.denyBtn);
                final Button resultBtn = convertView.findViewById(R.id.resultBtn);
                resultBtn.setEnabled(false);
                if (msg.getChk()==1)
                {
                    acceptBtn.setVisibility(View.GONE);
                    denyBtn.setVisibility(View.GONE);
                    resultBtn.setText("Accepted");
                    resultBtn.setVisibility(View.VISIBLE);


                }
                else if(msg.getChk()==2)
                {
                    denyBtn.setVisibility(View.GONE);
                    acceptBtn.setVisibility(View.GONE);
                    resultBtn.setText("Denied");
                    resultBtn.setVisibility(View.VISIBLE);
                }

                acceptBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        acceptBtn.setVisibility(View.GONE);
                        denyBtn.setVisibility(View.GONE);
                        resultBtn.setText("Accepted");
                        resultBtn.setVisibility(View.VISIBLE);
                        msg.setChk(1);
                        userDatabase.child(curuser.getUid()).child("Alarm").push().setValue(msg.getDate());
                    }
                });

                denyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        denyBtn.setVisibility(View.GONE);
                        acceptBtn.setVisibility(View.GONE);
                        resultBtn.setText("Denied");
                        resultBtn.setVisibility(View.VISIBLE);
                        msg.setChk(2);
                    }
                });

                TextView date = convertView.findViewById(R.id.dateTime);
                date.setText(msg.getDate());
                break;
        }
        return convertView;
    }


}



