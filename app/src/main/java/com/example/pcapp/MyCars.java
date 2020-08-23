package com.example.pcapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyCars extends AppCompatActivity {

    ListView listViewCars;
    Button btnAdd;
    ImageView btnLogout, btnBack;

    DatabaseReference firebaseReference;
    FirebaseAuth firebaseAuth;
    String uid;
    String key;

    List<Car> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cars);

        btnBack = findViewById(R.id.btnBack);
        btnLogout = findViewById(R.id.btnLogout);
        btnAdd = findViewById(R.id.btnAdd);
        listViewCars = findViewById(R.id.listViewCars);
        carList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        uid = user.getUid();
        firebaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent i = new Intent(MyCars.this, Home1.class);
                startActivity(i);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyCars.this, Home2.class);
                startActivity(i);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyCars.this, AddCar.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected  void onStart() {
        super.onStart();

        firebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                carList.clear();
                for (DataSnapshot carSnapshot : dataSnapshot.getChildren()) {
                    Car car = carSnapshot.getValue(Car.class);
                    key = String.valueOf(carSnapshot.getKey());
                    car.setKey(key);
                    carList.add(car);
                }
                if(carList.isEmpty()) {
                    Toast.makeText(MyCars.this, "You have no cars registered", Toast.LENGTH_SHORT).show();
                }

                CarList adapter = new CarList(MyCars.this, carList);
                listViewCars.setAdapter(adapter);

                listViewCars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        String type = ((TextView) view.findViewById(R.id.txtFuel)).getText().toString();
                        if(type.equals("Petrol")) {
                            Toast.makeText(MyCars.this, "Petrol Car", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MyCars.this, Info.class);
                            intent.putExtra("data", "petrol");
                            startActivity(intent);
                        }
                        else if(type.equals("Diesel")) {
                            Toast.makeText(MyCars.this, "Diesel Car", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MyCars.this, Info.class);
                            intent.putExtra("data", "diesel");
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(MyCars.this, "fuel type not specified", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
