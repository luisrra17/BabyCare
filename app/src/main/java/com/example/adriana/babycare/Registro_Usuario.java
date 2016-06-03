package com.example.adriana.babycare;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Registro_Usuario extends Activity {

    /*
    * http://babycaretec.hol.es/BabyCare/RegistroUsuario.php?primer_apellido=Montestel&segundo_apellido=Ramos&nombre=Maureen&genero=Femenino&telefono=60565031&email=mpmonestel@mail.com&password=1234
    * */
    public EditText nombre ;
    public EditText apellido1 ;
    public EditText apellido2 ;
    public EditText correoElectronico ;
    public EditText contrasena ;
    public EditText repetirContrasena ;
    public EditText telefono ;
    public RadioGroup genero ;
    public Button botonEnviar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registro_usuario);
        nombre =  (EditText) findViewById(R.id.editTextNombre);
        apellido1 =  (EditText) findViewById(R.id.editTextApellidoP);
        apellido2 =  (EditText) findViewById(R.id.editTextApellidoS);
        correoElectronico =  (EditText) findViewById(R.id.editTextCorreo);
        contrasena =  (EditText) findViewById(R.id.editTextPass);
        repetirContrasena = (EditText) findViewById(R.id.editTextRepeatPass);
        telefono =  (EditText) findViewById(R.id.editTextNumCel);
        genero = (RadioGroup) findViewById(R.id.radioGroupGenero);



    }

    public void onClickBotonEnviar(View view) {
        final String nombrePersona = nombre.getText().toString();
        final String primerApellido = apellido1.getText().toString();
        final String segundoApellido = apellido2.getText().toString();
        final String email = correoElectronico.getText().toString();
        final String password = contrasena.getText().toString();
        String repPassword = repetirContrasena.getText().toString();
        final String cellphone = telefono.getText().toString();
        final String sex =  ((RadioButton) findViewById(genero.getCheckedRadioButtonId())).getText().toString();
        final int textoVacio = nombrePersona.length()*primerApellido.length()*segundoApellido.length()*email.length()*password.length()*repPassword.length()*cellphone.length();
        System.out.println(nombrePersona+primerApellido+segundoApellido+email+password+repPassword+cellphone+sex);
        if(textoVacio>0  ){
            System.out.println("HOLAA");
            if(password.equals(repPassword)){
                System.out.println("ADIOSSSS");
                Thread thread = new Thread(){
                    @Override
                    public void run() {

                        final String resultado  = insertarNuevoUsuario(nombrePersona,primerApellido,segundoApellido,email,password,cellphone,sex);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"Ya termine, la vara termino con estado"+resultado,Toast.LENGTH_SHORT).show();;
                            }
                        });
                    }
                };
                thread.start();



            }else{

            }
        }
        else{

        }

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
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            response = cliente.execute(httpPost,contexto);
        }catch (Exception ex){

        }

        return respuesta;
    }
}
