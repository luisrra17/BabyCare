package com.example.adriana.babycare;

import java.io.Serializable;

/**
 * Created by ariel on 30/5/2016.
 */
public class Pregunta implements Serializable {
    private String texto;
    private int id_Rango;
    private int id;
    private int peso;
    private int id_Area;
    private int seleccion_peso;

    public Pregunta(String texto, int id_Rango, int id, int peso, int id_Area, int seleccion_peso) {
        this.texto = texto;
        this.id_Rango = id_Rango;
        this.id = id;
        this.peso = peso;
        this.id_Area = id_Area;
        this.seleccion_peso = seleccion_peso;
    }

    public Pregunta() {
    }

    public int getSeleccion_peso() {
        return seleccion_peso;
    }

    public void setSeleccion_peso(int seleccion_peso) {
        this.seleccion_peso = seleccion_peso;
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

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getId_Area() {
        return id_Area;
    }

    public void setId_Area(int id_Area) {
        this.id_Area = id_Area;
    }
}
