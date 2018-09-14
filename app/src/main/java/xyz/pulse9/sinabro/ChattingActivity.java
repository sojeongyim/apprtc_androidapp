package xyz.pulse9.sinabro;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import xyz.pulse9.sinabro.Message;

public class ChattingActivity extends AppCompatActivity {

    private DatabaseReference myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        myDatabase= FirebaseDatabase.getInstance().getReference("message");
        final TextView chatText =(TextView)findViewById(R.id.chatText);


        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatText.setText(dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                chatText.setText("CANCELLED");

            }
        });

    }
    public void sendMessage(View view)
    {
        EditText sendMsg= (EditText)findViewById(R.id.sendMsg);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("message");
        DatabaseReference intref = ref.child("ABC");

        Map<String, Message> mMessage = new HashMap<>();
        mMessage.put("alanisawesome", new Message("June 23, 1912", "Alan Turing"));

        intref.setValue(mMessage);
//        intref.push().setValue(mMessage);

        sendMsg.setText("");
    }
}