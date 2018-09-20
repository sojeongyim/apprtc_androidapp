package xyz.pulse9.sinabro;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimelineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimelineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimelineFragment extends Fragment{
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

    public TimelineFragment() {
        // Required empty public constructor
    }
//
//    @Override
//    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
//        String time = "You picked the following time: "+hourOfDay+"h"+minute+"m"+second;
//        timeTextView.setText(time);
//    }


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
        myDataset = new ArrayList<>();
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        myDataset.add(new MyData("I’m Korean-American living in Seoul.  But a few years ago a serendipitous event inspired me to get back into Korea where my roots are. As a Korean-American, I learned Korean as second language so that I understand how difficult it is to get fluent in Korean.  Such a hard-earning skill.\n" +
                "I have better understanding of how the language can be taught as compared to the native speakers. (No worries, I’m super fluent in Korean!)\n" +
                "If you believe that I can be of help to you or if you would like to learn more about me.\n" +
                "Just leave me a message.",R.drawable.timeline01));
        myDataset.add(new MyData("I’m a huge people-person and spent several years in overseas like Singapore and Hong Kong. There is nothing greater than finding opportunities for smart people to do awesome things, and teaching in Sina-bro is a fantastic process that I feel lucky to participate in.\n" +
                "When I’m not working, I am usually with my friends hanging out, who love to run and exercise even more than I do. I’m much a kid at heart, love to cook, watch football, play guitar, and travel whenever I can.\n" +
                "I love meeting new people and get inspired by them, so please feel free to say hello and share a story with me.I’m much thrilled to get a call from who I can share my story.\n" +
                "Let’s get to know each other!",R.drawable.timeline02));
        myDataset.add(new MyData("It's no surprise that I am tutoring in Sina-bro which rewards me for helping people realize their potential and setting them on their way to achieving great things.\n" +
                "After 10+ years in teaching industry, I get as excited today as I did back then when seeing both myself and students prosper.\n" +
                "Truly partnering with my students to help them think differently and coaching the talent I work with to attain the unattainable is what motivates me to continuously improve in this ever evolving industry.\n"+
                "I'm interested in hearing your stories.",R.drawable.timeline03)); //중간자름
        myDataset.add(new MyData("I love traveling the world and eating my way through the places I visit. In my spare time, I’m searching for travel deals or hang out with the new friends from Sina-bro.\n" +
                "I’m fluent in English, and am always looking to brush up my language skills over coffee or drinks. If you challenge me to Say Wars trivia, I will win. I’m also obsessed with Music. Connect with me for networking and more.\n",R.drawable.timeline04));
        myDataset.add(new MyData("One of my favorite things is connecting with people who have a passion for working in a self-managed organization and who genuinely love wowing their internal and external customers. For me, it’s all about discovering people’s dreams and matching them with careers that will allow them to grow and do their very best work, Getting to be yourself both inside and outside of work is where it’s at!\n" +
                "Outside of the office, I’m committed to education and tutor at Sina-bro.\n" +
                "I can be contacted directly at 00@gmail.com, text me!\n",R.drawable.timeline05));
//        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/HelveticaNeueLTPro-Lt.otf"); //asset > fonts 폴더 내 폰트파일 적용
//        myDataset.(typeFace);



        ImageButton setting_butt = (ImageButton) getView().findViewById(R.id.setting);

        ImageButton notice_butt = (ImageButton) getView().findViewById(R.id.notice);

        setting_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AppSettingActivity.class);
                startActivity(intent);





            }
        });
        notice_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ChatRoomListFragment llf = new ChatRoomListFragment();
                ft.replace(R.id.flContainer, llf);
                ft.commit();
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



    }
//    @Override
//    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//        String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
////        dateTextView.setText(date);
//        Log.e("sojeong")
//    }
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline, container, false);
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
        public ImageView mImageView;
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView)view.findViewById(R.id.image);
            mTextView = (TextView)view.findViewById(R.id.textview);
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
        holder.mTextView.setText(mDataset.get(position).text);
        holder.mImageView.setImageResource(mDataset.get(position).img);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

class MyData{
    public String text;
    public int img;
    public MyData(String text, int img){
        this.text = text;
        this.img = img;
    }
}




