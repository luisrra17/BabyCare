package com.example.adriana.babycare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MainActivity extends Activity {
    public static int indicePreguntaG = -1;
    public static ArrayList<Pregunta> listaPreguntas2 = new ArrayList<>();
    public static ArrayList<Respuesta> listaRespuestas2 = new ArrayList<>();
    public static TextView textViewPregunta;
    public static TextView textViewTitulo;
    public static TextView textViewContadorPreguntas;
    public static Button botonBack;
    public static Button botonNext;
    public static ProgressBar progressBar;
    public static RadioGroup radioGroupOpciones;
    public static RadioButton radioButton1;
    public static RadioButton radioButton2;
    public static RadioButton radioButton3;
    public static RadioButton radioButton4;
    public static RadioButton radioButton5;
    public static Map<String,String> listaQandA = new HashMap<>();
    public static Paciente paciente;
    public static int puntajeUsuario = 0;
    Map<Integer, String> arregloAreas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        puntajeUsuario = 0;
        indicePreguntaG = -1;
        listaPreguntas2 = new ArrayList<>();
        listaRespuestas2 = new ArrayList<>();
        listaQandA = new HashMap<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String edadEnMeses = bundle.getString("edad");
        paciente = (Paciente) bundle.get("paciente");
        progressBar = (ProgressBar) findViewById(R.id.progressBarSesion);
        textViewPregunta = (TextView) findViewById(R.id.textViewPregunta);
        textViewTitulo = (TextView) findViewById(R.id.textViewTitulo);
        botonBack = (Button) findViewById(R.id.buttonBack);
        botonNext = (Button) findViewById(R.id.buttonNext);
        radioGroupOpciones = (RadioGroup) findViewById(R.id.radioGroupPreguntas);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        radioButton5 = (RadioButton) findViewById(R.id.radioButton5);
        textViewContadorPreguntas = (TextView) findViewById(R.id.textViewContadorPreguntas);

        Thread thread = new Thread(){
            @Override
            public void run() {

                final String resultado  = getComentarioByRangoEdad(edadEnMeses);
                arregloAreas = getAreas();
                final ArrayList<Pregunta> listaPreguntas = getPreguntasByRangoEdad(edadEnMeses);
                final ArrayList<Respuesta> listaRespuestas =getRespuestaByRangoEdad(edadEnMeses);

                listaPreguntas2.clear();
                listaRespuestas2.clear();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{

                        }catch (Exception ex){}

                        for (Pregunta pregunta:listaPreguntas){
                            listaPreguntas2.add(pregunta);

                        }
                        for (Respuesta respuesta:listaRespuestas){
                            listaRespuestas2.add(respuesta);

                        }

                        startActivity(new Intent(MainActivity.this,PopUp.class).putExtra("Texto",resultado));

                        progressBar.setVisibility(View.INVISIBLE);
                        cambiarPregunta();
                    }
                });
            }
        };
        thread.start();

        botonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int idSeleccionado = Integer.parseInt(((RadioButton) findViewById(radioGroupOpciones.getCheckedRadioButtonId())).getTag().toString());
                listaQandA.put(String.valueOf(getPreguntaActual().getId()),String.valueOf(idSeleccionado));
                if(terminePreguntas()){
                    Intent intent =new Intent(MainActivity.this,PopUpResultado.class);
                    intent.putExtra("resultado",calcularNota(puntajeUsuario));
                    String jsonAnswer = convertirEnJson(listaQandA,paciente.getId().toString());
                    intent.putExtra("QandA",jsonAnswer);
                    JSONObject nota = new JSONObject();
                    try{
                    nota.put("nota",String.valueOf(calcularNota(puntajeUsuario)));
                    nota.put("fid_paciente",String.valueOf(paciente.getId()));
                    }catch (Exception ex){}
                    intent.putExtra("nota", nota.toString());
                    startActivityForResult(intent,3);
                }
                else {

                    botonNext.setEnabled(false);
                    botonNext.setVisibility(View.INVISIBLE);


                    sumarPuntaje(idSeleccionado);
                    cambiarPregunta();
                    radioGroupOpciones.clearCheck();
                }

            }
        });
    }

    private String convertirEnJson(Map<String, String> map, String idPaciente) {
        String jsonString = "";
        JSONArray jsonArray = new JSONArray();

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fid_pregunta", pair.getKey());
                jsonObject.put("fid_respuesta", pair.getValue());
                jsonObject.put("fid_paciente", idPaciente);
                jsonArray.put(jsonObject);
            }catch (Exception ex){

            }
        }
        jsonString=jsonArray.toString();
        return jsonString;
    }

    private void sumarPuntaje(int idSeleccionado) {
        for(Respuesta respuesta:listaRespuestas2){
            if(respuesta.getId() == idSeleccionado){
                puntajeUsuario+=respuesta.getPeso();
            }
        }
    }

    private int calcularNota(int puntos){
        int notaFinal = 0;
        int pesosTotal = 0;
        for(Respuesta resp:listaRespuestas2){
            pesosTotal+=resp.getPeso();
        }
        notaFinal = (puntos*100)/pesosTotal;
        return notaFinal;
    }

    private ArrayList<Respuesta> getRespuestaByRangoEdad(String edadEnMeses) {
        ArrayList<Respuesta> listaRespuestas = new ArrayList<Respuesta>();
        String answer = "";

        HttpClient cliente = new DefaultHttpClient();
        HttpContext contexto = new BasicHttpContext();
        HttpGet httpGet = new HttpGet("http://babycaretec.hol.es/BabyCare/getRespuestasPorRango.php?id_rango="+edadEnMeses);
        try{
            HttpResponse response = cliente.execute(httpGet,contexto);
            HttpEntity entity  = response.getEntity();
            answer = EntityUtils.toString(entity,"UTF-8");
        }catch (Exception ex){

        }
        try{


            JSONArray json  = new JSONArray(answer);

            for (int i = 0; i<json.length();i++){
                Respuesta respuesta= new Respuesta();

                respuesta.setId(json.getJSONObject(i).getInt("id"));
                respuesta.setFid_pregunta(json.getJSONObject(i).getInt("fid_pregunta"));
                respuesta.setPeso(json.getJSONObject(i).getInt("peso"));
                respuesta.setTexto(json.getJSONObject(i).getString("texto"));

                listaRespuestas.add(respuesta);

            }
        }

        catch(Exception e){

        }

        return listaRespuestas;
    }

    public void onClickRadioButton(View radioView){
        RadioButton radioButtonEscogido = (RadioButton) findViewById(radioView.getId());
        botonNext.setEnabled(true);
        botonNext.setVisibility(View.VISIBLE);

    }


    private void cambiarPregunta() {
        indicePreguntaG++;
        String textoPregunta = "No hay preguntas";

        if(listaPreguntas2.size() >0){
            if(indicePreguntaG >= listaPreguntas2.size()){
                Toast.makeText(getApplicationContext(),"Última pregunta.",Toast.LENGTH_SHORT).show();
                System.out.println("Entre al TOAST "+indicePreguntaG);
            }
            else {
                System.out.println("Entre al de abajo "+indicePreguntaG);
                textoPregunta = listaPreguntas2.get(indicePreguntaG).getTexto();
                textViewTitulo.setText("Pregunta #" + (indicePreguntaG + 1) + " " + arregloAreas.get(listaPreguntas2.get(indicePreguntaG).getId_Area()));
                cambiarOpciones(listaPreguntas2.get(indicePreguntaG).getId());
                textViewPregunta.setText(textoPregunta);
                textViewContadorPreguntas.setText(String.valueOf((indicePreguntaG+1))+"/"+String.valueOf(listaPreguntas2.size()));
            }
        }
        else{
            System.out.println("Entre al ULTIMO "+indicePreguntaG);
        textViewPregunta.setText(textoPregunta);
        }

    }

    private void cambiarOpciones(int idPregunta) {
        int CantRespuestas = 0;
        for(Respuesta respuesta:listaRespuestas2){
            if(respuesta.getFid_pregunta()==idPregunta){
                CantRespuestas++;

                switch (CantRespuestas){
                    case 1:{
                        radioButton1.setText(respuesta.getTexto());
                        radioButton1.setVisibility(View.VISIBLE);
                        radioButton1.setEnabled(true);
                        radioButton1.setTag(respuesta.getId());

                        break;
                    }
                    case 2: {
                        radioButton2.setText(respuesta.getTexto());
                        radioButton2.setVisibility(View.VISIBLE);
                        radioButton2.setEnabled(true);
                        radioButton2.setTag(respuesta.getId());
                        break;
                    }
                    case 3: {
                        radioButton3.setText(respuesta.getTexto());
                        radioButton3.setVisibility(View.VISIBLE);
                        radioButton3.setEnabled(true);
                        radioButton3.setTag(respuesta.getId());
                        break;
                    }
                    case 4: {
                        radioButton4.setText(respuesta.getTexto());
                        radioButton4.setVisibility(View.VISIBLE);
                        radioButton4.setEnabled(true);
                        radioButton4.setTag(respuesta.getId());
                        break;
                    }
                    case 5: {
                        radioButton5.setText(respuesta.getTexto());
                        radioButton5.setVisibility(View.VISIBLE);
                        radioButton5.setEnabled(true);
                        radioButton5.setTag(respuesta.getId());
                        break;
                    }



                }
            }
        }
    }

    private boolean terminePreguntas(){
        boolean termine = false;
        if(indicePreguntaG+1 == listaPreguntas2.size()){
            termine = true;
        }
        return termine;
    }
    private Pregunta getPreguntaActual(){
        return listaPreguntas2.get(indicePreguntaG);
    }

    public static String getComentarioByRangoEdad(String rangoEdad){
        String comentario = "";
        String respuesta = "";
        HttpClient cliente = new DefaultHttpClient();
        HttpContext contexto = new BasicHttpContext();

        HttpGet httpGet = new HttpGet("http://babycaretec.hol.es/BabyCare/getComentariosPorRango.php?id_rango="+rangoEdad);

            try{
                HttpResponse response = cliente.execute(httpGet,contexto);
                HttpEntity entity  = response.getEntity();
                respuesta = EntityUtils.toString(entity,"UTF-8");
            }catch (Exception ex){

            }
        try{
            JSONArray json  = new JSONArray(respuesta);
            for (int i = 0; i<json.length();i++){
                comentario = json.getJSONObject(i).getString("texto");
            }
        }
        catch(Exception e){

        }
        return comentario;
    }


    public static Map<Integer, String> getAreas(){
        Map<Integer, String> arregloAreas = new HashMap<Integer, String>();
        String respuesta = "";
        int id = 0;
        String area = "";
        HttpClient cliente = new DefaultHttpClient();
        HttpContext contexto = new BasicHttpContext();

        HttpGet httpGet = new HttpGet("http://babycaretec.hol.es/BabyCare/getAreas.php");
        try{
            HttpResponse response = cliente.execute(httpGet,contexto);
            HttpEntity entity  = response.getEntity();
            respuesta = EntityUtils.toString(entity,"UTF-8");
        }catch (Exception ex){

        }
        try{
            JSONArray json  = new JSONArray(respuesta);
            for (int i = 0; i<json.length();i++){
                id = json.getJSONObject(i).getInt("id");
                area = json.getJSONObject(i).getString("nombre");
                arregloAreas.put(id,area);
            }
        }
        catch(Exception e){

        }
        return arregloAreas;
    }

    public static ArrayList<Pregunta> getPreguntasByRangoEdad(String rangoEdad){
        ArrayList<Pregunta> listaPreguntas = new ArrayList<Pregunta>();
        String respuesta = "";

        HttpClient cliente = new DefaultHttpClient();
        HttpContext contexto = new BasicHttpContext();
       HttpGet httpGet = new HttpGet("http://babycaretec.hol.es/BabyCare/getPreguntasPorRango.php?id_rango="+rangoEdad);
        try{
            HttpResponse response = cliente.execute(httpGet,contexto);
            HttpEntity entity  = response.getEntity();
            respuesta = EntityUtils.toString(entity,"UTF-8");
        }catch (Exception ex){

        }
        try{


            JSONArray json  = new JSONArray(respuesta);

            for (int i = 0; i<json.length();i++){
                Pregunta pregunta = new Pregunta();

                pregunta.setId(json.getJSONObject(i).getInt("id"));
                pregunta.setId_Area(json.getJSONObject(i).getInt("fid_area"));
                pregunta.setId_Rango(json.getJSONObject(i).getInt("fid_rango"));
                pregunta.setTexto(json.getJSONObject(i).getString("texto"));

                listaPreguntas.add(pregunta);

            }
        }

        catch(Exception e){

        }

        return listaPreguntas;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
          //  startActivity(new Intent(MainActivity.this, Menu.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {
                finish();
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();

    }
}
