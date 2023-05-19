package citas.ui.login;

/**
 * Clase que expone los detalles del usuario autenticado a la interfaz de usuario (UI).
 */
class LoggedInUserView {
    private String displayName; // Nombre para mostrar del usuario autenticado
    //... otros campos de datos que pueden ser accesibles para la interfaz de usuario

    /**
     * Constructor de la clase LoggedInUserView.
     * @param displayName El nombre para mostrar del usuario autenticado.
     */
    LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Obtiene el nombre para mostrar del usuario autenticado.
     * @return El nombre para mostrar del usuario autenticado.
     */
    String getDisplayName() {
        return displayName;
    }
}
