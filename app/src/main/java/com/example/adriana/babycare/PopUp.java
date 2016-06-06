package com.example.adriana.babycare;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PopUp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8) ,(int)(height*0.6));
        TextView textView = (TextView) findViewById(R.id.textComentario);
        textView.setHeight((int) (height*0.3));
        textView.setBackgroundColor(36666);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String texto = bundle.getString("Texto");
        textView.setText(texto);


    }
}
