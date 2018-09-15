package xyz.pulse9.sinabro;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ChattingActivity extends AppCompatActivity {

    private DatabaseReference myDatabase;

    final String TAG= "ChattingActivity";
    ChatAdapter chatAdapter;
    String chatroomname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        chatroomname = intent.getStringExtra("chatroomname");

        chatAdapter = new ChatAdapter(this.getApplicationContext(),R.layout.chat_message);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        Log.d("ChattingActivity", chatAdapter.toString());
        final ListView listView = (ListView)findViewById(R.id.chatListview);
        listView.setAdapter(chatAdapter);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL); // 이게 필수
        // When message is added, it makes listview to scroll last message
        chatAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override

            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatAdapter.getCount()-1);
            }
        });



        myDatabase= FirebaseDatabase.getInstance().getReference("message").child(chatroomname);

        myDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String contents = dataSnapshot.child("contents").getValue().toString();
                String sender = dataSnapshot.child("sender").getValue().toString();
                String receiver = dataSnapshot.child("receiver").getValue().toString();
                Message mMessage = new Message(sender,receiver, contents);
                chatAdapter.add(mMessage);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



//        myDatabase.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                chatText.setText(dataSnapshot.getValue().toString());
//                for (DataSnapshot child : dataSnapshot.getChildren()) {
////                    chatText.setText(dataSnapshot.getChildren().toString());
////                    chatText.setText(chatText.getText().toString()+child.child("contents").getValue().toString());
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                chatText.setText("CANCELLED");
//
//            }
//        });

    }

    public void sendMessage(View view)
    {
        EditText sendMsg= (EditText)findViewById(R.id.sendMsg);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("message").child(chatroomname);

        Message mMessage = new Message("jangmin",sendMsg.getText().toString());
        ref.push().setValue(mMessage);

        sendMsg.setText("");
    }
}