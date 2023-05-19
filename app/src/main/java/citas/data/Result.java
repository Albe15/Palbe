package citas.data;

/**
 * Una clase genérica que contiene un resultado exitoso con datos o una excepción de error.
 */
public class Result<T> {

    // Se oculta el constructor privado para limitar los tipos de subclase (Success, Error)
    private Result() {}

    /**
     * Devuelve una representación en String de la instancia Result.
     * @return Una cadena que representa el resultado.
     */
    @Override
    public String toString() {
        if (this instanceof Result.Success) {
            Result.Success success = (Result.Success) this;
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof Result.Error) {
            Result.Error error = (Result.Error) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "";
    }

    /**
     * Subclase Success que indica un resultado exitoso con datos.
     * @param <T> El tipo de los datos contenidos en el resultado.
     */
    public final static class Success<T> extends Result {
        private T data;

        /**
         * Crea un nuevo objeto Success con datos.
         * @param data Los datos contenidos en el resultado.
         */
        public Success(T data) {
            this.data = data;
        }

        /**
         * Obtiene los datos contenidos en el resultado.
         * @return Los datos contenidos en el resultado.
         */
        public T getData() {
            return this.data;
        }
    }

    /**
     * Subclase Error que indica un resultado fallido con una excepción de error.
     */
    public final static class Error extends Result {
        private Exception error;

        /**
         * Crea un nuevo objeto Error con una excepción de error.
         * @param error La excepción de error contenida en el resultado.
         */
        public Error(Exception error) {
            this.error = error;
        }

        /**
         * Obtiene la excepción de error contenida en el resultado.
         * @return La excepción de error contenida en el resultado.
         */
        public Exception getError() {
            return this.error;
        }
    }
}
