package xyz.pulse9.sinabro;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TeacherTabFrg extends Fragment {
    private String teacherToken;
    private DatabaseReference userDatabase;
    DatabaseReference followerDB;
    private String uid;
    FirebaseUser curuser;
    TextView followertext;
    ImageView profile_image;
    TextView user_name;
    String targetUID;

    String receivernick;
    String receiverphoto;

    private String chatRoomname;
    ImageButton heart_butt;
    ImageButton heart_butt_check;
    ImageButton plus_button;
    LinearLayoutCompat profile_card;
    Animation clickanimation;

    public void setTeacherToken(String teacherToken){
        this.teacherToken=teacherToken;
    }
    public void ischatExist(final String Userid) {
        DatabaseReference myDB = FirebaseDatabase.getInstance().getReference("users").child(uid).child("rooms");
        myDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean chk = false;
                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    targetUID = data.child("receiver").getValue().toString();
                    if(targetUID.equals(Userid))
                    {
                        chk = true;
                        chatRoomname = data.getKey().toString();
                    }
                }
                if(!chk)
                {
                    chatRoomname = "none";
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        chatRoomname = "null";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        curuser = FirebaseAuth.getInstance().getCurrentUser();
        uid = curuser.getUid();
        userDatabase = FirebaseDatabase.getInstance().getReference("users");
        followerDB = userDatabase.child(teacherToken).child("follower");
        ischatExist(teacherToken);
        clickanimation = AnimationUtils.loadAnimation(getContext(),R.anim.clickanimaiton);
        followertext =(TextView)getView().findViewById(R.id.follower_num);
        profile_image = (ImageView) getView().findViewById(R.id.profile_image);
        heart_butt = (ImageButton) getView().findViewById(R.id.user_heart);
        heart_butt_check = (ImageButton) getView().findViewById(R.id.heart_check);
        plus_button = (ImageButton)getView().findViewById(R.id.plus_button);
        user_name=(TextView)getView().findViewById(R.id.user_name);
        profile_card =(LinearLayoutCompat)getView().findViewById(R.id.profile_card);
        GradientDrawable drawable = (GradientDrawable)getContext().getResources().getDrawable(R.drawable.rounded);
        profile_card.setBackground(drawable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            profile_card.setClipToOutline(true);
        }

        userDatabase.child(teacherToken).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                receivernick = dataSnapshot.child("nickname").getValue().toString();
                receiverphoto = dataSnapshot.child("photo").getValue().toString();
                user_name.setText(receivernick);
                Picasso.get().load(receiverphoto)
                        .transform(new CropCircleTransformation())
                        .into(profile_image);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        profile_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ischatExist(teacherToken);
                if(!chatRoomname.equals("null")) {
                    Intent intent = new Intent(getActivity(), ConnectActivity.class);
                    intent.putExtra("chatroomname", chatRoomname);
                    intent.putExtra("receiverUID", teacherToken);
                    startActivity(intent);
                }

            }
        });

        followerDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                followertext.setText(Long.toString(dataSnapshot.getChildrenCount()));
                if(dataSnapshot.child(uid).getValue()!=null){
                    heart_butt_check.setVisibility(View.VISIBLE);
                    heart_butt.setVisibility(View.INVISIBLE);
                }else{
                    heart_butt_check.setVisibility(View.INVISIBLE);
                    heart_butt.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        heart_butt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                followerDB.child(uid).setValue("1");
            }
        });
        heart_butt_check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                followerDB.child(uid).removeValue();
            }
        });
        plus_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view.startAnimation(clickanimation);
                Toast toast = Toast.makeText(getActivity(),"Coming Soon...", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.teacher_one, container, false);

    }

}
