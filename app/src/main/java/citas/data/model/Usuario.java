package citas.data.model;

public class Usuario {
    private int id;
    private String nombre;
    private String apellidos;
    private String email;
    private String dni;
    private String user;
    private String password;

    // Estos dos campos corresponden a la relación del usuario con el rol y la especialidad
    private int rolId;
    private int especialidadId;
    private String rol;
    private  String especialidad;

    public Usuario(int id, String nombre, String apellidos, String email, String dni, String user, String password, int rolId, int especialidadId, String rol, String especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.dni = dni;
        this.user = user;
        this.password = password;
        this.rolId = rolId;
        this.especialidadId = especialidadId;
        this.rol = rol;
        this.especialidad = especialidad;
    }

    // Getters y setters para cada uno de los campos
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {this.email = email;}

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getRolId() {
        return rolId;
    }
    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public int getEspecialidadId() {
        return especialidadId;
    }
    public void setEspecialidadId(int especialidadId) {
        this.especialidadId = especialidadId;
    }

    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEspecialidad() {
        return especialidad;
    }
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
