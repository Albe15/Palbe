package citas.data.model;

import android.content.Context;

public class Cita {

    private int id; // Identificador de la cita
    private String fecha; // Fecha de la cita
    private String descripcion; // Descripción de la cita
    private int usuarioId; // Identificador del usuario que ha reservado la cita
    private int especialidadId; // Identificador de la especialidad de la cita
    private int medicoId; // Identificador del médico asignado a la cita
    private Context context;

    // Constructor de una nueva cita sin identificador
    public Cita(String fecha, String descripcion, int usuarioId, int especialidadId, int medicoId) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.usuarioId = usuarioId;
        this.especialidadId = especialidadId;
        this.medicoId = medicoId;
    }

    // Constructor de una cita con identificador
    public Cita(int id, String fecha, String descripcion, int usuarioId, int especialidadId, int medicoId) {
        this.context = context;
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.usuarioId = usuarioId;
        this.especialidadId = especialidadId;
        this.medicoId = medicoId;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getUsuarioId() {return usuarioId;}
    public void setUsuarioId(int usuarioId) {this.usuarioId = usuarioId;}

    public int getEspecialidadId() {return especialidadId;}
    public void setEspecialidadId(int especialidadId) {this.especialidadId = especialidadId;}

    public int getMedicoId() {return medicoId;}
    public void setMedicoId(int medicoId) {this.medicoId = medicoId;}


}
