package xyz.pulse9.sinabro;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

/**
 * Created by ssoww on 2018-09-18.
 */

import android.app.Activity;

import android.widget.TextView;


public class DateTimePicker extends Activity {

    android.widget.DatePicker mDate;
    android.widget.TimePicker mTime;

    TextView mTxtDate;
    TextView mTxtTime;




    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_datetimepicker);



        mDate = (android.widget.DatePicker)findViewById(R.id.datepicker);

        mTxtDate = (TextView)findViewById(R.id.txt_date);

        mTime = (android.widget.TimePicker)findViewById(R.id.timepicker);

        mTxtTime = (TextView)findViewById(R.id.txt_time);



        //처음 DatePicker를 오늘 날짜로 초기화한다.

        //그리고 리스너를 등록한다.

        mDate.init(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(),

                new android.widget.DatePicker.OnDateChangedListener() {



                    //값이 바뀔때마다 텍스트뷰의 값을 바꿔준다.

                    @Override

                    public void onDateChanged(android.widget.DatePicker view, int year, int monthOfYear,

                                              int dayOfMonth) {

                        // TODO Auto-generated method stub



                        //monthOfYear는 0값이 1월을 뜻하므로 1을 더해줌 나머지는 같다.

                        mTxtDate.setText(String.format("%d/%d/%d", year,monthOfYear + 1

                                , dayOfMonth));



                    }

                });



        //선택기로부터 날짜 조사

        findViewById(R.id.btnnow).setOnClickListener(new View.OnClickListener() {



            //버튼 클릭시 DatePicker로부터 값을 읽어와서 Toast메시지로 보여준다.

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                String result = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    result = String.format("%d년 %d월 %d일 %d시 %d분", mDate.getYear(),

                            mDate.getMonth() + 1, mDate.getDayOfMonth(), mTime.getHour(),mTime.getMinute());
                }else{
                    result = String.format("%d년 %d월 %d일 %d시 %d분", mDate.getYear(),

                            mDate.getMonth() + 1, mDate.getDayOfMonth(), mTime.getCurrentHour(),mTime.getCurrentMinute());
                }

                Toast.makeText(DateTimePicker.this, result, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.putExtra("result", result);
                setResult(RESULT_OK, intent);

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
