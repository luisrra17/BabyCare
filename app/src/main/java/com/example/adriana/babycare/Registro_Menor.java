package com.example.adriana.babycare;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class Registro_Menor extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registro_menor);
    }
}
