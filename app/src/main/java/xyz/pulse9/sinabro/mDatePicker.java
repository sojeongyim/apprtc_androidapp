package xyz.pulse9.sinabro;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by ssoww on 2018-09-18.
 */

import android.app.Activity;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class mDatePicker extends Activity {
    android.widget.DatePicker mDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_datepicker);

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        int width = (int) (display.getWidth() * 0.9); //Display 사이즈의 70%
        int height = (int) (display.getHeight() * 0.8);  //Display 사이즈의 90%

        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;


        mDate = (android.widget.DatePicker)findViewById(R.id.datepicker);

        Button select_butt =(Button)findViewById(R.id.select);
        Button cancel_butt =(Button)findViewById(R.id.cancel);

        select_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar calendar = new GregorianCalendar(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth());
                Intent intent = new Intent();
                intent.putExtra("result", calendar.getTimeInMillis());
                Log.d("JANGMIN", "DatePicker Get Unix : " + calendar.getTimeInMillis());
                setResult(RESULT_OK,intent);
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
