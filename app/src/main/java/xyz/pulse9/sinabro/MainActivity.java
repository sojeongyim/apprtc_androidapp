package xyz.pulse9.sinabro;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import xyz.pulse9.sinabro.util.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity implements ChatRoomListFragment.OnFragmentInteractionListener, TimelineFragment.OnFragmentInteractionListener, TeacherlistFragment.OnFragmentInteractionListener {
    final String TAG = "MainActivity";
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("users");
    final FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
    final String uid = curuser.getUid();

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
                    fragment = new ChatRoomListFragment();
                    loadFragment(fragment);
                    item.setChecked(true);
                    return true;
                case R.id.navigation_teachers:
                    fragment = new TeacherlistFragment();
                    loadFragment(fragment);
                    item.setChecked(true);
                    return true;
                case R.id.navigation_schedule:
                    Toast toast = Toast.makeText(getApplicationContext(),"Coming Soon...", Toast.LENGTH_SHORT);
                    toast.show();
                case R.id.navigation_profile:
                    Toast toast2 = Toast.makeText(getApplicationContext(),
                            "Coming Soon...", Toast.LENGTH_SHORT);
                    toast2.show();

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");
        final FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = curuser.getUid();

        String photo = curuser.getPhotoUrl().toString();
        String email = curuser.getEmail();
        String nickname = curuser.getDisplayName();
        String token = FirebaseInstanceId.getInstance().getToken();

        ref.child(uid).child("email").setValue(email);
        ref.child(uid).child("token").setValue(token);
        ref.child(uid).child("nickname").setValue(nickname);
        ref.child(uid).child("photo").setValue(photo);

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

