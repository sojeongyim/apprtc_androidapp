package xyz.pulse9.sinabro;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TimelineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private static Animation clickanimation;
    private List<TimelineData> youtubeVideos;
    private TimelineAdapter timelineAdapter;
    protected Handler handler;
    private FirebaseUser curuser;

    private ImageButton setting_butt;
    private ImageButton notice_butt;

    // TODO: Rename and change types of parameters
    public TimelineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        clickanimation = AnimationUtils.loadAnimation(getContext(), R.anim.clickanimaiton);
        curuser = FirebaseAuth.getInstance().getCurrentUser();
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.timeline_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        youtubeVideos = new ArrayList<TimelineData>();
        loadData();
        setButtons();
        if(curuser==null)
        {
            setting_butt.setVisibility(View.GONE);
        }
        handler = new Handler();
        timelineAdapter = new TimelineAdapter(youtubeVideos, mRecyclerView);
        mRecyclerView.setAdapter(timelineAdapter);

        timelineAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                youtubeVideos.add(null);
                timelineAdapter.notifyItemInserted(youtubeVideos.size() - 1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //   remove progress item
                        youtubeVideos.remove(youtubeVideos.size() - 1);
                        timelineAdapter.notifyItemRemoved(youtubeVideos.size());
                        //add items one by one
                        int start = youtubeVideos.size();
                        int end = start + 5;

                        for (int i = start + 1; i <= end; i++) {
                            addMore(i);
//                            youtubeVideos.add(new Student("Student " + i, "AndroidStudent" + i + "@gmail.com"));
                            timelineAdapter.notifyItemInserted(youtubeVideos.size());
                        }
                        timelineAdapter.setLoaded();
                    }
                }, 2000);

            }
        });


    }

    private void addMore(int i ) {
//        youtubeVideos.add(youtubeVideos.get(1));
        Log.d("JANGMIN", "Something is added here");
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timeline, container, false);
        return v;
    }
    public void loadData()
    {
        Resources res = getResources();
        String[] youtubeCodes = res.getStringArray(R.array.youtubeCode);
        int[] drawabb = {R.drawable.kr1, R.drawable.kr2};
        youtubeVideos.add(new TimelineData(drawabb,"AFXER"));
//        new getYoutubeData().execute();


        for (int i = 0; i < 5; i++) {
            youtubeVideos.add(new TimelineData(youtubeCodes[i]));
        }
    }
    public void setButtons() {
        setting_butt = (ImageButton) getView().findViewById(R.id.setting);
        notice_butt = (ImageButton) getView().findViewById(R.id.notice);
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

    private class getYoutubeData extends AsyncTask {
        @Override
        protected void onPostExecute(Object o) {

            super.onPostExecute(o);
            Log.d("JANGMIN", "AsyncTask Ended");
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("video");

            final int cnt = 3;
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int cnt = 0;
                    Log.d("JANGMIN", " Youtube is Loaded");
                    for(DataSnapshot tmp : dataSnapshot.getChildren())
                    {
                        if(cnt <3) {
                            cnt++;
                            youtubeVideos.add(new TimelineData(tmp.getValue().toString()));
                            Log.d("JANGMIN", " Youtube Added");
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            return null;
        }
    }

}