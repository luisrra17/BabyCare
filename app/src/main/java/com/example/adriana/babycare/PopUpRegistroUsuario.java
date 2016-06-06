package com.example.adriana.babycare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

public class PopUpRegistroUsuario extends Activity {
    Button botonEntendido;
    ProgressBar progressBar;
    TextView textViewAvisoRegistrando;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_registro_usuario);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String nombrePersona = bundle.getString("nombrePersona");
        final String primerApellido = bundle.getString("primerApellido");
        final String segundoApellido = bundle.getString("segundoApellido");
        final String email = bundle.getString("email");
        final String password = bundle.getString("password");
        final String cellphone = bundle.getString("cellphone");
        final String sex = bundle.getString("sex");
        botonEntendido = (Button) findViewById(R.id.buttonOk);
        progressBar = (ProgressBar) findViewById(R.id.progressBarSesion);
        textViewAvisoRegistrando = (TextView) findViewById(R.id.textViewAvisoRegistrando);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.7) ,(int)(height*0.5));

        Thread thread = new Thread(){
            @Override
            public void run() {

               final String resultado  = insertarNuevoUsuario(nombrePersona,primerApellido,segundoApellido,email,password,cellphone,sex);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                        botonEntendido.setVisibility(View.VISIBLE);
                        textViewAvisoRegistrando.setText("Usuario Registrado correctamente.");

                    }
                });
            }
        };
        thread.start();
        botonEntendido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PopUpRegistroUsuario.this,InicioSesion.class));
                finish();
            }
        });
    }

    private String insertarNuevoUsuario(String nombrePersona, String primerApellido, String segundoApellido, String email, String password, String cellphone, String sex) {


        /**/
        String respuesta = "";
        HttpClient cliente = new DefaultHttpClient();
        HttpContext contexto = new BasicHttpContext();
        System.out.println("http://babycaretec.hol.es/BabyCare/RegistroUsuario.php?primer_apellido="+primerApellido+"&segundo_apellido="+segundoApellido+"&nombre="+nombrePersona+"&genero="+sex+"&telefono="+cellphone+"&email="+email+"&password="+password);
        HttpPost httpPost = new HttpPost("http://babycaretec.hol.es/BabyCare/RegistroUsuario.php");
        HttpResponse response = null;
        try{
            List<NameValuePair> params = new ArrayList<NameValuePair>(10);
            params.add(new BasicNameValuePair("primer_apellido",primerApellido));
            params.add(new BasicNameValuePair("segundo_apellido",segundoApellido));
            params.add(new BasicNameValuePair("nombre",nombrePersona));
            params.add(new BasicNameValuePair("genero",sex));
            params.add(new BasicNameValuePair("telefono",cellphone));
            params.add(new BasicNameValuePair("email",email));
            params.add(new BasicNameValuePair("password",password));
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            response = cliente.execute(httpPost,contexto);
            HttpEntity entity  = response.getEntity();
            respuesta = EntityUtils.toString(entity,"UTF-8");
        }catch (Exception ex){

        }

        return respuesta;
    }
}
