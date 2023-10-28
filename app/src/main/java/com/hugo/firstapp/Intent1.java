package com.hugo.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Intent1 extends AppCompatActivity {

    // Declaracion
    EditText txtName, txtAge, txtColor;
    CheckBox cbxRemember;
    Button sendButton;

    SharedPreferences sp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent1);

        sp = this.getSharedPreferences("UserPreference", Context.MODE_PRIVATE);

        // Inicializacion
        txtName = findViewById(R.id.txtIntentName);
        txtAge = findViewById(R.id.txtIntentAge);
        txtColor = findViewById(R.id.txtIntentColor);
        cbxRemember = findViewById(R.id.ckbIntentRemember);
        sendButton = findViewById(R.id.btnIntentSend);

        //
        txtName.setText(sp.getString("intentName", ""));
        txtAge.setText("" + sp.getInt("intentAge", 0));
        txtColor.setText(sp.getString("intentColor", "#000000"));
        cbxRemember.setChecked(sp.getBoolean("intentRemember", true));

        // Evento Click
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Metodo para obtener la informacion
                sendInfo();
            }
        });
    }

    private void sendInfo(){
        // Variables locales
        String name, color;
        int age;
        boolean remember;

        // Obtener contenido de componentes
        name = txtName.getText().toString();
        color = txtColor.getText().toString();
        age = Integer.parseInt(txtAge.getText().toString());
        remember = cbxRemember.isChecked();

        // Almacenar informacion en SharedPreferences
        SharedPreferences.Editor editor = sp.edit();
        if (cbxRemember.isChecked()){
            editor.putString("intentName", name);
            editor.putInt("intentAge", age);
            editor.putString("intentColor", color);
            editor.putBoolean("intentRemember", remember);
            editor.apply();
        }else {
            editor.clear();
            editor.apply();
        }

        // Enviar Info a otra pantalla
        Intent intent = new Intent(Intent1.this, Intent2.class);
        intent.putExtra("intentName", name);
        intent.putExtra("intentAge", age);
        intent.putExtra("intentColor", color);
        intent.putExtra("intentRemember", remember);
        startActivity(intent);
    }

}