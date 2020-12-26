package com.example.android.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;

import com.example.android.R;

public class Timer extends AppCompatActivity {
    private static final int MILLISINFUTURE = 181*1000;
    private static final int COUNT_DOWN_INTERVAL = 1000;

    private TextView countTxt;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        countTxt = (TextView)findViewById(R.id.count_txt);
        countDownTimer();
        countDownTimer.start();
    }

    public void countDownTimer(){
        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            public void onTick(long millisUntilFinished) {
                long emailAuthCount = millisUntilFinished /1000;
                if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10) {//초가 10보다 크면 그냥 출력
                    countTxt.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }
                else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                    countTxt.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                    }
            }
            public void onFinish() {
                countTxt.setText(String.valueOf("Finish ."));
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            countDownTimer.cancel();
        } catch (Exception e) {}
        countDownTimer=null;
    }
}

