package xyz.pulse9.sinabro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class MySettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private ToggleButton notiCheck;
    private Button lgoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);
        notiCheck = findViewById(R.id.notiToggle);
        lgoutBtn = findViewById(R.id.lgoutBtn);


        if(getCheck())
        {
            notiCheck.setBackgroundDrawable(getResources().getDrawable(R.drawable.on));
        }
        else
        {
            notiCheck.setBackgroundDrawable(getResources().getDrawable(R.drawable.off));
        }
        notiCheck.setChecked(getCheck());
        lgoutBtn.setOnClickListener(this);
        notiCheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.notiToggle:
                setBG(notiCheck.isChecked());
                setCheck(notiCheck.isChecked());
                break;
            case R.id.lgoutBtn:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Toast toast = Toast.makeText(this.getApplicationContext(),"Logout Complete", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
    private boolean getCheck(){
        SharedPreferences pref = getSharedPreferences("sina_set", MODE_PRIVATE);
        return pref.getBoolean("notiChk", true);
    }

    // 값 저장하기
    private void setCheck(boolean chk){
        SharedPreferences pref = getSharedPreferences("sina_set", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("notiChk", chk);
        editor.commit();
    }

    private void setBG(boolean chk)
    {
        if(chk)
        {

            Toast toast = Toast.makeText(getApplicationContext(),"Turn on Notification", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(),"Turn off Notification", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
