package citas.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectoalberto.MainActivity;
import com.example.proyectoalberto.databinding.ActivityLoginBinding;

import citas.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private static DatabaseHelper dbHelper;

    private DatabaseHelper mDatabaseHelper;
    private Context mContext;

    public LoginActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar el diseño de la actividad de inicio de sesión
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializar el ayudante de la base de datos
        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Inicializar el ViewModel de inicio de sesión
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory(mContext, dbHelper))
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.userButton;
        final EditText passwordEditText = binding.passwordButton;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;

        // Observar el estado del formulario de inicio de sesión
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        // Observar el resultado del inicio de sesión
        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }


                // Ocultar la barra de progreso
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    // Mostrar un mensaje de error en caso de error de inicio de sesión
                    showLoginFailed(loginResult.getError());
                    Toast.makeText(getApplicationContext(), "Usuario y contraseña incorrectos", Toast.LENGTH_LONG).show();
                }
                if (loginResult.getSuccess() != null) {
                    // Actualizar la interfaz de usuario con los datos del usuario
                    updateUiWithUser(loginResult.getSuccess());
                    setResult(Activity.RESULT_OK);
                    // Iniciar la actividad principal una vez que el inicio de sesión sea exitoso
                    Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    // Completar y destruir la actividad de inicio de sesión una vez que se haya realizado con éxito
                    finish();
                }
            }
        });

        // Escuchar los cambios en los campos de texto de usuario y contraseña
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignorar
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignorar
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Notificar al ViewModel cuando cambien los datos de inicio de sesión
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Verificar si se ha presionado la acción "Done" en el teclado virtual
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Realizar el inicio de sesión utilizando los datos de usuario y contraseña ingresados
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar la barra de progreso durante el proceso de inicio de sesión
                loadingProgressBar.setVisibility(View.VISIBLE);
                // Iniciar el proceso de inicio de sesión utilizando los datos de usuario y contraseña ingresados
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = "Bienvenido";
        // Mensaje de inicio de sesion correcto
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        // Mostrar un mensaje de error al inicio de sesión fallido
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}