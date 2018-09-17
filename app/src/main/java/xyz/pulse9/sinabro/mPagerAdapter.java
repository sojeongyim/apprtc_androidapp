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

        switch (position) {
            case 0:
                TabFragment.TabFragment1 tab1 = new TabFragment.TabFragment1();
                return tab1;
            case 1:
                TabFragment.TabFragment2 tab2 = new TabFragment.TabFragment2();
                return tab2;
            case 2:
                TabFragment.TabFragment3 tab3 = new TabFragment.TabFragment3();
                return tab3;
            case 3:
                TabFragment.TabFragment4 tab4 = new TabFragment.TabFragment4();
                return tab4;
            case 4:
                TabFragment.TabFragment5 tab5 = new TabFragment.TabFragment5();
                return tab5;
            case 5:
                TabFragment.TabFragment6 tab6 = new TabFragment.TabFragment6();
                return tab6;
            case 6:
                TabFragment.TabFragment7 tab7 = new TabFragment.TabFragment7();
                return tab7;
            case 7:
                TabFragment.TabFragment8 tab8 = new TabFragment.TabFragment8();
                return tab8;
            case 8:
                TabFragment.TabFragment9 tab9= new TabFragment.TabFragment9();
                return tab9;
            case 9:
                TabFragment.TabFragment10 tab10 = new TabFragment.TabFragment10();
                return tab10;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}