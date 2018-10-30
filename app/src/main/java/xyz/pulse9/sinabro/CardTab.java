package xyz.pulse9.sinabro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by pulse on 2018. 10. 30..
 */

public class CardTab extends Fragment {
    ImageView cardnews_view;
    Animation clickanimation;
    int drawable;

    public void setImage(int drawable){this.drawable=drawable;}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        clickanimation = AnimationUtils.loadAnimation(getContext(),R.anim.clickanimaiton);
        ImageView cardnews_oneview=(ImageView)getView().findViewById(R.id.cardnews_oneview);
        cardnews_oneview.setImageResource(drawable);





    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.cardnews_oneview, container, false);

    }

}
