package com.example.adriana.babycare;

import java.io.Serializable;

/**
 * Created by ariel on 3/6/2016.
 */
public class Usuario implements Serializable {

    private String id;
    private String primer_apellido;
    private String segundo_apellido;
    private String nombre;
    private String genero;
    private String telefono;
    private String email;
    private String password;
    private String roll;

    public Usuario() {
    }

    public Usuario(String id, String primer_apellido, String segundo_apellido, String nombre, String genero, String telefono, String email, String password, String roll) {
        this.id = id;
        this.primer_apellido = primer_apellido;
        this.segundo_apellido = segundo_apellido;
        this.nombre = nombre;
        this.genero = genero;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.roll = roll;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }
}
