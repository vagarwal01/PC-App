package com.example.pcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Info extends AppCompatActivity {

    Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        btnGo = findViewById(R.id.btnGo);

        Bundle extras = getIntent().getExtras();
        final String fuel = extras.getString("data", "");

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Info.this, start_stop.class);
                i.putExtra("data", fuel);
                startActivity(i);
            }
        });
    }
}
