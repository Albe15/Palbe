package citas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoalberto.R;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyCustomAdapter extends ArrayAdapter<String> {
    private DatabaseHelper dbHelper;
    public MyCustomAdapter(Context context, ArrayList<String> items) {
        super(context, 0, items);
        dbHelper = new DatabaseHelper(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Obtener la fila actual
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Obtener el elemento actual
        String item = getItem(position);

        // Agregar el texto al TextView de la fila
        TextView textViewItem = (TextView) listItemView.findViewById(R.id.text_view_item);
        textViewItem.setText(item);

        // Agregar el botón de borrar a la fila
        Button buttonDelete = (Button) listItemView.findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Eliminar el elemento actual

                String item = getItem(position);
                // Obtener el número de ID del elemento
                Pattern pattern = Pattern.compile("\\bId: (\\d+)\\b");
                Matcher matcher = pattern.matcher(item);
                int id = 0;
                if (matcher.find()) {
                    id = Integer.parseInt(matcher.group(1));
                }
                dbHelper.deleteCita(id);
                remove(getItem(position));
                Toast.makeText(getContext(), "Su cita ha sido eliminada", Toast.LENGTH_SHORT).show();
            }
        });
        return listItemView;
    }
}