package xyz.pulse9.sinabro;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Vector;

public class TimelineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private static Animation clickanimation;
    private Vector<TimelineData> youtubeVideos = new Vector<TimelineData>();

    // TODO: Rename and change types of parameters
    public TimelineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        clickanimation = AnimationUtils.loadAnimation(getContext(), R.anim.clickanimaiton);

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.timeline_recyclerview);
        mRecyclerView.setHasFixedSize(false);
//        mRecyclerView.scrollBy(0,-3000);
//        mRecyclerView.scrollTo(0,0);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Resources res = getResources();
        String[] youtubeCodes = res.getStringArray(R.array.youtubeCode);
        int[] drawabb = {R.drawable.kr1, R.drawable.kr2};
        youtubeVideos.add(new TimelineData(drawabb));

        for (int i = 0; i < youtubeCodes.length; i++) {
            youtubeVideos.add(new TimelineData(youtubeCodes[i]));
        }
        TimelineAdapter timelineAdapter = new TimelineAdapter(youtubeVideos);
        mRecyclerView.setAdapter(timelineAdapter);
        ImageButton setting_butt = (ImageButton) getView().findViewById(R.id.setting);
        ImageButton notice_butt = (ImageButton) getView().findViewById(R.id.notice);
        setting_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(clickanimation);
                Intent intent = new Intent(getActivity(), MySettingsActivity.class);
                startActivity(intent);
            }
        });
        notice_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(clickanimation);
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Coming Soon...", Toast.LENGTH_SHORT);
                toast.show();
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
//        mRecyclerView.scrollBy(0,-3000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timeline, container, false);
        return v;
    }
}