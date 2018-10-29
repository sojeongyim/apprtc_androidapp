package xyz.pulse9.sinabro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.mtp.MtpStorageInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;
import java.util.Vector;


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
//    private ArrayList<MyData> myDataset;

    private Vector<YouTubeVideos> youtubeVideos = new Vector<YouTubeVideos>();

    // TODO: Rename and change types of parameters
    private OnFragmentInteractionListener mListener;


    public TimelineFragment() {
        // Required empty public constructor
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.timeline_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)

//        myDataset = new ArrayList<>();
//        mAdapter = new MyAdapter(myDataset,this);
//        mRecyclerView.setAdapter(mAdapter);
//        myDataset.add(new MyData("uO4BMId9e0w"));
//        myDataset.add(new MyData("CHoPhkCzdrc"));
//        myDataset.add(new MyData("ivG_NZojm-8"));
//        myDataset.add(new MyData("7bR8TG2HgVA"));
        youtubeVideos.add( new YouTubeVideos("pjVdCUxvfXA"));
        youtubeVideos.add( new YouTubeVideos("6__TKYYJAkI"));
        youtubeVideos.add( new YouTubeVideos("69eq1zD5oWM"));
        youtubeVideos.add( new YouTubeVideos("7bR8TG2HgVA"));
        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);
        mRecyclerView.setAdapter(videoAdapter);

        ImageButton setting_butt = (ImageButton) getView().findViewById(R.id.setting);
        ImageButton notice_butt = (ImageButton) getView().findViewById(R.id.notice);
        setting_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MySettingsActivity.class);
                startActivity(intent);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_timeline, container, false);
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
class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    List<YouTubeVideos> youtubeVideoList;
    public VideoAdapter() {
    }
    public VideoAdapter(List<YouTubeVideos> youtubeVideoList) {
        this.youtubeVideoList = youtubeVideoList;
    }
    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.timeline_onelayout, parent, false);
        return new VideoViewHolder(view);
    }
    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
//        GridLayoutManager.LayoutParams layoutParams=(GridLayoutManager.LayoutParams)holder.itemView.getLayoutParams();
//        layoutParams.height=layoutParams.width;
//        holder.itemView.requestLayout();

        GetYoutubeInfo getVideoInfo = new GetYoutubeInfo(youtubeVideoList.get(position).getVideoCode(),holder);
        getVideoInfo.execute();

        holder.videoWeb.loadData(youtubeVideoList.get(position).getVideoUrl(), "text/html" , "utf-8" );
    }

    class GetYoutubeInfo extends AsyncTask<String, String, String > {
        String ApiToken = "AIzaSyD5DB011LhNQGjoAPqRzqKhuOMPkOf__KE";
        String videocode="";
        String Title="";
        String description="";
        String tag="";
        String channelId="";
        String channelName="";
        String channelImgUrl="";
        String likeCount="";
        Bitmap UrlBitmap=null;
        VideoViewHolder holder;


        GetYoutubeInfo(String VideoUrl,VideoViewHolder holder){
            this.videocode=VideoUrl;
            this.holder=holder;
        }
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            holder.TitleText.setText(Title);
            holder.descriptionText.setText(description);
            holder.tagText.setText(tag);
            holder.channel_name.setText(channelName);
            holder.likeCountText.setText(likeCount);
//            holder.channel_img.setImageBitmap(UrlBitmap);

            Picasso.get().load(channelImgUrl)
                    .transform(new CropCircleTransformation())
                    .into(holder.channel_img);


            makeTextViewResizable(holder.descriptionText, 3, "More", true);
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler videoHttp = new HttpHandler();
            String videoInfo = videoHttp.makeServiceCall("https://www.googleapis.com/youtube/v3/videos?id="+videocode+"&part=snippet,statistics&key="+ApiToken);

            if (videoInfo != null) {
                try {
                    JSONObject videoJson = new JSONObject(videoInfo);
                    JSONArray items = videoJson.getJSONArray("items");
                    JSONObject item = items.getJSONObject(0);
                    String snippet= item.getString("snippet");
                    Log.e("sojeong","snippet : "+snippet);

                    JSONObject snippetOB = new JSONObject(snippet);

                    if(snippetOB.has("title")) {
                        Title = snippetOB.getString("title");
                    }

                    if(snippetOB.has("description")) {
                        description = snippetOB.getString("description");
                    }

                    if(snippetOB.has("tags")) {
                        tag = snippetOB.getString("tags");
                        String[] tagparsing = tag.split(",");
                        for(int i=0;i<tagparsing.length;i++){
                            tagparsing[i].replaceAll("\"","");
                            tagparsing[i]="#"+tagparsing[i];
                        }
                    }

                    if(snippetOB.has("statistics")){
                        String statistics = snippetOB.getString("statistics");
                        JSONObject statisticsOB = new JSONObject(statistics);
                        if(statisticsOB.has("likeCount")) {
                            likeCount = statisticsOB.getString("likeCount");
                        }
                    }

                    channelId = snippetOB.getString("channelId");

                } catch (final JSONException e) {
                    Log.e("sojeong", "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e("sojeong", "Couldn't get json from server.");
            }

            HttpHandler channelHttp = new HttpHandler();
            String channelInfo = channelHttp.makeServiceCall("https://www.googleapis.com/youtube/v3/channels?id="+channelId+"&part=snippet&key="+ApiToken);
            if (channelInfo != null) {
                try {
                    JSONObject channelJson = new JSONObject(channelInfo);
                    JSONArray items = channelJson.getJSONArray("items");
                    JSONObject item = items.getJSONObject(0);
                    String snippet= item.getString("snippet");
                    Log.e("sojeong","snippet : "+snippet);

                    JSONObject snippetOB = new JSONObject(snippet);
                    channelName=snippetOB.getString("title");

                    String thumbnails_to_url=snippetOB.getString("thumbnails");
                    JSONObject thumnail_parsing = new JSONObject(thumbnails_to_url);
                    thumbnails_to_url=thumnail_parsing.getString("default");
                    thumnail_parsing=new JSONObject(thumbnails_to_url);
                    channelImgUrl= thumnail_parsing.getString("url");
                    Log.e("sojeong","channel_url: "+channelImgUrl);
//                    try{
//                        InputStream in = new java.net.URL(channelImgUrl).openStream();
//                        UrlBitmap = BitmapFactory.decodeStream(in);
//                    }catch(Exception e){
//                        Log.e("sojeong","channel bitmap error: "+e.getMessage());
//                        e.printStackTrace();
//                    }

                } catch (final JSONException e) {
                    Log.e("sojeong", "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e("sojeong", "Couldn't get json from server.");
            }

            return null;

        }
    }

    @Override
    public int getItemCount() {
        return youtubeVideoList.size();
    }
    public class VideoViewHolder extends RecyclerView.ViewHolder{
        WebView videoWeb;
        ImageView channel_img;
        TextView channel_name;
        ImageButton fold;
        TextView likeCountText;
        TextView TitleText;
        TextView descriptionText;
        TextView tagText;

        public VideoViewHolder(View itemView) {
            super(itemView);
            videoWeb = (WebView) itemView.findViewById(R.id.youtubeView);
            channel_img=(ImageView)itemView.findViewById(R.id.channel_img);
            channel_name=(TextView)itemView.findViewById(R.id.channel_name);
            fold = (ImageButton)itemView.findViewById(R.id.timeline_tab);
            likeCountText = (TextView)itemView.findViewById(R.id.likeCount);
            TitleText = (TextView)itemView.findViewById(R.id.title);
            descriptionText = (TextView)itemView.findViewById(R.id.description);
            tagText = (TextView)itemView.findViewById(R.id.tag);
            videoWeb.getSettings().setJavaScriptEnabled(true);
            videoWeb.setWebChromeClient(new WebChromeClient() {
            } );

        }

    }
    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                String text;
                int lineEndIndex;
                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    lineEndIndex = tv.getLayout().getLineEnd(0);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else {
                    lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                }
                tv.setText(text);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                tv.setText(
                        addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                viewMore), TextView.BufferType.SPANNABLE);
            }
        });

    }
    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    tv.setLayoutParams(tv.getLayoutParams());
                    tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                    tv.invalidate();
                    if (viewMore) {
//                        makeTextViewResizable(tv, -1, "", false);
                    } else {
                        makeTextViewResizable(tv, 3, "More", true);
                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

}
class YouTubeVideos {
    String videoUrl;
    String videoCode;
    public YouTubeVideos() {
    }
    public YouTubeVideos(String videoCode) {
        this.videoCode=videoCode;
        this.videoUrl = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"+videoCode+"\" frameborder=\"0\" allowfullscreen></iframe>";
    }
    public String getVideoUrl() {
        return videoUrl;
    }
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    public String getVideoCode() {
        return videoCode;
    }
}