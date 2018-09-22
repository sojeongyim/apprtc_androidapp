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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.webrtc.ContextUtils.getApplicationContext;

public class ChatAdapter extends ArrayAdapter {

    private static final String ITEM_VIEW_TYPE_MSG = "0";
    private static final String ITEM_VIEW_TYPE_CALL = "1";
    private String DateTime = null;

    List<Message> msgs = new ArrayList();

    public ChatAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }
    public void add(Message object) {
        msgs.add(object);
        super.add(object);
    }
    public String getType(int Pos) {
        return msgs.get(Pos).getType();
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
        LayoutInflater inflater;
        inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
        final String curuid = curuser.getUid();
        LinearLayout chatMessageContainer;
        final Message msg = msgs.get(position);
        String viewType = getType(position);

        boolean message_left = true;
        int align;


        if (msg.getSender().equals(curuid))
        {
            message_left = true;
            align = Gravity.LEFT;

        } else {
            message_left = false;
            align = Gravity.RIGHT;

        }

        switch (viewType) {
            case ITEM_VIEW_TYPE_MSG:

                convertView = inflater.inflate(R.layout.chat_message,parent, false);
                TextView rightText = convertView.findViewById(R.id.rightTime);
                TextView leftText = convertView.findViewById(R.id.leftTime);
                chatMessageContainer = convertView.findViewById(R.id.textLinear);
                Log.d("Message", "Message_Left = "  + message_left);
                Log.d("Message", "Align = "  + align);



                ImageView rightchatPhoto = convertView.findViewById(R.id.rightmessagepic);
                rightchatPhoto.setVisibility(View.GONE);
                ImageView leftchatPhoto = convertView.findViewById(R.id.leftmessagepic);
                leftchatPhoto.setVisibility(View.GONE);

                if(!message_left) {
                    Picasso.get().load(msg.getPhoto())
                            .transform(new CropCircleTransformation())
                            .into(rightchatPhoto);
                    rightchatPhoto.setVisibility(View.VISIBLE);

                    rightText.setText(msg.getSendDate());
                }
                else
                {
                    Picasso.get().load(curuser.getPhotoUrl())
                            .transform(new CropCircleTransformation())
                            .into(leftchatPhoto);
                    leftchatPhoto.setVisibility(View.VISIBLE);

                    leftText.setText(msg.getSendDate());
                }
                TextView titleTextView = (TextView) convertView.findViewById(R.id.contentsTxt);
                titleTextView.setText(msg.getContents());
                titleTextView.setBackground(this.getContext().getResources().getDrawable((message_left ? R.drawable.inbox_out_shot_res2 : R.drawable.inbox_in_shot_res2)));

                titleTextView.setTextColor(Color.parseColor(message_left ? "#0f2013" : "#c7c7c7")); //sinabro_black  &  sinabro_gray
                chatMessageContainer.setGravity(align);

                break;

            case ITEM_VIEW_TYPE_CALL:
                convertView = inflater.inflate(R.layout.chat_videocall, parent, false);
                chatMessageContainer = convertView.findViewById(R.id.video_layout);
                chatMessageContainer.setGravity(align);

                final DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference("users");
                final Button acceptBtn = convertView.findViewById(R.id.acceptBtn);
                final Button denyBtn = convertView.findViewById(R.id.denyBtn);
                final Button resultBtn = convertView.findViewById(R.id.resultBtn);

                resultBtn.setEnabled(false);
                // 0 for Nothing
                // 1 for Another person Accepted
                // 2 for Another Person denied
                if ((msg.getChk()=="0")&& (msg.getSender()==curuid))
                {
                    acceptBtn.setVisibility(View.GONE);
                    denyBtn.setVisibility(View.GONE);
                    resultBtn.setText("Waiting .. ");
                    resultBtn.setVisibility(View.VISIBLE);
                }
                else if (msg.getChk()=="1")
                {
                    denyBtn.setVisibility(View.GONE);
                    acceptBtn.setVisibility(View.GONE);
                    resultBtn.setText("Accepted");
                    resultBtn.setVisibility(View.VISIBLE);
                }
                else if (msg.getChk()=="2")
                {
                    denyBtn.setVisibility(View.GONE);
                    acceptBtn.setVisibility(View.GONE);
                    resultBtn.setText("Denied");
                    resultBtn.setVisibility(View.VISIBLE);
                }

                acceptBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        acceptBtn.setVisibility(View.GONE);
                        denyBtn.setVisibility(View.GONE);
                        resultBtn.setText("Accepted");
                        resultBtn.setVisibility(View.VISIBLE);
                        msg.setChk("1");
                        userDatabase.child(curuid).child("Alarm").push().setValue(msg.getSendDate());   //test
                        userDatabase.child(msg.getReceiver()).child("Alarm").push().setValue(msg.getSendDate());  //test
                    }
                });
                denyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        denyBtn.setVisibility(View.GONE);
                        acceptBtn.setVisibility(View.GONE);
                        resultBtn.setText("Denied");
                        resultBtn.setVisibility(View.VISIBLE);
                        msg.setChk("2");
                    }
                });
                TextView date = convertView.findViewById(R.id.dataTime);
                date.setText(msg.getSendDate());
                break;
        }

        return convertView;
    }


}



