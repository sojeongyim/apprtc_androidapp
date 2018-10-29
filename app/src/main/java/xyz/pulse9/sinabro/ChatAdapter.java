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

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static org.webrtc.ContextUtils.getApplicationContext;

public class ChatAdapter extends ArrayAdapter {
//    General Message = "0";
//    Offer Plan for Video Conferrence = "1";
//    Show Date of Last Message = "2";
//    Start Conferrence Message = "3";
    private static final String ITEM_VIEW_TYPE_MSG = "0";
    private static final String ITEM_VIEW_TYPE_CALL = "1";
    private static final String ITEM_VIEW_TYPE_DATE = "2";
    private static final String ITEM_VIEW_TYPE_CONF = "3";
    private static final String ITEM_VIEW_TYPE_END = "4";
    List<Message> msgs = new ArrayList();
    public ChatAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }
    public void add(Message object) {
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
    public Message getItemByName(String Name)
    {
        for(Message k : msgs)
        {
            if(k.getType().equals("1")) {
                if (k.getMessageName().equals(Name)) {
                    return k;
                }
            }
        }
        return null;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout chatMessageContainer;
        TimeZone tz = Calendar.getInstance().getTimeZone();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(tz);
        sdf2.setTimeZone(tz);

        final String curuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final Message msg = msgs.get(position);
        String viewType = msg.getType();

        boolean message_left = true;
        int align= 0;

        if(msg.getSender()!=null) {
            if (msg.getSender().equals(curuid)) {
                message_left = true;
                align = Gravity.LEFT;

            } else {
                message_left = false;
                align = Gravity.RIGHT;

            }
        }

        switch (viewType) {
            case ITEM_VIEW_TYPE_MSG:
                convertView = inflater.inflate(R.layout.chat_message,parent, false);
                TextView rightText = convertView.findViewById(R.id.rightTime);
                TextView leftText = convertView.findViewById(R.id.leftTime);
                ImageView rightchatPhoto = convertView.findViewById(R.id.rightmessagepic);
                chatMessageContainer = convertView.findViewById(R.id.textLinear);
                rightchatPhoto.setVisibility(View.GONE);

                Date confDate2 = new Date(Long.parseLong(msg.getSendDate())*1000L);
                String t2 = sdf2.format(confDate2); // I assume your timestamp is in seconds and you're converting to milliseconds?

                if(!message_left) {
                    Picasso.get().load(msg.getPhoto())
                            .transform(new CropCircleTransformation())
                            .into(rightchatPhoto);
                    rightchatPhoto.setVisibility(View.VISIBLE);
                    rightText.setText(t2);
                }
                else
                {
                    leftText.setText(t2);
                }
                TextView titleTextView = (TextView) convertView.findViewById(R.id.contentsTxt);
                titleTextView.setText(msg.getContents());
                titleTextView.setBackground(this.getContext().getResources().getDrawable((message_left ? R.drawable.inbox_out_shot_res2 : R.drawable.inbox_in_shot_res2)));
                titleTextView.setTextColor(Color.parseColor(message_left ? "#0f2013" : "#c7c7c7")); //sinabro_black  &  sinabro_gray
                chatMessageContainer.setGravity(align);

                if(msgs.size()>0 && position > 0)
                {
                    Message prev_msg = msgs.get(position-1);
                    if(!prev_msg.getType().equals("2") && !prev_msg.getType().equals("3")&& !prev_msg.getType().equals("4") ) {
                        if (prev_msg.getSender().equals(msg.getSender()))
                        {
                            if (message_left) {
                            } else {
                                rightchatPhoto.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }
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
                if ((msg.getChk().equals("0"))&& (msg.getSender().equals(curuid)))
                {
                    acceptBtn.setVisibility(View.GONE);
                    denyBtn.setVisibility(View.GONE);
                    resultBtn.setText("Waiting .. ");
                    resultBtn.setVisibility(View.VISIBLE);
                }
                else if (msg.getChk().equals("1"))
                {
                    denyBtn.setVisibility(View.GONE);
                    acceptBtn.setVisibility(View.GONE);
                    resultBtn.setText("Accepted");
                    resultBtn.setVisibility(View.VISIBLE);
                }
                else if (msg.getChk().equals("2"))
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

                        Long tmp_time = Long.parseLong(msg.getDate());
                        final DatabaseReference alarmDatabase = FirebaseDatabase.getInstance().getReference("alarm");
                        Alarm tmp_alarm = new Alarm(tmp_time, msg.getChatroomname(), msg.getReceiver(), msg.getSender());
                        alarmDatabase.push().setValue(tmp_alarm);

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref;
                        ref = database.getReference("message").child(msg.getChatroomname());
                        ref.child(msg.getMessageName()).child("chk").setValue("1");
                    }
                });
                denyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        denyBtn.setVisibility(View.GONE);
                        acceptBtn.setVisibility(View.GONE);
                        resultBtn.setText("Denied");
                        resultBtn.setVisibility(View.VISIBLE);

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref;
                        ref = database.getReference("message").child(msg.getChatroomname());
                        ref.child(msg.getMessageName()).child("chk").setValue("2");
                    }
                });
                TextView date = convertView.findViewById(R.id.dataTime);
                Date confDate = new Date(Long.parseLong(msg.getDate())*1000L);
                date.setText(sdf.format(confDate));
                break;
            case ITEM_VIEW_TYPE_DATE:
                convertView = inflater.inflate(R.layout.chat_date,parent, false);
                TextView dateText = convertView.findViewById(R.id.lastDate);
                dateText.setText(msg.getSendDate());
                break;
            case ITEM_VIEW_TYPE_CONF:
                convertView = inflater.inflate(R.layout.chat_startconf,parent, false);
                break;
            case ITEM_VIEW_TYPE_END:
                convertView = inflater.inflate(R.layout.chat_end,parent, false);
                TextView durationTxt = convertView.findViewById(R.id.durationTxt);
                int sec = Integer.parseInt(msg.getCallTime());
                int min = sec/60;
                sec = sec%60;
                durationTxt.setText(min + " : " + sec);
                break;
        }
        convertView.setAlpha(getAlpha(position));
        return convertView;

    }

    private float getAlpha(int position) {
        float min = 0.5f;
        float alpha = Math.abs(getCount() - position - 1);
        alpha = (255f - alpha * 7f) / 255f;
        Log.d("ALPHA", String.valueOf(Math.max(min, alpha)));
        Log.d("ALPHA", "It is alpha : " + String.valueOf(alpha));
        return Math.max(min, alpha);
    }
}