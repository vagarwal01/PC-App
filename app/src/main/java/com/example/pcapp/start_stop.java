package com.example.pcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class start_stop extends AppCompatActivity {

    TextView txt;
    ImageView car_image, btnBack, btnLogout;
    private static int TIME_OUT = 1500;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_stop);

        btnBack = findViewById(R.id.btnBack);
        btnLogout = findViewById(R.id.btnLogout);
        car_image = findViewById(R.id.car_image);
        txt = findViewById(R.id.txt);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(start_stop.this, MyCars.class);
                startActivity(i);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent i = new Intent(getApplicationContext(), Home1.class);
                startActivity(i);
            }
        });
        Bundle extras = getIntent().getExtras();
        final String type = extras.getString("data", "");

        Bundle extra = getIntent().getExtras();
        final String pro = extra.getString("stop", "");
        txt.setText(pro);

        car_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mediaPlayer = MediaPlayer.create(start_stop.this, R.raw.start);
                mediaPlayer.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (type.equals("petrol")) {
                            Intent i = new Intent(start_stop.this, Petrol.class);
                            startActivity(i);
                        }
                        if (type.equals("diesel")) {
                            Intent i = new Intent(start_stop.this, Diesel.class);
                            startActivity(i);
                        }
                        finish();
                    }
                }, TIME_OUT);
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
