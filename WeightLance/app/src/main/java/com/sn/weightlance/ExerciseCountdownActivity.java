package com.androiddeft.loginandregistration;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.concurrent.TimeUnit;

import Utitlities.RecyclerViewAdapter;
import Utitlities.ServerImageParseAdapter;

public class ExerciseCountdownActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button start_timer,stop_timer;
    MediaPlayer mp = null;
    MyCountDownTimer myCountDownTimer;
    ImageLoader imageLoader1;
    String imageurl;
    String time;
    NetworkImageView imageView;

    @Override
    public void onBackPressed() {
        myCountDownTimer.cancel();
        /*long timeinmillis = TimeUnit.MINUTES.toMillis(Integer.parseInt(time));*/
        long t = (Integer.parseInt(time)*1000);
        long interval = t/100;
        myCountDownTimer = new MyCountDownTimer(t, interval);
        progressBar.setProgress(0);
        if(mp!=null)
        {
            mp.stop();
        }
        Intent inte = new Intent(ExerciseCountdownActivity.this,ExerciseActivity.class);
        startActivity(inte);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_countdown);


        Intent i = getIntent();
        imageurl  = i.getStringExtra("image");
        time = i.getStringExtra("time");
        imageView = findViewById(R.id.excerciseImg);

        imageLoader1 = ServerImageParseAdapter.getInstance(this).getImageLoader();

        imageLoader1.get(imageurl,
                ImageLoader.getImageListener(
                       imageView,
                        R.mipmap.ic_launcher,
                        android.R.drawable.ic_dialog_alert
                )
        );

        imageView.setImageUrl(imageurl, imageLoader1);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        start_timer=(Button)findViewById(R.id.startBtn);
        stop_timer=(Button)findViewById(R.id.resetBtn);

        start_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long t = (Integer.parseInt(time)*1000);
                long interval = t/100;
                myCountDownTimer = new MyCountDownTimer(t, interval);
                myCountDownTimer.start();

            }
        });

        stop_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myCountDownTimer.cancel();
                /*long timeinmillis = TimeUnit.MINUTES.toMillis(Integer.parseInt(time));*/
                long t = (Integer.parseInt(time)*1000);
                long interval = t/100;
                myCountDownTimer = new MyCountDownTimer(t, interval);
                progressBar.setProgress(0);
               if(mp!=null)
               {
                   mp.stop();
               }
            }

        });

    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            int progress = (int) (millisUntilFinished/1000);
            progressBar.setProgress(progressBar.getMax()-progress);
        }
        @Override
        public void onFinish() {
            mp = MediaPlayer.create(ExerciseCountdownActivity.this,R.raw.alarm);
            mp.start();
            Toast.makeText(ExerciseCountdownActivity.this,"Exercise Finished: "+time+" "+imageurl,Toast.LENGTH_SHORT);
            myCountDownTimer.cancel();
        }
    }
}