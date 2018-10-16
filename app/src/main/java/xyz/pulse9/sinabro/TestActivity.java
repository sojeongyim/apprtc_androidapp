package xyz.pulse9.sinabro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by pulse on 2018. 10. 16..
 */

public class TestActivity extends YouTubeBaseActivity {
    YouTubePlayerView youtubeView;
    Button button;
    YouTubePlayer.OnInitializedListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        button = (Button) findViewById(R.id.youtubeButton);
        youtubeView = (YouTubePlayerView)findViewById(R.id.youtubeView);
        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer,
                                                boolean b) {
                youTubePlayer.loadVideo("n1T9VPG4st0");
            }
            @Override
            public void onInitializationFailure(
                    YouTubePlayer.Provider provider,
                    YouTubeInitializationResult youTubeInitializationResult) {
            }
        };
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                youtubeView.initialize("AIzaSyD5DB011LhNQGjoAPqRzqKhuOMPkOf__KE", listener);
            }
        });
    }
}