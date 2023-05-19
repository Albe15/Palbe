package com.example.proyectoalberto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoalberto.databinding.UserLayoutBinding;

import java.util.ArrayList;

import citas.DatabaseHelper;
import citas.data.LoginDataSource;
import citas.data.LoginRepository;
import citas.data.model.Especialidad;
import citas.data.model.LoggedInUser;
import citas.data.model.Rol;

public class AddUserActivity extends AppCompatActivity {
    private UserLayoutBinding binding;
    private LoginRepository loginRepository;
    private DatabaseHelper dbHelper;
    private LoggedInUser loggedUser;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_layout);

        dbHelper = new DatabaseHelper(this);
        loginRepository= LoginRepository.getInstance(new LoginDataSource(dbHelper));
        UserLayoutBinding binding = UserLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Obtiene los datos del usuario logado
        this.loggedUser=loginRepository.getCurrentLoggedUser();

        ArrayList listaRoles = this.dbHelper.getRolesList();
        listaRoles.add(0,"Seleccione");
        ArrayAdapter<Rol> adapterRoles = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listaRoles);
        Spinner spinnerRoles = binding.userRol;
        spinnerRoles.setAdapter(adapterRoles);

        ArrayList listaEspecialidades = this.dbHelper.getEspecialidadesList();
        listaEspecialidades.add(0, "Seleccione");
        ArrayAdapter<Especialidad> adapterEspecialidades = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listaEspecialidades);
        Spinner spinnerEspecialidades = binding.userEspecialidad;
        spinnerEspecialidades.setAdapter(adapterEspecialidades);
        spinnerEspecialidades.setVisibility(View.GONE);
        binding.textViewUser5.setVisibility(View.GONE);

        spinnerRoles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 1) {
                    spinnerEspecialidades.setVisibility(View.VISIBLE);
                    binding.textViewUser5.setVisibility(View.VISIBLE);
                } else {
                    spinnerEspecialidades.setVisibility(View.GONE);
                    binding.textViewUser5.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

        Button btnAddUser = binding.btnViewUser0;
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Comprobamos que todos los datos son correctos
                String nombre = (String) String.valueOf(binding.txtNombre.getText());
                String apellidos = (String) String.valueOf(binding.txtApellidos.getText());
                String email = (String) String.valueOf(binding.txtEmail.getText());
                String dni = (String) String.valueOf(binding.txtDni.getText());
                long especialidad = binding.userEspecialidad.getSelectedItemId();
                long rol = binding.userRol.getSelectedItemId();

                if (nombre != "" && apellidos != "" && email != "" && dni !="" && rol != 0){
                    long result = dbHelper.setUser(nombre, apellidos, email, dni, rol, especialidad);
                    Toast.makeText(getApplicationContext(),"Usuario añadido con éxito", Toast.LENGTH_LONG ).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Error. Debe rellenar todos los campos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}