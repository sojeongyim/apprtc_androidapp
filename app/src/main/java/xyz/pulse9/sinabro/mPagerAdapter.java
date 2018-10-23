package xyz.pulse9.sinabro;

/**
 * Created by ssoww on 2018-09-15.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class mPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public mPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
//          intern1,2,3,6,dev,dev4,dev5
        String[] teacherToken = {"Jhbg1lLcwJRP7HcwHwVQwzJDy1H2"
                ,"luzZy37nmveRpTavmzgAmvOemKw1"
                ,"jsPInIrU4RMoNLNQEOYciaMScL73"
                ,"cTNzaDD7mtfqbNVs7yZRsqVQVDV2"
                ,"ltGq7etoE4cBiFhwbGbjLYAms8m1"
                ,"lzByd8SE7YYupDMHkFKhvEHZg2F3"
                ,"voc9tqPaD5anHP0z28yTuAtlxcF3"};  //임시
        TabFragment tab = new TabFragment();
        tab.setTeacherToken(teacherToken[position]);
        return tab;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
