package citas.data.model;

public class Rol {
    private int id;         // identificador del rol
    private String rol;     // nombre del rol

    // Constructor para crear un nuevo objeto Rol con el nombre del rol
    public Rol(String rol ) {
        this.rol = rol;
    }

    // Constructor para crear un nuevo objeto Rol con un identificador y el nombre del rol
    public Rol(int id, String rol) {
        this.id = id;
        this.rol = rol;
    }

    // Métodos getter y setter para el identificador del rol
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Método getter para el nombre del rol
    public String getRol() {
        return rol;
    }

    @Override
    public String toString() {
        return rol;
    }
}

