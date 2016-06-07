package com.example.adriana.babycare;

/**
 * Created by ariel on 6/6/2016.
 */
public class Respuesta {


    private int id;
    private String texto;
    private int fid_pregunta;
    private int peso;

    public Respuesta() {
    }

    public Respuesta(int id, String texto, int fid_pregunta, int peso) {
        this.id = id;
        this.texto = texto;
        this.fid_pregunta = fid_pregunta;
        this.peso = peso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getFid_pregunta() {
        return fid_pregunta;
    }

    public void setFid_pregunta(int fid_pregunta) {
        this.fid_pregunta = fid_pregunta;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
}
