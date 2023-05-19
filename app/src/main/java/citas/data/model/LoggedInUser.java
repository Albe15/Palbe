package citas.data.model;

/** * Clase de datos que captura información de usuario para usuarios registrados recuperada de LoginRepository */
public class LoggedInUser extends Usuario{

    private String displayName;
    private boolean isLogedIn;

    // Constructor de la clase, que hereda de la clase Usuario y agrega dos nuevos atributos: displayName e isLogedIn
    public LoggedInUser (int id, String nombre, String apellidos, String email, String dni, String user, String password, int rolId, int especialidadId, String rol, String especialidad, String displayName, boolean isLogedIn) {
        // Llama al constructor de la superclase para inicializar los atributos heredados
        super (id, nombre, apellidos, email, dni, user, password, rolId, especialidadId, rol, especialidad);

        // Inicializa los nuevos atributos
        this.displayName = nombre + " " + apellidos; // displayName se inicializa con el nombre y apellidos concatenados
        this.isLogedIn = false; // isLogedIn se inicializa en falso, ya que se supone que el usuario no está logueado al momento de crear la instancia
    }

    // Método getter para el atributo displayName
    public String getDisplayName() {
        return displayName;
    }

    // Método setter para el atributo isLogedIn
    public void setLogedIn(boolean isLogedIn) {
        this.isLogedIn = isLogedIn;
    }
}
