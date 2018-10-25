package xyz.pulse9.sinabro;

import android.content.Context;
import android.content.Intent;
import android.mtp.MtpStorageInfo;
import android.net.Uri;
import android.os.AsyncTask;
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


import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.common.api.Api;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


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
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData> myDataset;

    // TODO: Rename and change types of parameters
    private OnFragmentInteractionListener mListener;
    YouTubePlayerSupportFragment youTubePlayerFragment;



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

        myDataset = new ArrayList<>();
        mAdapter = new MyAdapter(myDataset,this);
        mRecyclerView.setAdapter(mAdapter);
        myDataset.add(new MyData("uO4BMId9e0w"));
        myDataset.add(new MyData("bL54g7RF5hk"));
        myDataset.add(new MyData("CHoPhkCzdrc"));
        myDataset.add(new MyData("ivG_NZojm-8"));
        myDataset.add(new MyData("7bR8TG2HgVA"));

        GetYoutubeInfo getYoutubeInfo = new GetYoutubeInfo();
        getYoutubeInfo.execute();
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.GET,
//                "https://www.googleapis.com/youtube/v3/videos?id=7bR8TG2HgVA&part=snippet&key=AIzaSyD5DB011LhNQGjoAPqRzqKhuOMPkOf__KE",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        //***
//                        Log.d("sojeongjson","result: "+response);
//                        Toast.makeText(getActivity(),"success:"+response,Toast.LENGTH_SHORT);
//
//                        //**
//                        try {
//
//                            JSONObject jsonObject = new JSONObject(response);
//                            JSONArray jsonArray = jsonObject.getJSONArray("items");
//
//                            JSONObject object = jsonArray.getJSONObject(0);
//                            JSONObject snippet = object.getJSONObject("snippet");
//
//                            String title = snippet.getString("title");
//
//
//                            Log.d("stuff: ", "" + title);
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity(), "error: "+error.getMessage(), Toast.LENGTH_LONG).show();
//                        Log.e("sojeong","error: "+error);
//                    }
//                });
//
//        // Request (if not using Singleton [RequestHandler]
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        requestQueue.add(stringRequest);
//
//        // Request with RequestHandler (Singleton: if created)
////            RequestHandler.getInstance(itemView.getContext()).addToRequestQueue(stringRequest);

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
            }
        });


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
        View v=inflater.inflate(R.layout.fragment_timeline, container, false);

        // Inflate the layout for this fragment

        return v;
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
    private static Fragment mfragment;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageButton fold;
        String videoCode;
        String ApiToken = "AIzaSyD5DB011LhNQGjoAPqRzqKhuOMPkOf__KE";

        public ViewHolder(View view)
        {
            super(view);
            fold = (ImageButton)view.findViewById(R.id.timeline_tab);
            YouTubePlayerSupportFragment youTubePlayerFragment= YouTubePlayerSupportFragment.newInstance();
            FragmentTransaction transaction = mfragment.getChildFragmentManager().beginTransaction();
            transaction.add(R.id.youtubeView, youTubePlayerFragment).commit();

            youTubePlayerFragment.initialize(ApiToken, new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                    Log.i("youtube","YouTube Player onInitializationSuccess");

//                    player.setFullscreen(false);
                    if (!wasRestored) {
                        player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                        player.loadVideo(videoCode);
                        player.play();
//                        GetVideoInfo getVideoInfo = new GetVideoInfo();
//                        getVideoInfo.execute();


                    }
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    String errorMessage = youTubeInitializationResult.toString();
                    Toast.makeText(itemView.getContext(), errorMessage, Toast.LENGTH_LONG).show();
                    Log.d("errorMessage:", errorMessage);

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

//        public class GetVideoInfo extends AsyncTask<Void,Void,Void>{
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                // volley
//                StringRequest stringRequest = new StringRequest(
//                        Request.Method.GET,
//                        "https://www.googleapis.com/youtube/v3/videos?id=7bR8TG2HgVA&part=snippet&key=AIzaSyD5DB011LhNQGjoAPqRzqKhuOMPkOf__KE",
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//
//                                //***
//                                Log.d("sojeongjson","result: "+response);
//                                Toast.makeText(itemView.getContext(),"success:"+response,Toast.LENGTH_SHORT);
//
//                                //**
//                                try {
//
//                                    JSONObject jsonObject = new JSONObject(response);
//                                    JSONArray jsonArray = jsonObject.getJSONArray("items");
//
//                                    JSONObject object = jsonArray.getJSONObject(0);
//                                    JSONObject snippet = object.getJSONObject("snippet");
//
//                                    String title = snippet.getString("title");
//
//
//                                    Log.d("stuff: ", "" + title);
//
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(itemView.getContext(), "error: "+error.getMessage(), Toast.LENGTH_LONG).show();
//                                Log.e("sojeong","error: "+error);
//                            }
//                        });
//
//                // Request (if not using Singleton [RequestHandler]
//                RequestQueue requestQueue = Volley.newRequestQueue(itemView.getContext());
//                requestQueue.add(stringRequest);
//
//                // Request with RequestHandler (Singleton: if created)
////            RequestHandler.getInstance(itemView.getContext()).addToRequestQueue(stringRequest);
//
//                return null;
//            }
//        }

//        public void getVideoInfo(){
//
//            // volley
//            StringRequest stringRequest = new StringRequest(
//                    Request.Method.GET,
//                    "https://www.googleapis.com/youtube/v3/videos?id=" + videoCode + "&key=" +
//                            ApiToken +
//                            "&part=snippet,contentDetails,statistics,status",
//                    new com.android.volley.Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//
//                            //***
//                            Log.d("sojeongjson","result: "+response);
//                            Toast.makeText(itemView.getContext(),"success:"+response,Toast.LENGTH_SHORT);
//
//                            //**
//                            try {
//
//                                JSONObject jsonObject = new JSONObject(response);
//                                JSONArray jsonArray = jsonObject.getJSONArray("items");
//
//                                JSONObject object = jsonArray.getJSONObject(0);
//                                JSONObject snippet = object.getJSONObject("snippet");
//
//                                String title = snippet.getString("title");
//
//
//                                Log.d("stuff: ", "" + title);
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    },
//                    new com.android.volley.Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(itemView.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }){};
//
//            // Request (if not using Singleton [RequestHandler]
//             RequestQueue requestQueue = Volley.newRequestQueue(itemView.getContext());
//             requestQueue.add(stringRequest);
//
//            // Request with RequestHandler (Singleton: if created)
////            RequestHandler.getInstance(itemView.getContext()).addToRequestQueue(stringRequest);
//
//
//        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<MyData> myDataset, Fragment fragment) {
        mDataset = myDataset;
        mfragment = fragment;

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




class GetYoutubeInfo extends AsyncTask<Void, Void, Void> {

    String errorString = null;

    @Override

    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void result) {

        super.onPostExecute(result);



        Log.d("sojeong", "result - " + result);

        if (result == null) {
            Log.d("sojeong", "error - " + errorString);

        } else {
            Log.d("sojeong", "success - " + result);

        }

    }

    @Override
    protected Void doInBackground(Void... params) {
        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall("https://www.googleapis.com/youtube/v3/videos?id=7bR8TG2HgVA&part=snippet&key=AIzaSyD5DB011LhNQGjoAPqRzqKhuOMPkOf__KE");
        String youtube="";
        //  Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("items");

                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    youtube = c.getString("title");

                }

            } catch (final JSONException e) {
                // Log.e(TAG, "Json parsing error: " + e.getMessage());


            }
        } else {
            //Log.e(TAG, "Couldn't get json from server.");


        }
        Log.e("sojeong","youtube:"+youtube);
        return null;




//        StringRequest stringRequest = new StringRequest(
//                Request.Method.GET,
//                "https://www.googleapis.com/youtube/v3/videos?id=7bR8TG2HgVA&part=snippet&key=AIzaSyD5DB011LhNQGjoAPqRzqKhuOMPkOf__KE",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        //***
//                        Log.e("sojeong","result: "+response);
//
//                        //**
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("sojeong","error: "+error);
//                    }
//                });

////        String searchKeyword = params[0];
//
//        String serverURL = "https://www.googleapis.com/youtube/v3/videos?id=7bR8TG2HgVA&part=snippet&key=AIzaSyD5DB011LhNQGjoAPqRzqKhuOMPkOf__KE";
//
//        //String postParameters = "keyword=" + searchKeyword;
//
//
//        StringBuffer buffer = new StringBuffer();
//
//
//        try {
//
//            URL url = new URL(serverURL);
//
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//
//            httpURLConnection.setReadTimeout(5000);
//
//            httpURLConnection.setConnectTimeout(5000);
//
//            httpURLConnection.setRequestMethod("GET");
//
//            httpURLConnection.setDoInput(true);
//
//            httpURLConnection.connect();
//
//            OutputStream outputStream = httpURLConnection.getOutputStream();
//
//            outputStream.write(buffer.toString().getBytes("UTF-8"));
//            //요기 부분이 서버로 값을 전송하는 부분
//
//            outputStream.flush();
//
//            outputStream.close();
//
//            int responseStatusCode = httpURLConnection.getResponseCode();
//
//            Log.d("sojeong", "response code - " + responseStatusCode);
//
//            InputStream inputStream;
//
//            if (responseStatusCode == HttpURLConnection.HTTP_OK) {
//
//                inputStream = httpURLConnection.getInputStream();
//
//            } else {
//
//                inputStream = httpURLConnection.getErrorStream();
//
//            }
//
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
//
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//            StringBuilder sb = new StringBuilder();
//
//            String line;
//
//            while ((line = bufferedReader.readLine()) != null) {
//
//                sb.append(line);
//            }
//
//            bufferedReader.close();
//
//            return sb.toString().trim();
//
//        } catch (Exception e) {
//
//            Log.d("sojeong", "InsertData: Error ", e);
//
//            errorString = e.toString();
//
//            return null;
//
//        }

//        return null;
    }
}