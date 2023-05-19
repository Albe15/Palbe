package citas.ui.login;

import androidx.annotation.Nullable;

/**
 * Resultado de autenticación: éxito (detalles del usuario) o mensaje de error.
 */
class LoginResult {
    @Nullable
    public LoggedInUserView success;
    @Nullable
    private Integer error;

    /**
     * Constructor para el resultado de error.
     *
     * @param error Mensaje de error.
     */
    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    /**
     * Constructor para el resultado de éxito.
     *
     * @param success Detalles del usuario autenticado.
     */
    LoginResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    /**
     * Obtiene los detalles del usuario autenticado en caso de éxito.
     *
     * @return Detalles del usuario autenticado.
     */
    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }

    /**
     * Obtiene el mensaje de error en caso de fallo.
     *
     * @return Mensaje de error.
     */
    @Nullable
    Integer getError() {
        return error;
    }
}
