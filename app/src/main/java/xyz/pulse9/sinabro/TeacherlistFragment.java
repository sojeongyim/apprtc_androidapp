package xyz.pulse9.sinabro;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.provider.FirebaseInitProvider;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class TeacherlistFragment extends Fragment {
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private ImageButton left_arrow;
    private ImageButton right_arrow;
    private final int TEACHER_NUM=7;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("users");


    public TeacherlistFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        tabLayout = getView().findViewById(R.id.tab_layout);
        left_arrow = getView().findViewById(R.id.left_arrow);
        right_arrow = getView().findViewById(R.id.right_arrow);

        final mPagerAdapter adapter = new mPagerAdapter(getFragmentManager(), TEACHER_NUM);

        ArrayList<View> views =new ArrayList<>();
        for(int i=0;i<TEACHER_NUM;i++) {
            views.add(i, getLayoutInflater().inflate(R.layout.customtab, null));
            Log.e("sojeong","views.get(i).getId(): "+views.get(i).getId());
            Picasso.get().load(adapter.getImage(i))
                    .transform(new CropCircleTransformation())
                    .into((ImageView) views.get(i).findViewById(R.id.myicon));
            tabLayout.addTab(tabLayout.newTab().setCustomView(views.get(i)));
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mViewPager = (ViewPager) getView().findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                if(mViewPager.getCurrentItem()==0){
                    left_arrow.setVisibility(View.INVISIBLE);
                    right_arrow.setVisibility(View.VISIBLE);
                }else if(mViewPager.getCurrentItem()==adapter.getCount()-1){
                    left_arrow.setVisibility(View.VISIBLE);
                    right_arrow.setVisibility(View.INVISIBLE);
                }else{
                    left_arrow.setVisibility(View.VISIBLE);
                    right_arrow.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPage = mViewPager.getCurrentItem();

                mViewPager.setCurrentItem(currentPage - 1, true);

            }
        });
        right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPage = mViewPager.getCurrentItem();

                mViewPager.setCurrentItem(currentPage + 1, true);

            }
        });


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
