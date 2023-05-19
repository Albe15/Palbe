package citas.data;

import java.io.IOException;

import citas.DatabaseHelper;
import citas.data.model.LoggedInUser;
import citas.data.model.Usuario;

/**
 * Clase que maneja la autenticación con las credenciales de inicio de sesión y recupera información del usuario.
 */
public class LoginDataSource {
    // Atributo que se utiliza para acceder a la base de datos.
    private DatabaseHelper mDatabaseHelper;
    public LoginDataSource(DatabaseHelper databaseHelper) {
        mDatabaseHelper = databaseHelper;
    }

    /**
     * Método que toma un nombre de usuario y una contraseña como parámetros y devuelve un objeto "Resultado" que contiene información del usuario si la autenticación es exitosa, o un error si no lo es.
     */
    public Result<LoggedInUser> login(String username, String password) {
        try {
            // Se intenta validar las credenciales del usuario con el método "isValidCredentials" del objeto "DatabaseHelper".
            Usuario user = mDatabaseHelper.isValidCredentials(username, password);

            if (user != null) {
                // Si las credenciales son válidas, se crea un objeto "LoggedInUser" con la información del usuario y se establece como "logueado".
                LoggedInUser logedUser = new LoggedInUser(user.getId(), user.getNombre(), user.getApellidos(), user.getEmail(), user.getDni(),user.getUser(), user.getPassword(), user.getRolId(), user.getEspecialidadId(), user.getRol(),user.getEspecialidad(), null, false);
                logedUser.setLogedIn(true);
                // Finalmente, se devuelve un objeto "Resultado" con el usuario autenticado.
                return new Result.Success<>(logedUser);
            } else {
                // Si las credenciales no son válidas, se lanza una excepción con un mensaje de error.
                throw new RuntimeException("No se ha encontrado el usuario");
            }
        } catch (Exception e) {
            // Si se produce alguna excepción durante la autenticación, se devuelve un objeto "Resultado" con el error.
            return new Result.Error(new IOException("Error al iniciar sesión", e));
        }
    }

    /**
     * Método que se encarga de revocar la autenticación del usuario.
     */
    public void logout() {
        // TODO: revoke authentication
        // Actualmente este método no hace nada, ya que aún no se ha implementado la funcionalidad de revocar la autenticación.
    }
}
