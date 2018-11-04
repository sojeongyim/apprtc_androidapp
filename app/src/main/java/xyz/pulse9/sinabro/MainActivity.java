package xyz.pulse9.sinabro;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import xyz.pulse9.sinabro.util.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity implements TeacherlistFragment.OnFragmentInteractionListener {
    final String TAG = "MainActivity";
    private FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("users");

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_timeline:
                    fragment = new TimelineFragment();
                    loadFragment(fragment);
                    item.setChecked(true);
                    return true;
                case R.id.navigation_chatings:
                    if(curuser==null)
                    {
                        showDialog();
                    }
                    else {
                        fragment = new ChatRoomListFragment();
                        loadFragment(fragment);
                        item.setChecked(true);
                        return true;
                    }
                    break;
                case R.id.navigation_teachers:
                    if(curuser==null)
                    {
                        showDialog();
                    }
                    else {
                        fragment = new TeacherlistFragment();
                        loadFragment(fragment);
                        item.setChecked(true);
                        return true;
                    }
                    break;
                case R.id.navigation_schedule:
                case R.id.navigation_profile:
                    Toast toast = Toast.makeText(getApplicationContext(),"Coming Soon...", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRoomSharedPref("");
        setUserSharedPref();

        if(curuser !=null) {
            String uid = curuser.getUid();
            String photo = curuser.getPhotoUrl().toString();
            String email = curuser.getEmail();
            String nickname = curuser.getDisplayName();
            String token = FirebaseInstanceId.getInstance().getToken();

            ref.child(uid).child("email").setValue(email);
            ref.child(uid).child("token").setValue(token);
            ref.child(uid).child("nickname").setValue(nickname);
            ref.child(uid).child("photo").setValue(photo);
            FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_timeline);
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContainer, fragment);
        transaction.commit();
    }
    private void showDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sinabro");
        builder.setMessage("Please Sign up this beautiful app! ");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Thanks!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Maybe Next Time!",Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        setRoomSharedPref("");
    }

    public void setRoomSharedPref(String roomname) {
        SharedPreferences pref = getSharedPreferences("sina_set", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("cur_roomname", roomname);
        editor.commit();
    }
    public void setUserSharedPref() {
        SharedPreferences pref = getSharedPreferences("sina_set", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String uid = curuser.getUid();
        String photo = curuser.getPhotoUrl().toString();
        String email = curuser.getEmail();
        String nickname = curuser.getDisplayName();
        String token = FirebaseInstanceId.getInstance().getToken();

        editor.putString("uid", uid);
        editor.putString("photo", photo);
        editor.putString("email", email);
        editor.putString("nickname", nickname);
        editor.putString("token", token);
        editor.commit();
    }
    public void setRoomListSharedPref()
    {
        DatabaseReference temp = ref.child(curuser.getUid()).child("rooms");
        temp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<ChatRoom> tmpset = new HashSet<>();
                for(DataSnapshot t : dataSnapshot.getChildren())
                {
                    String tmp_receiver;
                    String tmp_roomName;
                    tmp_receiver = t.child("receiver").getValue().toString();
                    tmp_roomName = t.child("roomName").getValue().toString();
                    ChatRoom tmp_Room = new ChatRoom(tmp_roomName, tmp_receiver);
                    for(Iterator<ChatRoom> k = tmpset.iterator();k.hasNext();)
                    {
                        if(!k.next().getNickname().equals(tmp_receiver))
                        {
                            tmpset.add(tmp_Room);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void setSharedPrefRoomList(HashSet<String> k)
    {
        SharedPreferences pref = getSharedPreferences("sina_set", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putStringSet("roomList", k);
        editor.commit();
    }
}

