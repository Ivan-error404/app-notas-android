package com.ivan.appnotas;

public class Nota {

    private final int id;
    private final String titulo;
    private final String contenido;
    private final String fecha;

    public Nota(int id, String titulo, String contenido, String fecha) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public String getFecha() {
        return fecha;
    }
}
