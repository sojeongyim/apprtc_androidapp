package xyz.pulse9.sinabro;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements ChatRoomListFragment.OnFragmentInteractionListener, TimelineFragment.OnFragmentInteractionListener, TeacherlistFragment.OnFragmentInteractionListener {
    final String TAG = "MainActivity";
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("users");
    final FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
    final String uid = curuser.getUid();
    DatabaseReference useralarmDatabase = FirebaseDatabase.getInstance().getReference("users").child(uid).child("Alarm");


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_timeline:
                    fragment = new TimelineFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_chatings:
                    fragment = new ChatRoomListFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_teachers:
                    fragment = new TeacherlistFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d(TAG, "uid : " + uid);
        String email = curuser.getEmail();
        String nickname = curuser.getDisplayName();
        String token = FirebaseInstanceId.getInstance().getToken();

        ref.child(uid).child("email").setValue(email);
        ref.child(uid).child("token").setValue(token);
        ref.child(uid).child("nickname").setValue(nickname);

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);




        useralarmDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "Alaram is added to " + uid);
                String DateTime = dataSnapshot.getValue().toString();
                String AlarmId = dataSnapshot.getKey().toString();
//                Log.d(TAG, "Alarm datetime is " + DateTime);

                DateTime = DateTime.replaceAll(" " , "");
                String[] parseDateTime = DateTime.split("\\.");
                String[] parseTime = parseDateTime[2].split(":");
                int year = Integer.parseInt(parseDateTime[0].substring(5));
                int month = Integer.parseInt(parseDateTime[1]);
                int date =Integer.parseInt(parseDateTime[2].substring(0,2));
                int hour =Integer.parseInt(parseTime[1]);
                int min =Integer.parseInt(parseTime[2]);
//                Log.d(TAG, "year: " + year+"//month: "+month+"//date : "+date+"//hour: "+hour+"//min: "+min);
//                new AlarmHATT(getApplicationContext()).Alarm(AlarmId,year,month,date,hour,min);
                AlarmManager am = (AlarmManager)MainActivity.this.getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(MainActivity.this, PushInAppReceiver.class);

                PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

                Calendar calendar = Calendar.getInstance();
                //알람시간 calendar에 set해주기

                calendar.set(year, month, date, hour, min, 0);

                //알람 예약
                am.set(AlarmManager.RTC, calendar.getTimeInMillis(), sender);

                useralarmDatabase.child(AlarmId).removeValue();
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

        loadFragment(new TimelineFragment());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_teachers);

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContainer, fragment);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public class AlarmHATT {
        private Context context;
        public AlarmHATT(Context context) {
            this.context=context;
        }

        public void Alarm(String id,int year,int month, int date,int hour, int min) {
            AlarmManager am = (AlarmManager)MainActivity.this.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(MainActivity.this, PushInAppReceiver.class);

            PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            //알람시간 calendar에 set해주기

            calendar.set(year, month, date, hour, min, 0);

            //알람 예약
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

            useralarmDatabase.child(id).removeValue();
        }
    }
}
