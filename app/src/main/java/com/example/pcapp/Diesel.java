package com.example.pcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class Diesel extends AppCompatActivity {

    Button btnClick;
    VideoView earthVid;
    TextView ppm, txtStage, txtWTD, txtBody1, txtBody2, mint, sect, timer;
    SeekBar sBar;
    CountDownTimer countDownTimer;
    long startTime = 10000;
    private boolean timerRunning = false;
    private static int TIME_OUT = 3000;

    String stage1 = "1.&nbsp&nbsp&nbsp&nbsp <b>Accelerate</b> gradually<br>2.&nbsp&nbsp&nbsp&nbsp Cut down your <b>speed limits</b><br>3.&nbsp&nbsp&nbsp&nbsp Tires should be <b>inflated properly</b>";
    String stage2 = "1.&nbsp&nbsp&nbsp&nbsp Use <b>bike</b> or prefer <b>walking</b><br> 2.&nbsp&nbsp&nbsp&nbsp <b>Car Pool</b> with your friends<br> 3.&nbsp&nbsp&nbsp&nbsp Take <b>public transit</b> when possible";
    String stage3 = "1.&nbsp&nbsp&nbsp&nbsp If you have more than one vehicle, use the <b>most fuel-efficient one</b> possible<br> 2.&nbsp&nbsp&nbsp&nbsp <b>Combine errands</b> to make fewer<br> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp trips<br> 3.&nbsp&nbsp&nbsp&nbsp Get <b>regular tune-ups</b> of your car";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diesel);

        btnClick = findViewById(R.id.btnClick);
        mint = findViewById(R.id.min);
        sect = findViewById((R.id.sec));
        timer = findViewById(R.id.timer);
        earthVid = findViewById(R.id.earthVid);
        txtStage = findViewById(R.id.txtStage);
        txtWTD = findViewById(R.id.txtWTD);
        txtBody1 = findViewById(R.id.txtBody1);
        txtBody2 = findViewById(R.id.txtBody2);
        ppm = findViewById(R.id.ppm);
        sBar = findViewById(R.id.sBar);

        earthVid.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
//        MediaController mediaController = new MediaController(MainActivity.this);
//        mediaController.setAnchorView(earthVid);
//        earthVid.setMediaController(mediaController);
        earthVid.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.eco1);
        earthVid.start();

        sBar.setMax(1000);
        sBar.setProgress(300);

        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ppm.setText("" + i + " ppm");

                update(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mediaPlayer = MediaPlayer.create(Diesel.this, R.raw.stop);
                mediaPlayer.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(Diesel.this, MyCars.class);
                        Toast.makeText(Diesel.this, "Car Stops", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                        finish();
                    }
                }, TIME_OUT);
            }
        });

    }

    public void update(int val) {
        if(val >= 700) {
            if(! timerRunning) {
                timerRunning = true;
                earthVid.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.end);
                earthVid.start();
                txtStage.setText("CRITICAL STAGE");
                txtWTD.setText("Stop your car now or else forever !");
                txtBody2.setText("\n\n");
                txtBody1.setText("");
                timer.setText("TIMER");
                countDownTimer = new CountDownTimer(startTime, 1000) {
                    @Override
                    public void onTick(long l) {
                        int val = (int) l / 1000;
                        int min = val / 60;
                        int sec = val - (min * 60);
                        String secs = Integer.toString(sec);
                        String mins = Integer.toString(min);
                        if (min <= 9) {
                            mins = "0" + mins;
                        }
                        if (sec <= 9) {
                            secs = "0" + secs;
                        }
                        mint.setText(mins + " : ");
                        sect.setText(secs);
                        Log.i("time left", String.valueOf(val));
                    }

                    @Override
                    public void onFinish() {
                        timerRunning = false;
                        MediaPlayer mediaPlayer = MediaPlayer.create(Diesel.this, R.raw.stop);
                        mediaPlayer.start();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(Diesel.this, start_stop.class);
                                i.putExtra("stop", "CAR STOPS !");
                                startActivity(i);
                                finish();
                            }
                        }, TIME_OUT);
                    }
                }.start();
            }
        }
        else {
            timerRunning = false;
            startTime = 10000;
            countDownTimer.cancel();
            txtBody1.setText("What can you do ?");
            timer.setText("");
            mint.setText("");
            sect.setText("");

            if(val>=500 && val<550) {
//stage1
                txtStage.setText("STAGE 1");
                txtWTD.setText("Emissions are slightly above the Desired Standard Value !");
                txtBody2.setText(Html.fromHtml(stage1));
                earthVid.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.stage1);
                earthVid.start();
            }
            else if(val>=550 && val<650) {
//stage2
                txtStage.setText("STAGE 2");
                txtWTD.setText("Emissions Amount is Increasing !");
                txtBody2.setText(Html.fromHtml(stage2));
                earthVid.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.stage2);
                earthVid.start();
            }
            else if(val>=650 && val<700) {
//stage3
                txtStage.setText("STAGE 3");
                txtWTD.setText("Take Action or else enter into Critical Stage !");
                txtBody2.setText(Html.fromHtml(stage3));
                earthVid.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.stage3);
                earthVid.start();
            }
            else {
//eco
                txtStage.setText("ECO-FRIENDLY CAR !");
                txtWTD.setText("You are doing a great job !");
                txtBody1.setText("");
                txtBody2.setText("");
                earthVid.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.eco2);
                earthVid.start();
            }
        }
    }
    @Override
    public void onBackPressed() {

    }
}
