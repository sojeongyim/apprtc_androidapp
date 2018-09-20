package xyz.pulse9.sinabro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by ssoww on 2018-09-18.
 */

import android.app.Activity;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;


public class mDatePicker extends Activity {

    android.widget.DatePicker mDate;
    android.widget.TimePicker mTime;

    TextView mTxtDate;
    TextView mTxtTime;




    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_datepicker);

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        int width = (int) (display.getWidth() * 0.9); //Display 사이즈의 70%

        int height = (int) (display.getHeight() * 0.7);  //Display 사이즈의 90%

        getWindow().getAttributes().width = width;

        getWindow().getAttributes().height = height;


        mDate = (android.widget.DatePicker)findViewById(R.id.datepicker);

        mTime = (TimePicker)findViewById(R.id.timepicker);



//        //처음 DatePicker를 오늘 날짜로 초기화한다.
//
//        //그리고 리스너를 등록한다.
//
//        mDate.init(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(),
//
//                new android.widget.DatePicker.OnDateChangedListener() {
//
//
//
//                    //값이 바뀔때마다 텍스트뷰의 값을 바꿔준다.
//
//                    @Override
//
//                    public void onDateChanged(android.widget.DatePicker view, int year, int monthOfYear,
//
//                                              int dayOfMonth) {
//
//                        // TODO Auto-generated method stub
//
//
//
//                        //monthOfYear는 0값이 1월을 뜻하므로 1을 더해줌 나머지는 같다.
//
//                        mTxtDate.setText(String.format("%d/%d/%d", year,monthOfYear + 1
//
//                                , dayOfMonth));
//
//
//
//                    }
//
//                });



        //선택기로부터 날짜 조사

        findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {



            //버튼 클릭시 DatePicker로부터 값을 읽어와서 Toast메시지로 보여준다.

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                String result = null;
                result = String.format("Date: %d . %d . %d", mDate.getYear(),mDate.getMonth() + 1, mDate.getDayOfMonth());

//                Toast.makeText(mDatePicker.this, result, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mDatePicker.this,mTimePicker.class);
                intent.putExtra("result", result);
                startActivity(intent);
//                setResult(RESULT_OK, intent);

                //액티비티(팝업) 닫기
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