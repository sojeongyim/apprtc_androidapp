package xyz.pulse9.sinabro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by pulse on 2018. 10. 30..
 */

public class CardnewsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public CardnewsAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        int drawable[]={R.drawable.kr1,R.drawable.kr2,R.drawable.kr3,R.drawable.kr4,R.drawable.kr5,R.drawable.kr6,R.drawable.kr7
        ,R.drawable.kr8,R.drawable.kr9,R.drawable.kr10,R.drawable.kr11};

        CardTab tab = new CardTab();
        tab.setImage(drawable[position]);
        return tab;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

