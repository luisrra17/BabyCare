package com.example.adriana.babycare;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class PopUpRegistroPaciente extends Activity {
    String primer_apellido ;
    String segundo_apellido ;
    String nombre ;
    String edad_años ;
    String edad_meses ;
    String genero ;
    String peso ;
    String altura ;
    String fid_encargado ;
    String anotaciones ;
    Button butonEmpezar;
    EditText editTextAnotaciones;
    ProgressBar progressBar;
    Usuario usuario;
    AnalyticsTracker analyticsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_registro_paciente);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        analyticsTracker = AnalyticsTracker.getAnalyticsTracker(this.getApplicationContext());
        getWindow().setLayout((int)(width*0.9) ,(int)(height*0.7));
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        usuario = (Usuario) bundle.get("usuario");
        primer_apellido= bundle.getString("primer_apellido");
        segundo_apellido= bundle.getString("segundo_apellido");
        nombre= bundle.getString("nombre");
        edad_años= bundle.getString("edad_años");
        edad_meses= bundle.getString("edad_meses");
        genero= bundle.getString("genero");
        peso= bundle.getString("peso");
        altura= bundle.getString("altura");
        fid_encargado= bundle.getString("fid_encargado");
        anotaciones= bundle.getString("anotaciones");
        butonEmpezar = (Button) findViewById(R.id.buttonComenzar);
        editTextAnotaciones = (EditText) findViewById(R.id.editTextAnotaciones);
        progressBar = (ProgressBar) findViewById(R.id.progressBarPaciente);
        butonEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                final Button boton = (Button) findViewById(view.getId());
                boton.setText("Registrando al menor...");
                boton.setEnabled(false);

                if(editTextAnotaciones.getText().toString().length() > 0){
                    anotaciones =editTextAnotaciones.getText().toString();
                }

                Thread thread = new Thread(){
                    @Override
                    public void run() {

                        final String idPaciente  = insertarNuevoPaciente(primer_apellido, segundo_apellido, nombre, edad_años, edad_meses, genero, peso, altura, fid_encargado, anotaciones);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                boton.setEnabled(true);
                                progressBar.setVisibility(View.INVISIBLE);
                                Paciente paciente = new Paciente(idPaciente,primer_apellido,segundo_apellido,nombre,edad_años,edad_meses,genero,peso,altura,fid_encargado,anotaciones);
                                String edadTotalMeses =Integer.toString(Integer.parseInt(paciente.getEdad_meses())*1 +Integer.parseInt(paciente.getEdad_años())*12);
                                Intent intent = new Intent(PopUpRegistroPaciente.this,MainActivity.class);
                                intent.putExtra("paciente",paciente);
                                intent.putExtra("usuario",usuario);
                                intent.putExtra("edad",edadTotalMeses);

                                Intent i = new Intent();
                                setResult(Activity.RESULT_OK, i);
                                analyticsTracker.trackEvent("Registro", "Registro de un paciente", "Se registró un paciente");
                                finish();

                                startActivity(intent);

                            }
                        });
                    }
                };
                thread.start();

            }
        });

    }

    private String insertarNuevoPaciente( String primer_apellido, String segundo_apellido, String nombre, String edad_años, String edad_meses, String genero, String peso, String altura, String fid_encargado, String anotaciones) {


        /**/
        String respuesta = "";
        HttpClient cliente = new DefaultHttpClient();
        HttpContext contexto = new BasicHttpContext();

        HttpPost httpPost = new HttpPost("http://babycaretec.hol.es/BabyCare/RegistroPaciente.php");
        HttpResponse response = null;
        try{
            List<NameValuePair> params = new ArrayList<NameValuePair>(10);

            params.add(new BasicNameValuePair("primer_apellido",primer_apellido));
            params.add(new BasicNameValuePair("segundo_apellido",segundo_apellido));
            params.add(new BasicNameValuePair("nombre",nombre));
            params.add(new BasicNameValuePair("edad_años",edad_años));
            params.add(new BasicNameValuePair("edad_meses",edad_meses));
            params.add(new BasicNameValuePair("genero",genero));
            params.add(new BasicNameValuePair("peso",peso));
            params.add(new BasicNameValuePair("altura",altura));
            params.add(new BasicNameValuePair("fid_encargado",fid_encargado));
            params.add(new BasicNameValuePair("anotaciones",anotaciones));
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            response = cliente.execute(httpPost,contexto);
            HttpEntity entity  = response.getEntity();
            respuesta = EntityUtils.toString(entity,"UTF-8");
        }catch (Exception ex){

        }

        return respuesta;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
