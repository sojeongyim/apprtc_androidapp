package xyz.pulse9.sinabro;

/**
 * Created by ssoww on 2018-09-15.
 */

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TabFragment extends Fragment {

    public static class TabFragment1 extends Fragment {

        private String teacher1_token ="Jhbg1lLcwJRP7HcwHwVQwzJDy1H2";////intern1계정
        private DatabaseReference userDatabase;
        private String uid;
        FirebaseUser curuser;


        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            curuser = FirebaseAuth.getInstance().getCurrentUser();
            uid = curuser.getUid();
            userDatabase = FirebaseDatabase.getInstance().getReference("users");

            ImageView imageView_user1 = (ImageView) getView().findViewById(R.id.imageView_user1);
            ImageButton heart_butt = (ImageButton) getView().findViewById(R.id.user1_heart);

            imageView_user1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ConnectActivity.class);
                    intent.putExtra("chatroomname", "ToTeacher1");
                    intent.putExtra("receiveruid", teacher1_token);  //intern1계정으로 전송
                    intent.putExtra("uid", "yrSf3cetwGU8PaBWsJ3aZ6kuKFi1");  //uid 수정필요
                    startActivity(intent);
                }
            });

            heart_butt.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    userDatabase.child(teacher1_token).child("follower").push().setValue(uid);


                }
            });

            userDatabase.child(teacher1_token).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Log.e(dataSnapshot.getKey(),dataSnapshot.getChildrenCount() + "");
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            return inflater.inflate(R.layout.tab_fragment_1, container, false);

        }
    }

    public static class TabFragment2 extends Fragment {

        private String teacher2_token ="luzZy37nmveRpTavmzgAmvOemKw1";////intern2계정

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            ImageView imageView_user2 = (ImageView) getView().findViewById(R.id.imageView_user2);
            imageView_user2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ConnectActivity.class);
                    intent.putExtra("chatroomname", "ToTeacher2");
                    intent.putExtra("receiveruid", teacher2_token);  //intern2계정으로 전송
                    intent.putExtra("uid", "yrSf3cetwGU8PaBWsJ3aZ6kuKFi1");  //uid 수정필요
                    startActivity(intent);
                }
            });

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.tab_fragment_2, container, false);
        }
    }

    public static class TabFragment3 extends Fragment {

        private String teacher3_token ="luzZy37nmveRpTavmzgAmvOemKw1";////intern2계정, 수정필요

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            ImageView imageView_user3 = (ImageView) getView().findViewById(R.id.imageView_user3);
            imageView_user3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ConnectActivity.class);
                    intent.putExtra("chatroomname", "ToTeacher3");
                    intent.putExtra("receiveruid", teacher3_token);  //intern2계정으로 전송 수정필요
                    intent.putExtra("uid", "yrSf3cetwGU8PaBWsJ3aZ6kuKFi1");  //uid 수정필요
                    startActivity(intent);
                }
            });

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.tab_fragment_3, container, false);
        }
    }

    public static class TabFragment4 extends Fragment {

        private String teacher4_token ="luzZy37nmveRpTavmzgAmvOemKw1";////intern2계정, 수정필요

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            ImageView imageView_user4 = (ImageView) getView().findViewById(R.id.imageView_user4);
            imageView_user4.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ConnectActivity.class);
                    intent.putExtra("chatroomname", "ToTeacher4");
                    intent.putExtra("receiveruid", teacher4_token);  //intern2계정으로 전송, 수정필요
                    intent.putExtra("uid", "yrSf3cetwGU8PaBWsJ3aZ6kuKFi1");  //uid 수정필요
                    startActivity(intent);
                }
            });

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.tab_fragment_4, container, false);
        }
    }

    public static class TabFragment5 extends Fragment {

        private String teacher5_token ="luzZy37nmveRpTavmzgAmvOemKw1";////intern2계정, 수정필요

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            ImageView imageView_user5 = (ImageView) getView().findViewById(R.id.imageView_user5);
            imageView_user5.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ConnectActivity.class);
                    intent.putExtra("chatroomname", "ToTeacher5");
                    intent.putExtra("receiveruid", teacher5_token);  //intern2계정으로 전송, 수정필요
                    intent.putExtra("uid", "yrSf3cetwGU8PaBWsJ3aZ6kuKFi1");  //uid 수정필요
                    startActivity(intent);
                }
            });

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.tab_fragment_5, container, false);
        }
    }

    public static class TabFragment6 extends Fragment {

        private String teacher6_token ="luzZy37nmveRpTavmzgAmvOemKw1";////intern2계정, 수정필요

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            ImageView imageView_user6 = (ImageView) getView().findViewById(R.id.imageView_user6);
            imageView_user6.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ConnectActivity.class);
                    intent.putExtra("chatroomname", "ToTeacher6");
                    intent.putExtra("receiveruid", teacher6_token);  //intern2계정으로 전송, 수정필요
                    intent.putExtra("uid", "yrSf3cetwGU8PaBWsJ3aZ6kuKFi1");  //uid 수정필요
                    startActivity(intent);
                }
            });

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.tab_fragment_6, container, false);
        }
    }

    public static class TabFragment7 extends Fragment {

        private String teacher7_token ="luzZy37nmveRpTavmzgAmvOemKw1";////intern2계정, 수정필요

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            ImageView imageView_user7 = (ImageView) getView().findViewById(R.id.imageView_user7);
            imageView_user7.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ConnectActivity.class);
                    intent.putExtra("chatroomname", "ToTeacher7");
                    intent.putExtra("receiveruid", teacher7_token);  //intern2계정으로 전송, 수정필요
                    intent.putExtra("uid", "yrSf3cetwGU8PaBWsJ3aZ6kuKFi1");  //uid 수정필요
                    startActivity(intent);
                }
            });

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.tab_fragment_7, container, false);
        }
    }

    public static class TabFragment8 extends Fragment {

        private String teacher8_token ="luzZy37nmveRpTavmzgAmvOemKw1";////intern2계정, 수정필요

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            ImageView imageView_user8 = (ImageView) getView().findViewById(R.id.imageView_user8);
            imageView_user8.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ConnectActivity.class);
                    intent.putExtra("chatroomname", "ToTeacher8");
                    intent.putExtra("receiveruid", teacher8_token);  //intern2계정으로 전송, 수정필요
                    intent.putExtra("uid", "yrSf3cetwGU8PaBWsJ3aZ6kuKFi1");  //uid 수정필요
                    startActivity(intent);
                }
            });

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.tab_fragment_8, container, false);
        }
    }

    public static class TabFragment9 extends Fragment {

        private String teacher9_token ="luzZy37nmveRpTavmzgAmvOemKw1";////intern2계정, 수정필요

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            ImageView imageView_user9 = (ImageView) getView().findViewById(R.id.imageView_user9);
            imageView_user9.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ConnectActivity.class);
                    intent.putExtra("chatroomname", "ToTeacher9");
                    intent.putExtra("receiveruid", teacher9_token);  //intern2계정으로 전송, 수정필요
                    intent.putExtra("uid", "yrSf3cetwGU8PaBWsJ3aZ6kuKFi1");  //uid 수정필요
                    startActivity(intent);
                }
            });

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.tab_fragment_9, container, false);
        }
    }

    public static class TabFragment10 extends Fragment {

        private String teacher10_token ="luzZy37nmveRpTavmzgAmvOemKw1";////intern2계정, 수정필요

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            ImageView imageView_user10 = (ImageView) getView().findViewById(R.id.imageView_user10);
            imageView_user10.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ConnectActivity.class);
                    intent.putExtra("chatroomname", "ToTeacher10");
                    intent.putExtra("receiveruid", teacher10_token);  //intern2계정으로 전송, 수정필요
                    intent.putExtra("uid", "yrSf3cetwGU8PaBWsJ3aZ6kuKFi1");  //uid 수정필요
                    startActivity(intent);
                }
            });

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.tab_fragment_10, container, false);
        }
    }
    public static class TabFragment11 extends Fragment {

        private String teacher11_token ="luzZy37nmveRpTavmzgAmvOemKw1";////intern2계정, 수정필요

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            ImageView imageView_user11 = (ImageView) getView().findViewById(R.id.imageView_user11);
            imageView_user11.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ConnectActivity.class);
                    intent.putExtra("chatroomname", "ToTeacher11");
                    intent.putExtra("receiveruid", teacher11_token);  //intern2계정으로 전송, 수정필요
                    intent.putExtra("uid", "yrSf3cetwGU8PaBWsJ3aZ6kuKFi1");  //uid 수정필요
                    startActivity(intent);
                }
            });

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.tab_fragment_11, container, false);
        }
    }
}
