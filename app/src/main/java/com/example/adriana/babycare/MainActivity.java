package com.example.adriana.babycare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    public static int indicePreguntaG = -1;
    public static ArrayList<Pregunta> listaPreguntas2 = new ArrayList<>();
    public static TextView textViewPregunta;
    public static TextView textViewTitulo;
    public static ProgressBar progressBar;
    public static Button botonSi;
    public static Button botonNo;
    public static int puntajeUsuario = 0;
    Map<Integer, String> arregloAreas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String idRango = bundle.getString("rangoEdad");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textViewPregunta = (TextView) findViewById(R.id.textViewPregunta);
        textViewTitulo = (TextView) findViewById(R.id.textViewTitulo);
        botonSi = (Button) findViewById(R.id.buttonSi);
        botonNo = (Button) findViewById(R.id.buttonNo);

        Thread thread = new Thread(){
            @Override
            public void run() {

                final String resultado  = getComentarioByRangoEdad(idRango);
                final ArrayList<Pregunta> listaPreguntas = getPreguntasByRangoEdad(idRango);
                arregloAreas = getAreas();
                listaPreguntas2.clear();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (Pregunta pregunta:listaPreguntas){
                            listaPreguntas2.add(pregunta);
                            System.out.println("ID: "+pregunta.getId()+" Texto: " +pregunta.getTexto()
                                    +" id area "+pregunta.getId_Area()+" id rango "+pregunta.getId_Rango() +" peso "+pregunta.getPeso());
                        }

                        startActivity(new Intent(MainActivity.this,PopUp.class).putExtra("Texto",resultado));
                        System.out.println(resultado);
                        progressBar.setVisibility(View.INVISIBLE);
                        botonSi.setVisibility(View.VISIBLE);
                        botonNo.setVisibility(View.VISIBLE);
                        cambiarPregunta();
                    }
                });
            }
        };
        thread.start();


    }
    public void onClickBotonSiNo(View view){
        int respuesta = Integer.parseInt(view.getTag().toString());
        Pregunta preguntada = getPreguntaActual();

        if (respuesta == preguntada.getSeleccion_peso()){
            puntajeUsuario +=  preguntada.getPeso();
        }
        Toast.makeText(getApplicationContext(),"Puntaje: "+ puntajeUsuario,Toast.LENGTH_SHORT).show();
        if(!terminePreguntas()){
            cambiarPregunta();
        }else{
            Toast.makeText(getApplicationContext(),"Terminé. Puntaje: "+ getNotaFinal(puntajeUsuario),Toast.LENGTH_SHORT).show();
        }

    }
    private void cambiarPregunta() {
        indicePreguntaG++;
        String textoPregunta = "No hay preguntas";
        if(listaPreguntas2.size() >0){
            textoPregunta = listaPreguntas2.get(indicePreguntaG).getTexto();
        }
        textViewTitulo.setText("Pregunta #"+(indicePreguntaG+1)+" "+arregloAreas.get(listaPreguntas2.get(indicePreguntaG).getId_Area()));
        textViewPregunta.setText(textoPregunta);

    }
    public int getNotaFinal(int ptsUsuario){
        int notaFinal = 0;
        int sumPesos = 0;
            for(Pregunta pregunta:listaPreguntas2){
                sumPesos+=pregunta.getPeso();
            }
        notaFinal = (100*ptsUsuario)/sumPesos;
        return notaFinal;
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
        System.out.println("http://babycaretec.hol.es/BabyCare/getComentariosPorRango.php?id_rango="+rangoEdad);
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
        System.out.println("http://babycaretec.hol.es/BabyCare/getAreas.php");
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
        System.out.println("http://babycaretec.hol.es/BabyCare/getPreguntasPorRango.php?id_rango="+rangoEdad);
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
                pregunta.setPeso(json.getJSONObject(i).getInt("Peso"));
                pregunta.setTexto(json.getJSONObject(i).getString("texto"));
                pregunta.setSeleccion_peso(json.getJSONObject(i).getInt("seleccion_peso"));
                listaPreguntas.add(pregunta);
            }
        }
        catch(Exception e){

        }
        return listaPreguntas;
    }
}
