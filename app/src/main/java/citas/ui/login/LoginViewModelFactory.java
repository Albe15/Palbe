package citas.ui.login;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import citas.DatabaseHelper;
import citas.data.LoginDataSource;
import citas.data.LoginRepository;

/**
 * Fábrica de proveedores de ViewModel para instanciar LoginViewModel.
 * Requerido ya que LoginViewModel tiene un constructor no vacío.
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private DatabaseHelper mDatabaseHelper;
    private Context mContext;

    /**
     * Constructor de LoginViewModelFactory.
     *
     * @param context         Contexto de la aplicación.
     * @param databaseHelper  Ayudante de la base de datos.
     */
    public LoginViewModelFactory(Context context, DatabaseHelper databaseHelper) {
        mContext = context;
        mDatabaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            // Crea una instancia de LoginViewModel utilizando el repositorio y el origen de datos correspondientes
            return (T) new LoginViewModel(LoginRepository.getInstance(new LoginDataSource(mDatabaseHelper)));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
