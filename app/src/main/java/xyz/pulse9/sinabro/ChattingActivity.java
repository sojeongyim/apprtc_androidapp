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
    private DatabaseReference userDatabase;

    final String TAG= "ChattingActivity";
    ChatAdapter chatAdapter;
    String chatroomname;
    private String uid;
    private String receiveruid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        chatroomname = intent.getStringExtra("chatroomname");
        uid = intent.getStringExtra("uid");
        receiveruid = intent.getStringExtra("receiveruid");

        myDatabase= FirebaseDatabase.getInstance().getReference("message").child(chatroomname);
        userDatabase = FirebaseDatabase.getInstance().getReference("users");

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

        myDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(!dataSnapshot.getRef().getKey().toString().equals("personA") && !dataSnapshot.getRef().getKey().toString().equals("personB")) {

                    String contents = dataSnapshot.child("contents").getValue().toString();
                    String time = dataSnapshot.child("sendDate").getValue().toString();
                    Message mMessage = new Message(uid, contents);

                    userDatabase.child(uid).child("rooms").child(chatroomname).child("receiver").setValue(receiveruid);
                    userDatabase.child(uid).child("rooms").child(chatroomname).child("lastContents").setValue(contents);
                    userDatabase.child(uid).child("rooms").child(chatroomname).child("time").setValue(time);

                    userDatabase.child(receiveruid).child("rooms").child("chatroomname").child("receiver").setValue(uid);
                    userDatabase.child(receiveruid).child("rooms").child("chatroomname").child("lastContents").setValue(contents);
                    userDatabase.child(receiveruid).child("rooms").child("chatroomname").child("time").setValue(time);

                    chatAdapter.add(mMessage);
                }


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

    }

    public void sendMessage(View view)
    {
        EditText sendMsg= (EditText)findViewById(R.id.sendMsg);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("message").child(chatroomname);

        Message mMessage = new Message(uid, sendMsg.getText().toString());
        ref.push().setValue(mMessage);

        sendMsg.setText("");
    }
}