package xyz.pulse9.sinabro;

/**
 * Created by ssoww on 2018-09-15.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class mPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    String[] teacherImage = {"https://lh3.googleusercontent.com/-3BbYp2g2bWU/AAAAAAAAAAI/AAAAAAAAAAA/APUIFaNWrBl2tEHsOivS0ifo6Op0Z45lsw/s96-c/photo.jpg"
            ,"https://lh4.googleusercontent.com/-RambhDwrC_I/AAAAAAAAAAI/AAAAAAAAAAA/APUIFaMXgnprl9Jr1ZCwpqjEK24gDVXGhQ/s96-c/photo.jpg"
            ,"https://lh4.googleusercontent.com/-M_2Et9VJ0jo/AAAAAAAAAAI/AAAAAAAAAAA/APUIFaPzFeDiWw1NUcdYamHS8lWW1rDi4w/s96-c/photo.jpg"
            ,"https://lh6.googleusercontent.com/-3_qN5O6SBqY/AAAAAAAAAAI/AAAAAAAAAAA/APUIFaNvq_pFyFyF2jiSboPHs8CzJV24uw/s96-c/photo.jpg"
            ,"https://lh4.googleusercontent.com/-b1HXe8eQcnU/AAAAAAAAAAI/AAAAAAAAAAA/AAN31DUVudIYJtnk_SkoKoZ09J1UxV0IGQ/s96-c/photo.jpg"
            ,"https://lh4.googleusercontent.com/-1Zg-83UaQew/AAAAAAAAAAI/AAAAAAAAAAA/AAN31DU4AkUfustg4j9JOSGyHYbfFV9rvA/s96-c/photo.jpg"
            ,"https://lh4.googleusercontent.com/-eGVgDQ-F-bg/AAAAAAAAAAI/AAAAAAAAAAA/APUIFaN2b4OE2Tg4MfijUmFURXTgVz0D8Q/s96-c/photo.jpg"};

    public mPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
//          intern1,2,3,6,dev,dev4,dev5  //임시
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

    public String getImage(int idx)
    {
        return teacherImage[idx];
    }
}
