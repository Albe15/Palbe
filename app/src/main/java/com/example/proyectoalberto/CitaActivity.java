package com.example.proyectoalberto;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoalberto.databinding.CitaLayoutBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import citas.DatabaseHelper;
import citas.data.LoginDataSource;
import citas.data.LoginRepository;
import citas.data.model.Especialidad;
import citas.data.model.LoggedInUser;

public class CitaActivity extends AppCompatActivity {
    private CitaLayoutBinding binding;
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
        setContentView(R.layout.cita_layout);

        dbHelper = new DatabaseHelper(this);
        loginRepository= LoginRepository.getInstance(new LoginDataSource(dbHelper));

        CitaLayoutBinding binding = CitaLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Obtiene los datos del usuario logado
        this.loggedUser=loginRepository.getCurrentLoggedUser();
        int user_id = this.loggedUser.getId();

        ArrayList listaEspecialidades = this.dbHelper.getEspecialidadesList();
        listaEspecialidades.add(0, "Seleccione");
        ArrayAdapter<Especialidad> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listaEspecialidades);
        Spinner spinner = binding.especialidad;
        spinner.setAdapter(adapter);

        Button btnPedirCita = binding.btnPedirCita2;
        btnPedirCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Comprobamos que todos los datos son correctos
                String fecha = (String) binding.textViewTime.getText();
                String descripcion = String.valueOf(binding.descripcion.getText());
                long especialidad = binding.especialidad.getSelectedItemId();

                if (fecha != ""){
                    // Guardamos la petición de cita
                    dbHelper.setCita(user_id, fecha, descripcion, especialidad);
                    Toast.makeText(getApplicationContext(), "Su cita se ha guardado con éxito", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                } else{
                    Toast.makeText(getApplicationContext(), "Error. Debe rellenar todos los campos", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button buttonPickDateTime = binding.buttonPickTime;
        final TextView textViewDateTime = binding.textViewTime;

        buttonPickDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la fecha y hora actual

                final Calendar currentDateTime = Calendar.getInstance();
                int year = currentDateTime.get(Calendar.YEAR);
                int month = currentDateTime.get(Calendar.MONTH);
                int dayOfMonth = currentDateTime.get(Calendar.DAY_OF_MONTH);
                int hourOfDay = currentDateTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentDateTime.get(Calendar.MINUTE);

                // Mostrar el DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(CitaActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Actualizar la fecha seleccionada en el Calendar
                                currentDateTime.set(Calendar.YEAR, year);
                                currentDateTime.set(Calendar.MONTH, month);
                                currentDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                // Mostrar el TimePickerDialog
                                TimePickerDialog timePickerDialog = new TimePickerDialog(CitaActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                // Actualizar el TextView con la fecha y hora seleccionadas
                                                currentDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                                currentDateTime.set(Calendar.MINUTE, minute);
                                                String selectedDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(currentDateTime.getTime());
                                                textViewDateTime.setText(selectedDateTime);
                                            }
                                        }, hourOfDay, minute, true);
                                timePickerDialog.show();
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });





    }
}