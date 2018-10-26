//package xyz.pulse9.sinabro;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//import com.google.android.youtube.player.YouTubeBaseActivity;
//import com.google.android.youtube.player.YouTubeInitializationResult;
//import com.google.android.youtube.player.YouTubePlayer;
//import com.google.android.youtube.player.YouTubePlayerView;
//import com.google.firebase.auth.FirebaseAuth;
//
//import java.util.ArrayList;
//
///**
// * Created by pulse on 2018. 10. 16..
// */
//
//public class TestActivity extends YouTubeBaseActivity {
//    private RecyclerView mRecyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager mLayoutManager;
//    private ArrayList<MyData> myDataset;
//    YouTubePlayerView youtubeView;
//    Button button;
//    YouTubePlayer.OnInitializedListener listener;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_timeline);
//
//        mRecyclerView = (RecyclerView)findViewById(R.id.timeline_recyclerview);
//
//
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        myDataset = new ArrayList<>();
//        mAdapter = new MyAdapter(myDataset);
//        mRecyclerView.setAdapter(mAdapter);
//        myDataset.add(new MyData("uO4BMId9e0w"));
//        myDataset.add(new MyData("bL54g7RF5hk"));
//        myDataset.add(new MyData("CHoPhkCzdrc"));
//        myDataset.add(new MyData("ivG_NZojm-8"));
//        myDataset.add(new MyData("7bR8TG2HgVA"));
//        // specify an adapter (see also next example)
//
//
//
//        ImageButton setting_butt = (ImageButton)findViewById(R.id.setting);
//
//        ImageButton notice_butt = (ImageButton) findViewById(R.id.notice);
//
//        setting_butt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(getActivity(), AppSettingActivity.class);
////                startActivity(intent);
//                FirebaseAuth.getInstance().signOut();
//                Toast toast = Toast.makeText(getApplicationContext(),"Logout Complete", Toast.LENGTH_SHORT);
//                toast.show();
//                Intent intent = new Intent(TestActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//
//            }
//        });
//        notice_butt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast toast = Toast.makeText(getApplicationContext(),"Coming Soon...", Toast.LENGTH_SHORT);
//                toast.show();
////                FragmentManager fm = getFragmentManager();
////                FragmentTransaction ft = fm.beginTransaction();
////                ChatRoomListFragment llf = new ChatRoomListFragment();
////                ft.replace(R.id.flContainer, llf);
////                ft.commit();
//
//
////                Calendar now = Calendar.getInstance();
////                DatePickerDialog dpd = DatePickerDialog.newInstance(
////                        (DatePickerDialog.OnDateSetListener) getActivity(),
////                        now.get(Calendar.YEAR), // Initial year selection
////                        now.get(Calendar.MONTH), // Initial month selection
////                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
////                );
////                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
////                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
//            }
//        });
//    }
//}
//
