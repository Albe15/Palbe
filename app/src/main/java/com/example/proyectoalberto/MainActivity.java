package com.example.proyectoalberto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoalberto.databinding.ActivityNuevoBinding;

import java.util.ArrayList;

import citas.DatabaseHelper;
import citas.MyCustomAdapter;
import citas.data.LoginDataSource;
import citas.data.LoginRepository;
import citas.data.model.LoggedInUser;

public class MainActivity extends AppCompatActivity {
    private ActivityNuevoBinding binding;
    private LoginRepository loginRepository;
    private DatabaseHelper dbHelper;
    private LoggedInUser loggedUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        loginRepository= LoginRepository.getInstance(new LoginDataSource(dbHelper));
        binding = ActivityNuevoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Obtiene los datos del usuario logado
        this.loggedUser=loginRepository.getCurrentLoggedUser();
        int user_id = this.loggedUser.getId();
        String rol = this.loggedUser.getRol();
        String especialidad = this.loggedUser.getEspecialidad();
        int rol_id = this.loggedUser.getRolId();
        ArrayList citas = new ArrayList<String>();;

        // Configuración de pantalla en función al rol
        switch (rol_id){
            case 1: // Usuario
                binding.btnPedirCita.setVisibility(View.VISIBLE);
                citas = dbHelper.getCitasList(user_id);
                binding.Bienvenido.setText(this.loggedUser.getDisplayName());
                break;
            case 2: // Médico
                citas = dbHelper.getCitasMedicoList(user_id);
                binding.Bienvenido.setText("<Médico " + especialidad +"> " + this.loggedUser.getDisplayName());
                break;
            case 3:  // Administrador
                binding.Bienvenido.setText("<Administrador>" + this.loggedUser.getDisplayName());
                citas = dbHelper.getAllCitas();
                binding.AddUser.setVisibility(View.VISIBLE);
                break;
        }

        MyCustomAdapter adapter = new MyCustomAdapter(this, citas);

        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, citas);
        if (citas.isEmpty()){
            binding.citas.setText("No tiene citas");
        } else{
            // ListView listView = binding.lista;
            // listView.setAdapter(adapter);
            ListView listView = binding.lista;
            listView.setAdapter(adapter);
        }

        // Código del botón de pedir citas para enlazar con la siguiente actividad.
        Button btnPedirCita = binding.btnPedirCita;
        btnPedirCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CitaActivity.class);
                startActivity(intent);
            }
        });

        // Código del botón de añadir usuarios
        Button btnAddUser = binding.AddUser;
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddUserActivity.class);
                startActivity(intent);
            }
        });

        /*binding.textViewDescripcion.setText(this.loggedUser.getDisplayName());
        binding.textViewDescripcion.setText(this.loggedUser.getApellidos());
        binding.textViewDescripcion.setText(this.loggedUser.getDisplayName());*/
    }
}