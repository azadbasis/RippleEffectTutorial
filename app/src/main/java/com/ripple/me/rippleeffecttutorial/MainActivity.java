package com.ripple.me.rippleeffecttutorial;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RippleBackground rippleBackground;
    private ImageView foundDevice;
    private LinearLayout userInfoContainer;
    //TIMER
    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    private Button startB;
    public TextView text;
    private final long startTime = 15 * 1000;
    private final long interval = 1 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rippleBackground=(RippleBackground)findViewById(R.id.content);
        final Handler handler=new Handler();

        foundDevice=(ImageView)findViewById(R.id.foundDevice);
        userInfoContainer=(LinearLayout) findViewById(R.id.userInfoContainer);

        text = (TextView) this.findViewById(R.id.timer);
        countDownTimer = new MyCountDownTimer(startTime, interval);
        text.setText("00:00:"+text.getText() + String.valueOf(startTime / 1000));

        final ImageView button=(ImageView)findViewById(R.id.centerImage);
        button.performClick();
        button.setPressed(true);
        button.invalidate();
        rippleBackground.startRippleAnimation();
        button.postDelayed(new Runnable() {
            @Override
            public void run() {
                button.setPressed(false);
                button.invalidate();
                foundDevice();
                countDownTimer();
            }
        },3000);

    }

    private void countDownTimer() {
        if (!timerHasStarted) {
            countDownTimer.start();
            timerHasStarted = true;
            //startB.setText("STOP");
        } else {
            countDownTimer.cancel();
            timerHasStarted = false;
          //  startB.setText("RESTART");
        }
    }

    private void foundDevice(){
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        ArrayList<Animator> animatorList=new ArrayList<Animator>();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleX", 0f, 1.2f, 1f);
        animatorList.add(scaleXAnimator);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleY", 0f, 1.2f, 1f);
        animatorList.add(scaleYAnimator);
        animatorSet.playTogether(animatorList);
        foundDevice.setVisibility(View.VISIBLE);
        userInfoContainer.setVisibility(View.VISIBLE);
        animatorSet.start();
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            text.setText("Time's up!");
            rippleBackground.stopRippleAnimation();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            text.setText("00:00:" + millisUntilFinished / 1000);
        }
    }

}