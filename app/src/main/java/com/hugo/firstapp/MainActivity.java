package com.hugo.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Declaracion de variables globales
    TextView holamundo;
    Button btnExplicito;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("holamundo", "onCreate");

        // Inicializacion de variables y enlace a componente
        holamundo = findViewById(R.id.txtHolaMundo);
        btnExplicito = findViewById(R.id.btnExplicito);

        // Evento Click
        btnExplicito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Integracion de Intent Explicito
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
                //finish();  Se utiliza para destruir la actividad en contexto
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("holamundo", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("holamundo", "onResume");
        holamundo.setText("Hola mundo desde la clase Java en el metodo onResume");
    }
}