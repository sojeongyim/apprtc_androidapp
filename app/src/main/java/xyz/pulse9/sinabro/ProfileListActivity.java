//package xyz.pulse9.sinabro;
//
//import android.os.Bundle;
//import android.support.design.widget.TabLayout;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//
//import java.util.ArrayList;
//
///**
// * Created by ssoww on 2018-09-12.
// */
//
//public class ProfileListActivity extends AppCompatActivity {
//
////    private RecyclerView mRecyclerView;
////    private RecyclerView.Adapter mAdapter;
////    private RecyclerView.LayoutManager mLayoutManager;
////    private ArrayList<MyData> myDataset;
//    private ViewPager mViewPager;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.testviewpager);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        mViewPager = (ViewPager) findViewById(R.id.container);
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user1));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user2));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user3));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user4));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user5));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user6));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user7));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user8));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user9));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user10));
//
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//        final mPagerAdapter adapter = new mPagerAdapter
//                (getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
////        mRecyclerView = (RecyclerView) findViewById(R.id.profilelist_recyclerview);
////
////        // use this setting to improve performance if you know that changes
////        // in content do not change the layout size of the RecyclerView
////        mRecyclerView.setHasFixedSize(true);
////
////        // use a linear layout manager
////        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, true);  //스크롤 가로로 설정
//////        mRecyclerView.scrollToPosition(itemClass.size() - 1);
////
////        mRecyclerView.setLayoutManager(mLayoutManager);
////
////        // specify an adapter (see also next example)
////        myDataset = new ArrayList<>();
////        mAdapter = new MyAdapter(myDataset,2);
////        mRecyclerView.setAdapter(mAdapter);
////
////        myDataset.add(new MyData("#InsideOut", R.drawable.eximage1));
////        myDataset.add(new MyData("#Mini", R.drawable.eximage2));
////        myDataset.add(new MyData("#ToyStroy", R.drawable.eximage3));
//
//    }
//
//}
