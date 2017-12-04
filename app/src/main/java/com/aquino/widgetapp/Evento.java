package com.aquino.widgetapp;

/**
 * Created by Toshiba on 03/12/2017.
 */

public class Evento {

   private Integer id;
   private String nivel;
   private String fecha;
   private  String mensaje;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nivel='" + nivel + '\'' +
                ", fecha='" + fecha + '\'' +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
