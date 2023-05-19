package citas.data.model;

public class Especialidad {
    private int id; // id de la especialidad
    private String especialidad; // nombre de la especialidad

    // Constructor de la clase Especialidad sin id (utilizado para crear nuevas especialidades)
    public Especialidad(String especialidad ) {
        this.especialidad = especialidad;
    }

    // Constructor de la clase Especialidad con id (utilizado para recuperar especialidades existentes)
    public Especialidad(int id, String especialidad) {
        this.id = id;
        this.especialidad = especialidad;
    }

    // Métodos getter y setter para el atributo id
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    // Método getter para el atributo especialidad
    public String getEspecialidad() {return especialidad;}

    @Override
    public String toString() {
        return especialidad;
    }
}
