package com.example.tp5jr;

public class DetalleJuegos {
    private int id;
    private int imagen;
    private String nombre;
    private String descripcion;
    private String jugadores;
    private String compatibilidad;
    private String genero;
    private String idioma;
    private String clasificacion;

    public DetalleJuegos(int id, int imagen, String nombre, String descripcion, String jugadores,
                         String compatibilidad, String genero, String idioma, String clasificacion) {
        this.id = id;
        this.imagen = imagen;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.jugadores = jugadores;
        this.compatibilidad = compatibilidad;
        this.genero = genero;
        this.idioma = idioma;
        this.clasificacion = clasificacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImagen() { return imagen; }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getJugadores() {
        return jugadores;
    }

    public void setJugadores(String jugadores) {
        this.jugadores = jugadores;
    }

    public String getCompatibilidad() { return compatibilidad; }

    public void setCompatibilidad(String compatibilidad) {
        this.compatibilidad = compatibilidad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
}
