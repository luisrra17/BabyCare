package com.example.adriana.babycare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Registro_Menor extends Activity {
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
    Usuario usuario;
    EditText EditNombre ;
    EditText EditApellidoP ;
    EditText EditApellidoS ;
    EditText EditEdadAnos ;
    EditText EditEdadMeses ;
    RadioGroup RadioButtonGenero;
    EditText EditPeso ;
    EditText EditAltura ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registro_menor);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        usuario = (Usuario) bundle.get("usuario");
        EditNombre =(EditText) findViewById(R.id.editTextNombre) ;
        EditApellidoP =(EditText) findViewById(R.id.editTextApellidoP)  ;
        EditApellidoS =(EditText) findViewById(R.id.editTextApellidoS)  ;
        EditEdadAnos =(EditText) findViewById(R.id.editTextAnos)  ;
        EditEdadMeses = (EditText) findViewById(R.id.editTextMeses) ;
        RadioButtonGenero= (RadioGroup) findViewById(R.id.radioGroupGenero) ;
        EditPeso =(EditText) findViewById(R.id.editTextPeso)  ;
        EditAltura =(EditText) findViewById(R.id.editTextAltura)  ;



        Button buttonSiguiente = (Button)findViewById(R.id.buttonSiguiente);
        buttonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                primer_apellido = EditApellidoP.getText().toString() ;
                segundo_apellido =EditApellidoS.getText().toString();
                nombre =EditNombre.getText().toString() ;
                edad_años =EditEdadAnos.getText().toString();
                edad_meses =EditEdadMeses.getText().toString();
                genero = ((RadioButton) findViewById(RadioButtonGenero.getCheckedRadioButtonId())).getText().toString();
                peso = EditPeso.getText().toString();
                altura  = EditAltura.getText().toString();
                fid_encargado = usuario.getId() ;
                anotaciones="NULL" ;

                int camposVacios= primer_apellido.length()*segundo_apellido.length()*nombre.length()*edad_años.length()*edad_meses.length()*genero.length()*peso.length()*altura.length()*fid_encargado.length()*anotaciones.length();
                if(camposVacios>0) {
                    Intent intent = new Intent(Registro_Menor.this, PopUpRegistroPaciente.class);
                    intent.putExtra("usuario",usuario);
                    intent.putExtra("primer_apellido", primer_apellido);
                    intent.putExtra("segundo_apellido", segundo_apellido);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("edad_años", edad_años);
                    intent.putExtra("edad_meses", edad_meses);
                    intent.putExtra("genero", genero);
                    intent.putExtra("peso", peso);
                    intent.putExtra("altura", altura);
                    intent.putExtra("fid_encargado", fid_encargado);
                    intent.putExtra("anotaciones", anotaciones);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Debe llenar todos los campos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
