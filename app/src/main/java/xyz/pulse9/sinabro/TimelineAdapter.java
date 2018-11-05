package xyz.pulse9.sinabro;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<TimelineData> youtubeVideoList;
    private NetworkInfo activeNetwork;
    public TimelineAdapter() {
    }
    public TimelineAdapter(List<TimelineData> youtubeVideoList) {
        this.youtubeVideoList = youtubeVideoList;
    }
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_onelayout_cardnews, parent, false);
            return new ImageViewHolder(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_onelayout_youtube, parent, false);
            return new VideoViewHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Animation clickanimation=AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.clickanimaiton);

        if(holder instanceof VideoViewHolder)
        {
            final String current_videoCode = youtubeVideoList.get(position).getVideoCode();
            ((VideoViewHolder) holder).timeline_tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(clickanimation);
                    Toast.makeText( ((VideoViewHolder)holder).itemView.getContext(),"Coming Soon...",Toast.LENGTH_SHORT).show();
                }
            });

            ConnectivityManager cm = (ConnectivityManager)holder.itemView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork!=null) {
                    ((VideoViewHolder) holder).videoWeb.getSettings().setJavaScriptEnabled(true);
                    ((VideoViewHolder) holder).videoWeb.setWebChromeClient(new WebChromeClient() {
                    } );
                    GetYoutubeInfo getVideoInfo = new GetYoutubeInfo(current_videoCode, ((VideoViewHolder) holder));
                    getVideoInfo.execute();
                    ((VideoViewHolder) holder).videoWeb.setVisibility(View.VISIBLE);
                    ((VideoViewHolder) holder).videoWeb.loadData(youtubeVideoList.get(position).getVideoUrl(), "text/html", "utf-8");

                    ((VideoViewHolder) holder).timeLineDB.child(current_videoCode).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ((VideoViewHolder) holder).likeCountText.setText(Long.toString(dataSnapshot.getChildrenCount()));
                            Log.e("sojeong", "curser: " + ((VideoViewHolder) holder).curuser);
                            Log.e("sojeong", "uid: " + ((VideoViewHolder) holder).uid);
                            Log.e("sojeong", "getvalue: " + dataSnapshot.child(((VideoViewHolder) holder).uid).getValue());
                            if (((VideoViewHolder) holder).curuser != null && dataSnapshot.child(((VideoViewHolder) holder).uid).getValue() != null) {
                                ((VideoViewHolder) holder).heart_check.setVisibility(View.VISIBLE);
                                ((VideoViewHolder) holder).heart.setVisibility(View.INVISIBLE);
                            } else {
                                ((VideoViewHolder) holder).heart_check.setVisibility(View.INVISIBLE);
                                ((VideoViewHolder) holder).heart.setVisibility(View.VISIBLE);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    ((VideoViewHolder) holder).heart.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            if (((VideoViewHolder) holder).curuser != null) {
                                ((VideoViewHolder) holder).timeLineDB.child(current_videoCode).child(((VideoViewHolder) holder).uid).setValue("1");
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
                    ((VideoViewHolder) holder).heart_check.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            ((VideoViewHolder) holder).timeLineDB.child(current_videoCode).child(((VideoViewHolder) holder).uid).removeValue();
                        }
                    });

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
        else    //cardnews
        {

            final String current_cardnewsCode = youtubeVideoList.get(position).getCardnewsCode();
            ((ImageViewHolder)holder).timeline_tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(clickanimation);
                    Toast.makeText(holder.itemView.getContext(),"Coming Soon...",Toast.LENGTH_SHORT).show();
                }
            });


            CardnewsAdapter adapter = new CardnewsAdapter(((AppCompatActivity)holder.itemView.getContext()).getSupportFragmentManager(),11);
            ((ImageViewHolder)holder).cardnewspager.setAdapter(adapter);
//                holder.cardimage.setImageResource(youtubeVideoList.get(position).getDrawables());
            ((ImageViewHolder)holder).cardnewspager.setVisibility(View.VISIBLE);


            ((ImageViewHolder) holder).timeLineDB.child(current_cardnewsCode).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ((ImageViewHolder) holder).likeCountText.setText(Long.toString(dataSnapshot.getChildrenCount()));

                    if (((ImageViewHolder) holder).curuser != null && dataSnapshot.child(((ImageViewHolder) holder).uid).getValue() != null) {
                        ((ImageViewHolder) holder).heart_check.setVisibility(View.VISIBLE);
                        ((ImageViewHolder) holder).heart.setVisibility(View.INVISIBLE);
                    } else {
                        ((ImageViewHolder) holder).heart_check.setVisibility(View.INVISIBLE);
                        ((ImageViewHolder) holder).heart.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            ((ImageViewHolder) holder).heart.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (((ImageViewHolder) holder).curuser != null) {
                        ((ImageViewHolder) holder).timeLineDB.child(current_cardnewsCode).child(((ImageViewHolder) holder).uid).setValue("1");
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
            ((ImageViewHolder) holder).heart_check.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    ((ImageViewHolder) holder).timeLineDB.child(current_cardnewsCode).child(((ImageViewHolder) holder).uid).removeValue();
                }
            });
        }






    }

    @Override
    public int getItemViewType(int position) {
        return youtubeVideoList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return youtubeVideoList.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder{
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
            videoWeb.setHorizontalScrollBarEnabled(false);
            videoWeb.setVerticalScrollBarEnabled(false);
//            cardimage=(ImageView)itemView.findViewById(R.id.cardnews);

        }

    }
    class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView channel_img;
        TextView channel_name;
        TextView likeCountText;
        ImageButton heart;
        ImageButton heart_check;
        ImageButton timeline_tab;

        String uid="";
        FirebaseUser curuser;
        DatabaseReference timeLineDB;
        ViewPager cardnewspager;


        public ImageViewHolder(View itemView) {
            super(itemView);

            curuser = FirebaseAuth.getInstance().getCurrentUser();
            if(curuser!=null) {
                uid = curuser.getUid();
            }
            Log.e("sojeong","curser: "+curuser);
            Log.e("sojeong","uid: "+uid);
            timeLineDB= FirebaseDatabase.getInstance().getReference("timeline");
            channel_img=(ImageView)itemView.findViewById(R.id.channel_img);
            channel_name=(TextView)itemView.findViewById(R.id.channel_name);
            likeCountText = (TextView)itemView.findViewById(R.id.likeCount);
            heart = (ImageButton) itemView.findViewById(R.id.heart);
            heart_check = (ImageButton)itemView.findViewById(R.id.heart_check);
            timeline_tab=(ImageButton)itemView.findViewById(R.id.timeline_tab);
            cardnewspager = (ViewPager)itemView.findViewById(R.id.cardnews_pager);

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
            makeTextViewResizable(holder.tagText, 1, "More", true);

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
//                        tv.setTextColor(Color.parseColor("#c7c7c7"));

                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }
}