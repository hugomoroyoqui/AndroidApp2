package com.hugo.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class Intent2 extends AppCompatActivity {

    TextView txtName, txtAge, txtColor;
    ConstraintLayout layoutColor;
    CheckBox chxRemember;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent2);

        Intent intent = getIntent();

        txtAge = findViewById(R.id.txtIntent2Age);
        txtName = findViewById(R.id.txtIntent2Name);
        txtColor = findViewById(R.id.txtIntent2Color);
        chxRemember = findViewById(R.id.chxIntent2Remember);
        layoutColor = findViewById(R.id.Intent2ColorLayout);


        // Asignar valores a componentes

        txtName.setText(intent.getStringExtra("intentName"));

        String ageText = "No se encontro edad";
        if (intent.getIntExtra("intentAge", 0) != 0){
            ageText = "Edad: " + intent.getIntExtra("intentAge", 0);
        }

        txtAge.setText(ageText);

        txtColor.setText(intent.getStringExtra("intentColor"));

        chxRemember.setChecked(intent
                .getBooleanExtra("intentRemember", false));

        if (intent.getStringExtra("intentColor").contains("#")){
            try {
                layoutColor.setBackgroundColor(Color
                        .parseColor(intent.getStringExtra("intentColor")));
            } catch (Exception e){
                Toast.makeText(this, e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }




        /*String name = intent.getStringExtra("intentName");
        int age = intent.getIntExtra("intentAge", 0);

        Toast.makeText(Intent2.this,
                name + " = " + age, Toast.LENGTH_SHORT).show();*/
    }
}