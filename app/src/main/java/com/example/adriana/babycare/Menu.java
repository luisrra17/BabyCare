package com.example.adriana.babycare;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class Menu extends  Activity {
    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        usuario =(Usuario) bundle.get("usuario");


    }
    public void irADatosMenor(View v){

        startActivity(new Intent(Menu.this, Registro_Menor.class).putExtra("usuario",usuario));

    }
    public void irAAyuda(View v){
        String url = "https://drive.google.com/open?id=0B0Qhm-1UypPhRFgxZVVCZFRLVWc";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    public void irAQuienesSomos(View v){
        String url = "https://drive.google.com/open?id=0B0Qhm-1UypPhNzhwSms0a3VXZGc";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onResume() {
        super.onResume();
        // .... other stuff in my onResume ....
        this.doubleBackToExitPressedOnce = false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Presione atrás de nuevo para salir", Toast.LENGTH_SHORT).show();
    }


}
