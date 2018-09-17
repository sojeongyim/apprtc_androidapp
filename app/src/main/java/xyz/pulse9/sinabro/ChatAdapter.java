package xyz.pulse9.sinabro;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
                Log.d("test", "in Switch " + viewType);
                convertView = inflater.inflate(R.layout.chat_message,
                        parent, false);
                TextView titleTextView = (TextView) convertView.findViewById(R.id.contentsTxt);
                titleTextView.setText(msg.getContents());
                boolean message_left = true;
                titleTextView.setBackground(this.getContext().getResources().getDrawable((message_left ? R.drawable.word_resize2 : R.drawable.transpose2)));
                titleTextView.setTextColor(Color.parseColor("#000000"));
                break;

            case ITEM_VIEW_TYPE_CALL:
                Log.d("test", "in Switch " + viewType);

                convertView = inflater.inflate(R.layout.chat_videocall,
                        parent, false);
                TextView caller = convertView.findViewById(R.id.VideoCall);
                TextView date = convertView.findViewById(R.id.dataTime);

                caller.setText("Jangmin");
                date.setText("1 /2 / 1995");
                Log.d("test", "Its is switch " + viewType);

                break;
        }
        return convertView;
    }
}

