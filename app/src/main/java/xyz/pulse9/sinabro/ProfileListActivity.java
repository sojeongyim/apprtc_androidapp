package xyz.pulse9.sinabro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by ssoww on 2018-09-12.
 */

public class ProfileListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData> myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilelist_recyclerview);
        mRecyclerView = (RecyclerView) findViewById(R.id.profilelist_recyclerview);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, true);  //스크롤 가로로 설정
//        mRecyclerView.scrollToPosition(itemClass.size() - 1);

        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        myDataset = new ArrayList<>();
        mAdapter = new MyAdapter(myDataset,2);
        mRecyclerView.setAdapter(mAdapter);

        myDataset.add(new MyData("#InsideOut", R.drawable.eximage1));
        myDataset.add(new MyData("#Mini", R.drawable.eximage2));
        myDataset.add(new MyData("#ToyStroy", R.drawable.eximage3));

    }

}
