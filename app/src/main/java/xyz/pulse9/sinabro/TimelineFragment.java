package xyz.pulse9.sinabro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimelineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimelineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimelineFragment extends YouTubePlayerSupportFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData> myDataset;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    YouTubePlayerSupportFragment youTubePlayerFragment;

    public ImageButton fold;
    YouTubePlayerView youtubeView;
    YouTubePlayer youTubePlayer;
    Button button;
    YouTubePlayer.OnInitializedListener listener;



    public TimelineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.timeline_recyclerview);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

//        myDataset = new ArrayList<>();
//        mAdapter = new MyAdapter(myDataset);
//        mRecyclerView.setAdapter(mAdapter);
//        myDataset.add(new MyData("uO4BMId9e0w"));
//        myDataset.add(new MyData("bL54g7RF5hk"));
//        myDataset.add(new MyData("CHoPhkCzdrc"));
//        myDataset.add(new MyData("ivG_NZojm-8"));
//        myDataset.add(new MyData("7bR8TG2HgVA"));




        ImageButton setting_butt = (ImageButton) getView().findViewById(R.id.setting);

        ImageButton notice_butt = (ImageButton) getView().findViewById(R.id.notice);

        setting_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), AppSettingActivity.class);
//                startActivity(intent);
                FirebaseAuth.getInstance().signOut();
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),"Logout Complete", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });
        notice_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),"Coming Soon...", Toast.LENGTH_SHORT);
                toast.show();
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ChatRoomListFragment llf = new ChatRoomListFragment();
//                ft.replace(R.id.flContainer, llf);
//                ft.commit();


//                Calendar now = Calendar.getInstance();
//                DatePickerDialog dpd = DatePickerDialog.newInstance(
//                        (DatePickerDialog.OnDateSetListener) getActivity(),
//                        now.get(Calendar.YEAR), // Initial year selection
//                        now.get(Calendar.MONTH), // Initial month selection
//                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
//                );
//                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
//                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
            }
        });

//        button = (Button)getView().findViewById(R.id.youtubeButton);
//        youtubeView = (YouTubePlayerView)getView().findViewById(R.id.youtubeView);
//        fold = (ImageButton)getView().findViewById(R.id.timeline_tab);
//
//        listener = new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider,
//                                                YouTubePlayer youTubePlayer,
//                                                boolean b) {
//                youTubePlayer.loadVideo("uO4BMId9e0w");
//            }
//            @Override
//            public void onInitializationFailure(
//                    YouTubePlayer.Provider provider,
//                    YouTubeInitializationResult youTubeInitializationResult) {
//            }
//        };
//        button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                youtubeView.initialize("AIzaSyD5DB011LhNQGjoAPqRzqKhuOMPkOf__KE", listener);
//            }
//        });
////        initYouTube();

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimelineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimelineFragment newInstance(String param1, String param2) {
        TimelineFragment fragment = new TimelineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        initYouTube();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }
    private void initYouTube() {
        youTubePlayerFragment= YouTubePlayerSupportFragment.newInstance();
        if (youTubePlayerFragment == null) {
            youTubePlayerFragment = (YouTubePlayerSupportFragment)getChildFragmentManager().findFragmentById(R.id.youtubeView);
            youTubePlayerFragment.initialize("AIzaSyD5DB011LhNQGjoAPqRzqKhuOMPkOf__KE", new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                    Log.i("Detail","YouTube Player onInitializationSuccess");

                    // Don't do full screen
                    player.setFullscreen(false);
                    if (!wasRestored) {
                        youTubePlayer = player;
//                        cueVideoIfNeeded();
                    }
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Log.i("Detail", "Failed: "+youTubeInitializationResult);

                }
            });
        }
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}


class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<MyData> mDataset;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageButton fold;
        YouTubePlayerView youtubeView;
        Button button;
        YouTubePlayer.OnInitializedListener listener;
        String videoCode;


        public ViewHolder(View view)
        {
            super(view);
            button = (Button)view.findViewById(R.id.youtubeButton);
            youtubeView = (YouTubePlayerView)view.findViewById(R.id.youtubeView);
            fold = (ImageButton)view.findViewById(R.id.timeline_tab);

            listener = new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                    YouTubePlayer youTubePlayer,
                                                    boolean b) {
                    youTubePlayer.loadVideo(videoCode);
                }
                @Override
                public void onInitializationFailure(
                        YouTubePlayer.Provider provider,
                        YouTubeInitializationResult youTubeInitializationResult) {
                }
            };
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    youtubeView.initialize("AIzaSyD5DB011LhNQGjoAPqRzqKhuOMPkOf__KE", listener);
                }
            });

            fold.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast toast = Toast.makeText(view.getContext().getApplicationContext(),"Coming Soon...", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<MyData> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timeline_onelayout, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.videoCode=mDataset.get(position).video_code;

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

class MyData{
    public String video_code;

    public MyData(String code){
        this.video_code = code;
    }
}




