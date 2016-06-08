package com.example.adriana.babycare;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
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
                Intent intent=new Intent(Registro_Usuario.this,PopUpRegistroUsuario.class);
                intent.putExtra("nombrePersona",nombrePersona);
                intent.putExtra("primerApellido",primerApellido);
                intent.putExtra("segundoApellido",segundoApellido);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                intent.putExtra("cellphone",cellphone);
                intent.putExtra("sex",sex);
                startActivityForResult(intent,1);


            }else{
                Toast.makeText(getApplicationContext(),"Las contraseñas deben coincidir",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Asegurese de llenar todos los espacios correctamente",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                finish();
            }if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),"Ya ha sido registrado un usuario con este correo electrónico",Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            startActivity(new Intent(Registro_Usuario.this, InicioSesion.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }




    }
