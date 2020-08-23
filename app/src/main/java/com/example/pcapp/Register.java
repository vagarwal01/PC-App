package com.example.pcapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class Register extends AppCompatActivity {

    EditText txtName, txtEmail, txtPassword, txtConfirmPassword;
    Button register;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtName = findViewById(R.id.name);
        txtConfirmPassword = findViewById(R.id.cnfPassword);
        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.password);
        register = findViewById(R.id.register);

        firebaseAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String confirmPassword = txtConfirmPassword.getText().toString().trim();

                if ((TextUtils.isEmpty(name)) && (TextUtils.isEmpty(email)) && (TextUtils.isEmpty(password)) && (TextUtils.isEmpty(confirmPassword))){
                    txtName.setError("Please Enter Name");
                    txtEmail.setError("Please Enter Email");
                    txtPassword.setError("Please Enter Password");
                    txtConfirmPassword.setError("Please Enter Confirm Password");
                }
                else if (TextUtils.isEmpty(name)){
                    txtName.setError("Please Enter Name");
                }
                else if (TextUtils.isEmpty(email)){
                    txtEmail.setError("Please Enter Email");
                }
                else if (TextUtils.isEmpty(password)){
                    txtPassword.setError("Please Enter Password");
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    txtEmail.setError("Please Enter Valid Email Address");
                }
                else if(password.length() < 6){
                    txtPassword.setError("Password Length Should Be Greater Than 6");
                }
                else if (password.length() > 12){
                    txtPassword.setError("Password Length Should Be Smaller Than 12");
                }
                else if (TextUtils.isEmpty(confirmPassword)){
                    txtConfirmPassword.setError("Please Enter Confirm Password");
                }
                else if (password.equals(confirmPassword)){
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getApplicationContext(), MyCars.class));
                                        Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(Register.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(Register.this, "Password Not Matched", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
