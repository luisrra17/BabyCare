package com.example.adriana.babycare;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PopUpResultado extends Activity {
    int resultado;
    TextView notaFinal;
    public static  Button enviarResultad;
    String json;
    String jsonNota ;
    TextView textViewRecomendacion;
    boolean envioresultados = false;
    public  static ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_up_resultado);
        textViewRecomendacion = (TextView) findViewById(R.id.textViewRecomendacion);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        envioresultados = false;
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8) ,(int)(height*0.8));
        notaFinal = (TextView) findViewById(R.id.textViewNotaFinal);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        resultado = bundle.getInt("resultado");
        json = bundle.getString("QandA");
        jsonNota = bundle.getString("nota");
        System.out.println(json);
        System.out.println(jsonNota);
        notaFinal.setText(resultado + "%");
        enviarResultad = (Button) findViewById(R.id.buttonEnviarResultados);
        progressBar = (ProgressBar) findViewById(R.id.progressBarEnviarResultado);
        enviarResultad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(envioresultados==false) {
                   progressBar.setVisibility(View.VISIBLE);
                   enviarResultad.setText("Enviando los resultados...");
                   enviarResultad.setEnabled(false);

                   Thread thread = new Thread() {
                       @Override
                       public void run() {

                           final String idPaciente = insertarNuevoResultado(json, jsonNota);
                           final String recomendaciones = getRecomendacionPorNota(String.valueOf(resultado));
                           System.out.println("-----------------RECOMENDACIONES:---------- "+recomendaciones);
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   enviarResultad.setEnabled(true);
                                   progressBar.setVisibility(View.INVISIBLE);
                                   textViewRecomendacion.setText(recomendaciones);
                                   textViewRecomendacion.setVisibility(View.VISIBLE);
                                   envioresultados = true;
                                   enviarResultad.setText("Entendido");

                               }
                           });
                       }
                   };
                   thread.start();
               }else{
                   Intent i = new Intent();
                   setResult(Activity.RESULT_OK, i);
                   finish();
                 //  startActivity(new Intent(PopUpResultado.this,Menu.class));
               }
            }
        });


    }

    private String getRecomendacionPorNota(String calificacion) {
        String respuesta = "";
        HttpClient cliente = new DefaultHttpClient();
        HttpContext contexto = new BasicHttpContext();

        HttpPost httpPost = new HttpPost("http://babycaretec.hol.es/BabyCare/getRecomendacionPorNota.php");
        HttpResponse response = null;
        try{
            List<NameValuePair> params = new ArrayList<NameValuePair>(10);

            params.add(new BasicNameValuePair("calificacion",calificacion));
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            response = cliente.execute(httpPost,contexto);
            HttpEntity entity  = response.getEntity();
            respuesta = EntityUtils.toString(entity,"UTF-8");
        }catch (Exception ex){

        }

        return respuesta;
    }

    private String insertarNuevoResultado(String json, String jsonNota) {

        String respuesta = "";
        HttpClient cliente = new DefaultHttpClient();
        HttpContext contexto = new BasicHttpContext();

        HttpPost httpPost = new HttpPost("http://babycaretec.hol.es/BabyCare/RegistroResultado.php");
        HttpResponse response = null;
        try{
            List<NameValuePair> params = new ArrayList<NameValuePair>(10);

            params.add(new BasicNameValuePair("json",json));
            params.add(new BasicNameValuePair("jsonNota",jsonNota));
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            response = cliente.execute(httpPost,contexto);
            HttpEntity entity  = response.getEntity();
            respuesta = EntityUtils.toString(entity,"UTF-8");
        }catch (Exception ex){

        }

        return respuesta;
    }
}
