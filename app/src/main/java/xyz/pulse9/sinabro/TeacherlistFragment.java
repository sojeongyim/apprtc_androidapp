package xyz.pulse9.sinabro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.provider.FirebaseInitProvider;

public class TeacherlistFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private ImageButton left_arrow;
    private ImageButton right_arrow;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public TeacherlistFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);


        tabLayout = (TabLayout) getView().findViewById(R.id.tab_layout);
        left_arrow =(ImageButton)getView().findViewById(R.id.left_arrow);
        right_arrow =(ImageButton)getView().findViewById(R.id.right_arrow);

        View view1 = getLayoutInflater().inflate(R.layout.customtab, null);
        view1.findViewById(R.id.myicon).setBackgroundResource(R.drawable.user1);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));


        View view2 = getLayoutInflater().inflate(R.layout.customtab, null);
        view2.findViewById(R.id.myicon).setBackgroundResource(R.drawable.user2);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view2));


        View view3 = getLayoutInflater().inflate(R.layout.customtab, null);
        view3.findViewById(R.id.myicon).setBackgroundResource(R.drawable.user3);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view3));

        View view4 = getLayoutInflater().inflate(R.layout.customtab, null);
        view4.findViewById(R.id.myicon).setBackgroundResource(R.drawable.user4);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view4));

//
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user1));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user2));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user3));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user4));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user5));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user6));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user7));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user8));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user9));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user10));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user11));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = (ViewPager) getView().findViewById(R.id.pager);
        final mPagerAdapter adapter = new mPagerAdapter(getFragmentManager(), tabLayout.getTabCount());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        left_arrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacherlist, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
    }

}
