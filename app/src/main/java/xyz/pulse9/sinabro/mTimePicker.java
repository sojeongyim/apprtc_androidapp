package xyz.pulse9.sinabro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ssoww on 2018-09-18.
 */


public class mTimePicker extends Activity {

    android.widget.TimePicker mTime;
    String result;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_timepicker);
        Intent intent1 =getIntent();
        result = intent1.getStringExtra("result");

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        int width = (int) (display.getWidth() * 0.9); //Display 사이즈의 70%

        int height = (int) (display.getHeight() * 0.65);  //Display 사이즈의 90%

        getWindow().getAttributes().width = width;

        getWindow().getAttributes().height = height;

        mTime = (android.widget.TimePicker)findViewById(R.id.timepicker);

        Button select_butt =(Button)findViewById(R.id.select);
        Button cancel_butt =(Button)findViewById(R.id.cancel);
        select_butt.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub
                String timeresult = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    timeresult = String.format("/%d/%d", mTime.getHour(),mTime.getMinute());
                }else{
                    timeresult = String.format("/%d/%d", mTime.getCurrentHour(),mTime.getCurrentMinute());
                }

                result = result+timeresult;
//                Toast.makeText(mDatePicker.this, result, Toast.LENGTH_SHORT).show();

                Intent intent2 = new Intent();
                Toast.makeText(mTimePicker.this,result,Toast.LENGTH_SHORT).show();
                intent2.putExtra("result", result);
                setResult(RESULT_OK, intent2);

                //액티비티(팝업) 닫기
                finish();

            }

        });

        cancel_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

}
