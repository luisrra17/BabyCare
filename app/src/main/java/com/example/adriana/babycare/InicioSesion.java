package com.example.adriana.babycare;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class InicioSesion extends Activity {
    Button botonEntrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_inicio_sesion);
        TextView textView = (TextView) findViewById(R.id.textViewRegistrate);
        botonEntrar = (Button) findViewById(R.id.buttonEntrar);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InicioSesion.this,Registro_Usuario.class));
            }
        });
    }
    public void onClickBotonEntrar(View view) {
        final Button botonEntrar = (Button) findViewById(view.getId());
        botonEntrar.setText("Entrando");
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarSesion);
        progressBar.setVisibility(View.VISIBLE);
        EditText correo = (EditText) findViewById(R.id.editTextEmail);
        EditText password = (EditText) findViewById(R.id.editTextPass);

        final String email = correo.getText().toString();
        final String contrasena = password.getText().toString();

        Thread thread = new Thread(){
            @Override
            public void run() {

                final String resultado  = verificarCredenciales(email,contrasena);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Ya termine, la vara termino con estado"+resultado,Toast.LENGTH_SHORT).show();;
                        if(resultado.equals("ERROR 01")){
                            Toast.makeText(getApplicationContext(),"Correo electrónico incorrecto. "+resultado,Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            botonEntrar.setText("Entrar");
                        }else if(resultado.equals("ERROR 02")){
                            Toast.makeText(getApplicationContext(),"Contraseña incorrecta. "+resultado,Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            botonEntrar.setText("Entrar");
                        }else{

                            progressBar.setVisibility(View.INVISIBLE);
                            Usuario usuario = new Usuario();
                            try{
                                JSONArray json  = new JSONArray(resultado);
                                for (int i = 0; i<json.length();i++){

                                    usuario.setId(json.getJSONObject(i).getString("id"));
                                    usuario.setNombre(json.getJSONObject(i).getString("nombre"));
                                    usuario.setPrimer_apellido(json.getJSONObject(i).getString("primer_apellido"));
                                    usuario.setSegundo_apellido(json.getJSONObject(i).getString("segundo_apellido"));
                                    usuario.setGenero(json.getJSONObject(i).getString("genero"));
                                    usuario.setTelefono(json.getJSONObject(i).getString("telefono"));
                                    usuario.setEmail(json.getJSONObject(i).getString("email"));
                                    usuario.setPassword(json.getJSONObject(i).getString("password"));
                                    usuario.setRoll(json.getJSONObject(i).getString("roll"));

                                }
                            }
                            catch (Exception ex){}

                            Intent intent = new Intent(InicioSesion.this,Menu.class);
                            intent.putExtra("usuario",usuario);
                            startActivity(intent);


                        }


                    }
                });
            }
        };
        thread.start();


    }



    private String verificarCredenciales(String email, String password) {

        System.out.println("----------------EMAIL:------ "+ email+" password "+password);
        /**/
        String respuesta = "";
        HttpClient cliente = new DefaultHttpClient();
        HttpContext contexto = new BasicHttpContext();

        HttpPost httpPost = new HttpPost("http://babycaretec.hol.es/BabyCare/inicioSesion.php");
        HttpResponse response = null;
        try{
            List<NameValuePair> params = new ArrayList<NameValuePair>(10);
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
