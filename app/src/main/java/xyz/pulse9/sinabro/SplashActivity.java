package xyz.pulse9.sinabro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        try
//        {
//            Thread.sleep(1000);
            Intent mainIntent = new Intent(this, LoginActivity.class);
            startActivity(mainIntent);
            finish();
//        }
//        catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }
    }
}
