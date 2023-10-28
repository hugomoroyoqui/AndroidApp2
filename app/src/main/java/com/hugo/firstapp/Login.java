package com.hugo.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    TextInputEditText email, pass;
    Button login;

    TextView goToSignUp;

    private FirebaseAuth mAuth;

    CheckBox rem;

    SharedPreferences sp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = this.getSharedPreferences("UserCredentials", Context.MODE_PRIVATE);

        email = findViewById(R.id.lgEmail);
        pass = findViewById(R.id.lgPass);
        login = findViewById(R.id.btnLogin);
        goToSignUp = findViewById(R.id.btnWantAccount);
        rem = findViewById(R.id.checkBoxRemLogin);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginMethod(email.getText().toString(), pass.getText().toString());
            }
        });
    }

    private void LoginMethod(String emailText, String passText) {
        if (!emailText.isEmpty() && !passText.isEmpty()){
            if (passText.length() > 6){
                mAuth.signInWithEmailAndPassword(emailText, passText)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(Login.this, task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();

                                    updateUI(null);
                                }
                            }
                        });
            } else {
                Toast.makeText(this, "La contraseña debe ser mayor a 6 digítos.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Los campos estan vacios.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            sharedPrefMethod(email.getText().toString(), pass.getText().toString());
            emptyComponents();
            goToLoginMethod();
        }
    }

    private void sharedPrefMethod(String spE, String spP) {
        Boolean remember = rem.isChecked();

        // Almacenar informacion en SharedPreferences
        SharedPreferences.Editor editor = sp.edit();
        if (rem.isChecked()){
            editor.putString("loginEmail", spE);
            editor.putString("loginPass", spP);
            editor.putBoolean("loginRem", remember);
            editor.apply();
        }else {
            editor.clear();
            editor.apply();
        }
    }

    private void goToLoginMethod() {
        startActivity(new Intent(Login.this, Home.class));
    }

    private void emptyComponents() {
        email.setText("");
        pass.setText("");
        Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null && currentUser.isEmailVerified()){
            emptyComponents();
            goToLoginMethod();
        }
    }
}