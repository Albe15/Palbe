package citas.data;

import citas.data.model.LoggedInUser;

/**
 * Clase que solicita autenticación e información del usuario desde la fuente de datos remota y
 * mantiene en caché en memoria el estado de inicio de sesión y la información de credenciales del usuario.
 */
public class LoginRepository {

    // Singleton para asegurarnos de que solo tengamos una instancia de la clase en ejecución
    private static volatile LoginRepository instance;

    private LoginDataSource dataSource; // Instancia de la clase LoginDataSource

    // Si las credenciales del usuario se van a almacenar en caché en el almacenamiento local, se recomienda cifrar
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null; // Objeto que representa al usuario autenticado actualmente

    // Constructor privado: acceso singleton
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Método para obtener la instancia de la clase LoginRepository
    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) { // Si la instancia es nula, la creamos
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    // Método que devuelve true si hay un usuario autenticado actualmente
    public boolean isLoggedIn() {
        return user != null;
    }

    // Método para cerrar sesión
    public void logout() {
        user = null;
        dataSource.logout();
    }

    // Método para establecer el usuario autenticado actualmente
    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // Si las credenciales del usuario se van a almacenar en caché en el almacenamiento local, se recomienda cifrar
        // @see https://developer.android.com/training/articles/keystore
    }

    public LoggedInUser getCurrentLoggedUser(){
        return this.user;
    }

    // Método que maneja la autenticación del usuario
    public Result<LoggedInUser> login(String username, String password) {
        Result<LoggedInUser> result = dataSource.login(username, password); // Llamamos al método login de LoginDataSource

        if (result instanceof Result.Success) { // Si el resultado es un éxito
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData()); // Establecemos el usuario autenticado actualmente
        }
        return result; // Devolvemos el resultado
    }
}
