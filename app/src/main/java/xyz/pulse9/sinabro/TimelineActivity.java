package xyz.pulse9.sinabro;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData> myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_recyclerview);
        mRecyclerView = (RecyclerView) findViewById(R.id.timeline_recyclerview);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        myDataset = new ArrayList<>();
        mAdapter = new MyAdapter(myDataset,1);
        mRecyclerView.setAdapter(mAdapter);

        myDataset.add(new MyData("#InsideOut", R.drawable.eximage1));
        myDataset.add(new MyData("#Mini", R.drawable.eximage2));
        myDataset.add(new MyData("#ToyStroy", R.drawable.eximage3));


        ImageButton setting_butt = (ImageButton) findViewById(R.id.setting);
        ImageButton notice_butt = (ImageButton) findViewById(R.id.notice);
        ImageButton plustutor_butt = (ImageButton) findViewById(R.id.plustutor);
        ImageButton home_butt = (ImageButton) findViewById(R.id.home);
        ImageButton chatting_butt = (ImageButton) findViewById(R.id.chatting);

        setting_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimelineActivity.this, AppSettingActivity.class);
                startActivity(intent);
            }
        });
        notice_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimelineActivity.this, ConnectActivity.class); //NotiActivity->message
                startActivity(intent);
            }
        });
        plustutor_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimelineActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        home_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimelineActivity.this, TimelineActivity.class);
                startActivity(intent);
            }
        });
        chatting_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimelineActivity.this, ConnectActivity.class);  //message
                startActivity(intent);
            }
        });

    }
}


class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<MyData> mDataset;
    private int intentnum;


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
    public MyAdapter(ArrayList<MyData> myDataset,int num) {
        mDataset = myDataset;
        intentnum = num; // timeline:1, profile:2
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v;
        if(intentnum==1) {
            // create a new view
           v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.timeline_onelayout, parent, false);
            // set the view's size, margins, paddings and layout parameters
        }else{
           v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.profilelist_onelayout, parent, false);
        }
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

