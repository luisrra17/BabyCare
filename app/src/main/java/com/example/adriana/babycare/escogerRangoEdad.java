package com.example.adriana.babycare;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class escogerRangoEdad extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_escoger_rango_edad);
    }
    public void irAVentanaPreguntas(View v){
        String rangoEdad = v.getTag().toString();
        System.out.println("------------------------------"+rangoEdad+"--------------");
        Intent i  = new Intent(escogerRangoEdad.this,MainActivity.class);
        i.putExtra("rangoEdad",rangoEdad);

        startActivity(i);

    }
}
