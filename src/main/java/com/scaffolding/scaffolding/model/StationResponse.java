package com.scaffolding.scaffolding.model;


import java.io.Serializable;


public class StationResponse implements Serializable {//se implementa el serializable para la transformación a objetos de los Json

    private String estacion;

    @Override
    public String toString() {
        return "Station{" +
                "estacion='" + estacion + '\'' +
                ", fecha='" + fecha + '\'' +
                ", nombre='" + nombre + '\'' +
                ", provincia='" + provincia + '\'' +
                ", tmed=" + tmed +
                ", tmin=" + tmin +
                ", tmax=" + tmax +
                '}';
    }

    private String fecha;
    private String nombre;
    private String provincia;
    private float tmed;
    private float tmin;
    private float tmax;

    public StationResponse(){

    }

    public StationResponse(String estacion, String fecha, String nombre, String provincia, float tmed, float tmin, float tmax) {
        this.estacion=estacion;
        this.fecha = fecha;
        this.nombre = nombre;
        this.provincia = provincia;
        this.tmed = tmed;
        this.tmin = tmin;
        this.tmax = tmax;
    }

    public String getEstacion() {
        return estacion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public float getTmed() {
        return tmed;
    }

    public float getTmin() {
        return tmin;
    }

    public float getTmax() {
        return tmax;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setTmed(float tmed) {
        this.tmed = tmed;
    }

    public void setTmin(float tmin) {
        this.tmin = tmin;
    }

    public void setTmax(float tmax) {
        this.tmax = tmax;
    }



}
