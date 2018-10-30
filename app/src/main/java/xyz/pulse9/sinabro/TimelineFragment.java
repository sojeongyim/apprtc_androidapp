package xyz.pulse9.sinabro;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class TimelineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static Animation clickanimation;
//    private ArrayList<MyData> myDataset;

    private Vector<Data> youtubeVideos = new Vector<Data>();

    // TODO: Rename and change types of parameters
    private OnFragmentInteractionListener mListener;


    public TimelineFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        clickanimation= AnimationUtils.loadAnimation(getContext(),R.anim.clickanimaiton);

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.timeline_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)

        Resources res = getResources();
        String[] youtubeCodes= res.getStringArray(R.array.youtubeCode);
        int[] drawabb = {R.drawable.kr1,R.drawable.kr2};
        youtubeVideos.add(new Data(drawabb));

        for(int i=0;i<youtubeCodes.length;i++){
            youtubeVideos.add( new Data(youtubeCodes[i]));
        }
        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);
        mRecyclerView.setAdapter(videoAdapter);

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
    List<Data> youtubeVideoList;
    private NetworkInfo activeNetwork;
    public VideoAdapter() {
    }
    public VideoAdapter(List<Data> youtubeVideoList) {
        this.youtubeVideoList = youtubeVideoList;
    }
    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
//        if(viewType==1){
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_onelayout_cardnews, parent, false);
//        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_onelayout_youtube, parent, false);
//        }
        return new VideoViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return youtubeVideoList.get(position).getType();
    }

    @Override
    public void onBindViewHolder(final VideoViewHolder holder, int position) {
        final String current_videoCode = youtubeVideoList.get(position).getVideoCode();
        final Animation clickanimation=AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.clickanimaiton);

        holder.timeline_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(clickanimation);
                Toast.makeText(holder.itemView.getContext(),"Coming Soon...",Toast.LENGTH_SHORT).show();
            }
        });

        ConnectivityManager cm = (ConnectivityManager)holder.itemView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork!=null) {
            if(youtubeVideoList.get(position).getVideoCode()!=null) {
                holder.videoWeb.getSettings().setJavaScriptEnabled(true);
                holder.videoWeb.setWebChromeClient(new WebChromeClient() {
                } );
                GetYoutubeInfo getVideoInfo = new GetYoutubeInfo(current_videoCode, holder);
                getVideoInfo.execute();
                holder.videoWeb.setVisibility(View.VISIBLE);
                holder.videoWeb.loadData(youtubeVideoList.get(position).getVideoUrl(), "text/html", "utf-8");

                holder.timeLineDB.child(current_videoCode).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.likeCountText.setText(Long.toString(dataSnapshot.getChildrenCount()));
                        Log.e("sojeong", "curser: " + holder.curuser);
                        Log.e("sojeong", "uid: " + holder.uid);
                        Log.e("sojeong", "getvalue: " + dataSnapshot.child(holder.uid).getValue());
                        if (holder.curuser != null && dataSnapshot.child(holder.uid).getValue() != null) {
                            holder.heart_check.setVisibility(View.VISIBLE);
                            holder.heart.setVisibility(View.INVISIBLE);
                        } else {
                            holder.heart_check.setVisibility(View.INVISIBLE);
                            holder.heart.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                holder.heart.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (holder.curuser != null) {
                            holder.timeLineDB.child(current_videoCode).child(holder.uid).setValue("1");
                        } else {
                            view.startAnimation(clickanimation);
                            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                            builder.setTitle("Sinabro");
                            builder.setMessage("Please Sign up this beautiful app! ");
                            builder.setNegativeButton("ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            // 다이얼로그를 취소한다
                                            dialog.cancel();
                                        }
                                    });
                            builder.show();
                        }

                    }
                });
                holder.heart_check.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        holder.timeLineDB.child(current_videoCode).child(holder.uid).removeValue();
                    }
                });
            }else{
                CardnewsAdapter adapter = new CardnewsAdapter(((AppCompatActivity)holder.itemView.getContext()).getSupportFragmentManager());
                holder.cardnewspager.setAdapter(adapter);
//                holder.cardimage.setImageResource(youtubeVideoList.get(position).getDrawables());
                holder.cardnewspager.setVisibility(View.VISIBLE);
            }


        }else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(holder.itemView.getContext());
            alertDialogBuilder.setTitle("internet disconnected");

            alertDialogBuilder
                    .setMessage("Please connect to the internet")
                    .setCancelable(false)
                    .setNegativeButton("ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    // 다이얼로그를 취소한다
                                    dialog.cancel();
                                }
                            });

            // 다이얼로그 생성
            AlertDialog alertDialog = alertDialogBuilder.create();

            // 다이얼로그 보여주기
            alertDialog.show();
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{
        WebView videoWeb;
        ImageView channel_img;
        TextView channel_name;
        TextView likeCountText;
        TextView TitleText;
        TextView descriptionText;
        TextView tagText;
        ImageButton heart;
        ImageButton heart_check;
        ImageButton timeline_tab;

        String uid="";
        FirebaseUser curuser;
        DatabaseReference timeLineDB;
        ViewPager cardnewspager;
//        ImageView cardimage;


        public VideoViewHolder(View itemView) {
            super(itemView);

            curuser = FirebaseAuth.getInstance().getCurrentUser();
            if(curuser!=null) {
                uid = curuser.getUid();
            }
            Log.e("sojeong","curser: "+curuser);
            Log.e("sojeong","uid: "+uid);
            timeLineDB= FirebaseDatabase.getInstance().getReference("timeline");

            videoWeb = (WebView) itemView.findViewById(R.id.youtubeView);
            channel_img=(ImageView)itemView.findViewById(R.id.channel_img);
            channel_name=(TextView)itemView.findViewById(R.id.channel_name);
            likeCountText = (TextView)itemView.findViewById(R.id.likeCount);
            TitleText = (TextView)itemView.findViewById(R.id.title);
            descriptionText = (TextView)itemView.findViewById(R.id.description);
            tagText = (TextView)itemView.findViewById(R.id.tag);
            heart = (ImageButton) itemView.findViewById(R.id.heart);
            heart_check = (ImageButton)itemView.findViewById(R.id.heart_check);
            timeline_tab=(ImageButton)itemView.findViewById(R.id.timeline_tab);

            cardnewspager = (ViewPager)itemView.findViewById(R.id.cardnews_pager);



//            cardimage=(ImageView)itemView.findViewById(R.id.cardnews);



        }

    }

    class GetYoutubeInfo extends AsyncTask<String, String, String > {
        String ApiToken = "AIzaSyD5DB011LhNQGjoAPqRzqKhuOMPkOf__KE";
        String videocode="";
        String Title="";
        String description="";
        String newtag="";
        String channelId="";
        String channelName="";
        String channelImgUrl="";
        String likeCount="";
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
            holder.tagText.setText(newtag);
            holder.channel_name.setText(channelName);
//            holder.likeCountText.setText(likeCount);




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
                    String statistic = item.getString("statistics");
                    Log.e("sojeong","snippet : "+snippet);
                    Log.e("sojeong","statistic : "+statistic);

                    JSONObject snippetOB = new JSONObject(snippet);
                    JSONObject statisticOB = new JSONObject(statistic);
                    if(snippetOB.has("title")) {
                        Title = snippetOB.getString("title");
                    }

                    if(snippetOB.has("description")) {
                        description = snippetOB.getString("description");
                    }

                    if(snippetOB.has("tags")) {
                        String tag = snippetOB.getString("tags");
                        tag=tag.replaceAll("\\[","");
                        tag=tag.replaceAll("]","");
                        tag=tag.replaceAll("\"","");
                        String[] tagparsing = tag.split(",");
                        for(int i=0;i<tagparsing.length;i++){
                            tagparsing[i].replaceAll("\"","");
                            tagparsing[i]="#"+tagparsing[i];
                            newtag+=tagparsing[i]+" ";
                        }
                    }

                    if(statisticOB.has("likeCount")){
                            likeCount = statisticOB.getString("likeCount");
                        Log.e("sojeong","likeCount: "+likeCount);
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


class Data {
    String videoUrl=null;
    String videoCode=null;
    // List Draw
    int[] drawables;
    int type=0;

    public Data(int[] drawables ) {
        this.drawables=drawables;
        this.type=1;
    }

    public Data(String videoCode) {
        this.videoCode=videoCode;
        this.videoUrl = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"+videoCode+"\" frameborder=\"0\" allowfullscreen></iframe>";
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public int[] getDrawables(){return drawables;}

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getType(){return type;}

    public String getVideoCode() {
        return videoCode;
    }
}