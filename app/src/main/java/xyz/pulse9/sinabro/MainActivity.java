package xyz.pulse9.sinabro;

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
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements ChatRoomListFragment.OnFragmentInteractionListener, TimelineFragment.OnFragmentInteractionListener, TeacherlistFragment.OnFragmentInteractionListener {
    final String TAG = "MainActivity";

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
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");
        final FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
//        ImageView testImg = findViewById(R.id.testImgV);
//        Picasso.get().load("https://lh6.googleusercontent.com/-3_qN5O6SBqY/AAAAAAAAAAI/AAAAAAAAAAA/APUIFaNvq_pFyFyF2jiSboPHs8CzJV24uw/s96-c/photo.jpg")
//                .transform(new CropCircleTransformation())
//                .into(testImg);

        Log.d(TAG, "Url : " +         curuser.getPhotoUrl());
        final String uid = curuser.getUid();
        Log.d(TAG, "uid : " + uid);
        String photo = curuser.getPhotoUrl().toString();
        String email = curuser.getEmail();
        String nickname = curuser.getDisplayName();
        String token = FirebaseInstanceId.getInstance().getToken();

        ref.child(uid).child("email").setValue(email);
        ref.child(uid).child("token").setValue(token);
        ref.child(uid).child("nickname").setValue(nickname);
        ref.child(uid).child("photo").setValue(photo);

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        DatabaseReference useralarmDatabase = FirebaseDatabase.getInstance().getReference("users").child(uid).child("Alarm");
        useralarmDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "Alaram is added to " + uid);
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    data.getValue();
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

//        loadFragment(new TimelineFragment());
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_timeline);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContainer, fragment);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
