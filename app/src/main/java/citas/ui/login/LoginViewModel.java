package citas.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyectoalberto.R;

import citas.data.LoginRepository;
import citas.data.Result;
import citas.data.model.LoggedInUser;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    /**
     * Constructor de LoginViewModel.
     *
     * @param loginRepository Repositorio de inicio de sesión.
     */
    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    /**
     * Obtiene el estado del formulario de inicio de sesión.
     *
     * @return LiveData del estado del formulario de inicio de sesión.
     */
    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    /**
     * Obtiene el resultado del inicio de sesión.
     *
     * @return LiveData del resultado del inicio de sesión.
     */
    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    /**
     * Inicia sesión con el nombre de usuario y la contraseña proporcionados.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña.
     */
    public void login(String username, String password) {
        // Se puede ejecutar en un trabajo asíncrono separado
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    /**
     * Valida los datos del formulario de inicio de sesión cuando cambian.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña.
     */
    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    /**
     * Verifica si el nombre de usuario es válido.
     *
     * @param username Nombre de usuario.
     * @return true si el nombre de usuario es válido, false de lo contrario.
     */
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    /**
     * Verifica si la contraseña es válida.
     *
     * @param password Contraseña.
     * @return true si la contraseña es válida, false de lo contrario.
     */
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 4;
    }
}
