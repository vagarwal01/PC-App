package com.example.pcapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class AddCar extends AppCompatActivity {

    EditText txtBrand, txtModel;
    RadioButton rbPetrol, rbDiesel;
    Button btnAdd;
    String fuel;
    ImageView btnLogout, btnBack;

    DatabaseReference mReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        txtBrand = findViewById(R.id.txtBrand);
        txtModel = findViewById(R.id.txtModel);
        rbDiesel = findViewById(R.id.rbDiesel);
        rbPetrol = findViewById(R.id.rbPetrol);
        btnAdd = findViewById(R.id.btnAdd);
        btnLogout = findViewById(R.id.btnLogout);
        btnBack = findViewById(R.id.btnBack);

        firebaseAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference();
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddCar.this, MyCars.class);
                startActivity(i);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent i = new Intent(getApplicationContext(), Home1.class);
                startActivity(i);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String userID = user.getUid();

                String brand = txtBrand.getText().toString().trim();
                String model = txtModel.getText().toString().trim();

                if(rbPetrol.isChecked()) {
                    fuel = "Petrol";
                }
                else if(rbDiesel.isChecked()) {
                    fuel = "Diesel";
                }

                if (fuel != null) {
                    if ((TextUtils.isEmpty(brand)) && (TextUtils.isEmpty(model))) {
                        txtModel.setError("Please Enter your Car Model");
                        txtBrand.setError("Please Enter your Car Brand");
                    }
                    else if(TextUtils.isEmpty(brand)) {
                        txtBrand.setError("Please Enter your Car Brand");
                    }
                    else if(TextUtils.isEmpty(model)) {
                        txtModel.setError("Please Enter your Car Model");
                    }
                    else {
                        String f = fuel;

                        mReference.child("users").child(userID).child(model).child("brand").setValue(brand);
                        mReference.child("users").child(userID).child(model).child("model").setValue(model);
                        mReference.child("users").child(userID).child(model).child("fuel").setValue(f);

                        Intent i = new Intent(AddCar.this, MyCars.class);
                        startActivity(i);
                    }
                }
                else {
                    Toast.makeText(AddCar.this, "Please Select Fuel Type", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
