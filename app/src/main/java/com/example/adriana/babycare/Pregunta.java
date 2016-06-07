package com.example.adriana.babycare;

import java.io.Serializable;

/**
 * Created by ariel on 30/5/2016.
 */
public class Pregunta implements Serializable {
    private String texto;
    private int id_Rango;
    private int id;
    private int id_Area;

    public Pregunta(String texto, int id_Rango, int id, int id_Area) {
        this.texto = texto;
        this.id_Rango = id_Rango;
        this.id = id;

        this.id_Area = id_Area;

    }

    public Pregunta() {
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getId_Rango() {
        return id_Rango;
    }

    public void setId_Rango(int id_Rango) {
        this.id_Rango = id_Rango;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getId_Area() {
        return id_Area;
    }

    public void setId_Area(int id_Area) {
        this.id_Area = id_Area;
    }
}
