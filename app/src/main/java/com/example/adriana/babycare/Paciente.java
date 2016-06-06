package com.example.adriana.babycare;

import java.io.Serializable;

/**
 * Created by ariel on 5/6/2016.
 */
public class Paciente implements Serializable{
    private String id;
    private String primer_apellido;
    private String segundo_apellido;
    private String nombre;
    private String edad_años;
    private String edad_meses;
    private String genero;
    private String peso;
    private String altura;
    private String fid_encargado;
    private String anotaciones;

    public Paciente() {
    }

    public Paciente(String id ,String primer_apellido, String segundo_apellido, String nombre, String edad_años, String edad_meses, String genero, String peso, String altura, String fid_encargado, String anotaciones) {
        this.id = id;
        this.primer_apellido = primer_apellido;
        this.segundo_apellido = segundo_apellido;
        this.nombre = nombre;
        this.edad_años = edad_años;
        this.edad_meses = edad_meses;
        this.genero = genero;
        this.peso = peso;
        this.altura = altura;
        this.fid_encargado = fid_encargado;
        this.anotaciones = anotaciones;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrimer_apellido() {
        return primer_apellido;
    }

    public void setPrimer_apellido(String primer_apellido) {
        this.primer_apellido = primer_apellido;
    }

    public String getSegundo_apellido() {
        return segundo_apellido;
    }

    public void setSegundo_apellido(String segundo_apellido) {
        this.segundo_apellido = segundo_apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad_años() {
        return edad_años;
    }

    public void setEdad_años(String edad_años) {
        this.edad_años = edad_años;
    }

    public String getEdad_meses() {
        return edad_meses;
    }

    public void setEdad_meses(String edad_meses) {
        this.edad_meses = edad_meses;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getFid_encargado() {
        return fid_encargado;
    }

    public void setFid_encargado(String fid_encargado) {
        this.fid_encargado = fid_encargado;
    }

    public String getAnotaciones() {
        return anotaciones;
    }

    public void setAnotaciones(String anotaciones) {
        this.anotaciones = anotaciones;
    }
}
