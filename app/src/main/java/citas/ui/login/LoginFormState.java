package citas.ui.login;

import androidx.annotation.Nullable;

/**
 * Estado de validación de datos del formulario de inicio de sesión.
 */
class LoginFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    private boolean isDataValid;

    /**
     * Constructor que indica los errores de nombre de usuario y contraseña.
     *
     * @param usernameError Error del nombre de usuario.
     * @param passwordError Error de la contraseña.
     */
    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    /**
     * Constructor que indica si los datos son válidos.
     *
     * @param isDataValid Indica si los datos son válidos.
     */
    LoginFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    /**
     * Obtiene el error del nombre de usuario.
     *
     * @return Error del nombre de usuario.
     */
    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    /**
     * Obtiene el error de la contraseña.
     *
     * @return Error de la contraseña.
     */
    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    /**
     * Verifica si los datos son válidos.
     *
     * @return true si los datos son válidos, false de lo contrario.
     */
    boolean isDataValid() {
        return isDataValid;
    }
}
