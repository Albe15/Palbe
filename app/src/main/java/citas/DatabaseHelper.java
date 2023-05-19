package citas;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;

import citas.data.model.Especialidad;
import citas.data.model.Rol;
import citas.data.model.Usuario;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "citas.db"; // Nombre de la base de datos
    private static final int DATABASE_VERSION = 1;  // Versión de la base de datos

    // Tabla usuarios
    private static final String TABLE_USUARIOS = "usuarios";
    private static final String COLUMN_USUARIO_ID = "id_usuario";
    private static final String COLUMN_USUARIO_NOMBRE = "nombre";
    private static final String COLUMN_USUARIO_APELLIDOS = "apellidos";
    private static final String COLUMN_USUARIO_DNI = "dni";
    private static final String COLUMN_USUARIO_EMAIL = "email";
    private static final String COLUMN_USUARIO_USER = "user";
    private static final String COLUMN_USUARIO_PASSWORD = "password";
    private static final String COLUMN_USUARIO_ROL_ID = "rol_id";
    private static final String COLUMN_USUARIO_ESPECIALIDAD_ID = "especialidad_id";

    // Tabla roles
    private static final String TABLE_ROLES = "roles";
    private static final String COLUMN_ROL_ID = "id_rol";
    private static final String COLUMN_ROL_NOMBRE = "rol";

    // Tabla especialidades
    private static final String TABLE_ESPECIALIDADES = "especialidades";
    private static final String COLUMN_ESPECIALIDAD_ID = "id_especialidad";
    private static final String COLUMN_ESPECIALIDAD_NOMBRE = "especialidad";

    // Tabla citas
    private static final String TABLE_CITAS = "citas";
    private static final String COLUMN_CITA_ID = "id_cita";
    private static final String COLUMN_CITA_FECHA = "cita_fecha";
    private static final String COLUMN_CITA_DESCRIPCION = "cita_descripcion";
    private static final String COLUMN_CITA_USUARIO_ID = "usuario_id";
    private static final String COLUMN_CITA_MEDICO_ID = "medico_id";
    private static final String COLUMN_CITA_ESPECIALIDAD_ID = "especialidad_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Este método se llama automáticamente cuando la base de datos es creada por primera vez
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla de Usuarios
        String CREATE_USUARIOS_TABLE = "CREATE TABLE " + TABLE_USUARIOS + "("
                + COLUMN_USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USUARIO_NOMBRE + " TEXT,"
                + COLUMN_USUARIO_APELLIDOS + " TEXT,"
                + COLUMN_USUARIO_DNI + " TEXT,"
                + COLUMN_USUARIO_EMAIL + " TEXT,"
                + COLUMN_USUARIO_USER + " TEXT,"
                + COLUMN_USUARIO_PASSWORD + " TEXT,"
                + COLUMN_USUARIO_ROL_ID + " INTEGER,"
                + COLUMN_USUARIO_ESPECIALIDAD_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_USUARIO_ROL_ID + ") REFERENCES " + TABLE_ROLES + "(" + COLUMN_ROL_ID + "),"
                + "FOREIGN KEY(" + COLUMN_USUARIO_ESPECIALIDAD_ID + ") REFERENCES " + TABLE_ESPECIALIDADES + "(" + COLUMN_ESPECIALIDAD_ID + ")"
                + ")";

        // Crear la tabla de Roles
        String CREATE_ROLES_TABLE = "CREATE TABLE " + TABLE_ROLES + "("
                + COLUMN_ROL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ROL_NOMBRE + " TEXT"
                + ")";

        // Crear la tabla de Especialidades
        String CREATE_ESPECIALIDADES_TABLE = "CREATE TABLE " + TABLE_ESPECIALIDADES + "("
                + COLUMN_ESPECIALIDAD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ESPECIALIDAD_NOMBRE + " TEXT"
                + ")";

        // Crear la tabla de Citas
        String CREATE_CITAS_TABLE = "CREATE TABLE " + TABLE_CITAS + "("
                + COLUMN_CITA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CITA_FECHA + " TEXT,"
                + COLUMN_CITA_DESCRIPCION + " TEXT,"
                + COLUMN_CITA_USUARIO_ID + " INTEGER,"
                + COLUMN_CITA_MEDICO_ID + " INTEGER,"
                + COLUMN_CITA_ESPECIALIDAD_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_CITA_USUARIO_ID + ") REFERENCES " + TABLE_USUARIOS + "(" + COLUMN_USUARIO_ID + "),"
                + "FOREIGN KEY(" + COLUMN_CITA_MEDICO_ID + ") REFERENCES " + TABLE_USUARIOS + "(" + COLUMN_USUARIO_ID + "),"
                + "FOREIGN KEY(" + COLUMN_CITA_ESPECIALIDAD_ID + ") REFERENCES " + TABLE_ESPECIALIDADES + "(" + COLUMN_ESPECIALIDAD_ID + ")"
                + ")";

        // Ejecutar las sentencias SQL para crear las tablas
        db.execSQL(CREATE_USUARIOS_TABLE);
        db.execSQL(CREATE_ROLES_TABLE);
        db.execSQL(CREATE_ESPECIALIDADES_TABLE);
        db.execSQL(CREATE_CITAS_TABLE);

        String Insert_especialidades = "insert into " + TABLE_ESPECIALIDADES + " ("
                + COLUMN_ESPECIALIDAD_NOMBRE + ") values (\"Pediatría\")";
        db.execSQL(Insert_especialidades);



        String Insert_roles = "insert into " + TABLE_ROLES + " ("
                + COLUMN_ROL_NOMBRE + ") values (\"Administrador\")";
        db.execSQL(Insert_roles);



        String Insert_usuario = "INSERT INTO " + TABLE_USUARIOS + "("
                + COLUMN_USUARIO_NOMBRE + ","
                + COLUMN_USUARIO_APELLIDOS + ","
                + COLUMN_USUARIO_DNI + ","
                + COLUMN_USUARIO_EMAIL + ","
                + COLUMN_USUARIO_USER + ","
                + COLUMN_USUARIO_PASSWORD + ","
                + COLUMN_USUARIO_ROL_ID + ","
                + COLUMN_USUARIO_ESPECIALIDAD_ID + ")"
                + "VALUES (\"Pedro\", \"Ramos Delgado\", \"28789321T\", \"Pedro@email.com\", \"PedroRa\", \"12345\","
                + "3,null)";
        db.execSQL(Insert_usuario);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Elimina todas las tablas si ya existen en la base de datos actual
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESPECIALIDADES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITAS);

        // Llama al método onCreate para crear las tablas nuevamente
        onCreate(db);
    }

    public ArrayList<String> getCitasList(int idUsuario) {
        ArrayList<String> citasList = new ArrayList<>();

        // Consulta SQL para obtener las citas del usuario especificado
        String select_citas = "select c.*,e.especialidad,u.nombre,u.apellidos from " + TABLE_CITAS + " c "
                + " left join " + TABLE_ESPECIALIDADES +  " e on c.especialidad_id = e.id_especialidad"
                + " left join  " + TABLE_USUARIOS + " u on c.medico_id = u.id_usuario"
                + " WHERE " + COLUMN_CITA_USUARIO_ID
                + "=" + idUsuario + " ORDER BY "
                + "substr(" + COLUMN_CITA_FECHA + ", 9, 2)"
                + " || substr(" +  COLUMN_CITA_FECHA + ", 4, 2)"
                + " || substr(" + COLUMN_CITA_FECHA + ", 1, 2)"
                + " || substr(" + COLUMN_CITA_FECHA + ", 12, 2)"
                + " || substr(" + COLUMN_CITA_FECHA + ", 15, 2)";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select_citas, null);

        // Recorrer el cursor y obtener los valores de las columnas para cada cita
        if (cursor.moveToFirst()) {
            do {
                // Obtener los valores de las columnas para cada cita
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CITA_ID));
                String fecha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CITA_FECHA));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CITA_DESCRIPCION));
                int usuarioId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CITA_USUARIO_ID));
                int especialidadId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CITA_ESPECIALIDAD_ID));
                String especialidad = cursor.getString((cursor.getColumnIndexOrThrow(COLUMN_ESPECIALIDAD_NOMBRE)));
                int medicoId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CITA_MEDICO_ID));
                String medico_apellidos = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USUARIO_APELLIDOS));
                String medico_nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USUARIO_NOMBRE));

                // Crear una cadena de texto con los datos de la cita y agregarla a la lista de citas
                citasList.add("Id: " + id + " Fecha: " + fecha + "\n" + "Especialidad: " + especialidad  + "\n" + "Médico: " + medico_apellidos + ", " + medico_nombre + "\n" + "Descripción: " + descripcion );
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return citasList;
    }

    public ArrayList<String> getCitasMedicoList(int idMedico) {
        ArrayList<String> citasList = new ArrayList<>();

        // Consulta SQL para obtener las citas de un médico específico
        String select_citas = "select c.*,e.especialidad,u.nombre,u.apellidos from " + TABLE_CITAS + " c "
                + " left join " + TABLE_ESPECIALIDADES +  " e on c.especialidad_id = e.id_especialidad"
                + " left join  " + TABLE_USUARIOS + " u on c.usuario_id = u.id_usuario"
                + " WHERE c." + COLUMN_CITA_MEDICO_ID
                + "=" + idMedico + " ORDER BY "
                + "substr(" + COLUMN_CITA_FECHA + ", 9, 2)"
                + " || substr(" +  COLUMN_CITA_FECHA + ", 4, 2)"
                + " || substr(" + COLUMN_CITA_FECHA + ", 1, 2)"
                + " || substr(" + COLUMN_CITA_FECHA + ", 12, 2)"
                + " || substr(" + COLUMN_CITA_FECHA + ", 15, 2)";
        System.out.println(select_citas);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select_citas, null);

        // Recorrer el cursor y obtener los valores de las columnas para cada fila
        if (cursor.moveToFirst()) {
            do {
                // Obtener los valores de las columnas para cada fila del cursor
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CITA_ID));
                String fecha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CITA_FECHA));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CITA_DESCRIPCION));
                int usuarioId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CITA_USUARIO_ID));
                int especialidadId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CITA_ESPECIALIDAD_ID));
                String especialidad = cursor.getString((cursor.getColumnIndexOrThrow(COLUMN_ESPECIALIDAD_NOMBRE)));
                int medicoId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CITA_MEDICO_ID));
                String paciente_apellidos = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USUARIO_APELLIDOS));
                String paciente_nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USUARIO_NOMBRE));

                // Crear una cadena de texto con los datos de la cita y agregarla a la lista de citas
                citasList.add("Id: " + id + " Fecha: " + fecha + "\n" + "Especialidad: " + especialidad  + "\n" + "Paciente: " + paciente_apellidos + ", " + paciente_nombre + "\n" + "Descripción: " + descripcion );
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return citasList;
    }

    public ArrayList<String> getAllCitas() {
        ArrayList<String> citasList = new ArrayList<>();

        // Consulta SQL para obtener todas las citas de la base de datos, incluyendo información adicional de otras tablas
        String select_citas = "select c.*,e.especialidad,u.nombre || ' ' || u.apellidos as medico, v.nombre || ' ' || v.apellidos as paciente from " + TABLE_CITAS + " c "
                + " left join " + TABLE_ESPECIALIDADES +  " e on c.especialidad_id = e.id_especialidad"
                + " left join  " + TABLE_USUARIOS + " u on c.medico_id = u.id_usuario"
                + " left join  " + TABLE_USUARIOS + " v on c.usuario_id = v.id_usuario"
                + " ORDER BY "
                + "substr(" + COLUMN_CITA_FECHA + ", 9, 2)"
                + " || substr(" +  COLUMN_CITA_FECHA + ", 4, 2)"
                + " || substr(" + COLUMN_CITA_FECHA + ", 1, 2)"
                + " || substr(" + COLUMN_CITA_FECHA + ", 12, 2)"
                + " || substr(" + COLUMN_CITA_FECHA + ", 15, 2)";

        // Ejecutar la consulta y obtener un cursor con los resultados
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select_citas, null);
        if (cursor.moveToFirst()) {
            do {
                // Obtener los valores de las columnas para cada fila del cursor
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CITA_ID));
                String fecha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CITA_FECHA));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CITA_DESCRIPCION));
                int usuarioId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CITA_USUARIO_ID));
                int especialidadId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CITA_ESPECIALIDAD_ID));
                String especialidad = cursor.getString((cursor.getColumnIndexOrThrow(COLUMN_ESPECIALIDAD_NOMBRE)));
                int medicoId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CITA_MEDICO_ID));
                String paciente = cursor.getString(cursor.getColumnIndexOrThrow("paciente"));
                String medico = cursor.getString(cursor.getColumnIndexOrThrow("medico"));

                // Crear una cadena de texto con los datos de la cita y agregarla a la lista de citas
                citasList.add("Id: " + id + " Fecha: " + fecha + "\n"
                        + "Especialidad: " + especialidad  + "\n"
                        + "Médico: " + medico + "\n"
                        + "Paciente: " + paciente + "\n"
                        + "Descripción: " + descripcion );
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return citasList;

    }
    // Devuelve una lista con TODAS las especialidades
    public ArrayList<Especialidad> getEspecialidadesList (){
        ArrayList<Especialidad> especialidadesList = new ArrayList<>();
        String select_citas = "select * from " + TABLE_ESPECIALIDADES
                + " ORDER BY " + COLUMN_ESPECIALIDAD_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select_citas, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ESPECIALIDAD_ID));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ESPECIALIDAD_NOMBRE));
                Especialidad especialidad = new Especialidad(id, nombre);
                especialidadesList.add(especialidad);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return especialidadesList;
    }

    // Devuelve una lista con todos los roles
    public ArrayList<Rol> getRolesList (){
        ArrayList<Rol> rolesList = new ArrayList<>();
        String select_roles = "select * from " + TABLE_ROLES
                + " WHERE " + COLUMN_ROL_ID + " IN(1,2) ORDER BY " +COLUMN_ROL_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select_roles, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ROL_ID));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROL_NOMBRE));
                Rol rol = new Rol(id, nombre);
                rolesList.add(rol);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return rolesList;
    }

    public long deleteCita(int item){
        SQLiteDatabase db = this.getReadableDatabase();
        // Elimina la cita de la tabla de citas utilizando el ID proporcionado
        // El método "delete" devuelve el número de filas afectadas por la operación de eliminación
        long resultado = db.delete(TABLE_CITAS, COLUMN_CITA_ID + "=" +item,null);
        return resultado;
    }
    public long setCita (int user_id, String fecha, String descripcion, long especialidad) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Médico por defecto para evitar errores
        int medico_id = 2;
        // Primero debemos obtener el médico.
        String select_medicos = "Select " + COLUMN_USUARIO_ID + " from " + TABLE_USUARIOS + " WHERE " + COLUMN_USUARIO_ESPECIALIDAD_ID + "=" + especialidad;
        Cursor cursor = db.rawQuery(select_medicos, null);
        if (cursor.moveToFirst()) {
            do {
                medico_id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USUARIO_ID));
            } while (cursor.moveToNext());
        }

        ContentValues valores = new ContentValues();
        valores.put("cita_fecha", fecha);
        valores.put("cita_descripcion",descripcion);
        valores.put("especialidad_id", especialidad);
        valores.put("usuario_id", user_id);
        valores.put("medico_id",medico_id);
        return db.insert(TABLE_CITAS,null,valores);
    }
        /* Cuando se crea por primera vez un usuario el usuario es su email y
           la contraseña 12345 para que lo cambie la primera vez que inicie de sesion en una futura mejora */
    public long setUser (String nombre, String apellidos, String email, String dni, long rol, long especialidad) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("apellidos",apellidos);
        valores.put("user", email);   // Insertamos como user el email
        valores.put("password", "12345");   // Insertamos como password 12345
        valores.put("email", email);
        valores.put("dni", dni);
        valores.put("rol_id", rol);
        valores.put("especialidad_id", especialidad == 0 ? null: especialidad); // operador ternario
        return db.insert(TABLE_USUARIOS,null,valores);
    }
    public Usuario isValidCredentials(String username, String password) {
        // Obtiene una instancia de la base de datos en modo lectura
        SQLiteDatabase db = this.getReadableDatabase();

        // Define las columnas a seleccionar en la consulta
        String[] columns = {"u.*", "r.rol", "e.especialidad"};

        // Define la cláusula WHERE de la consulta
        String selection = "u.user = ? AND u.password = ?";

        // Define la tabla principal y las tablas a unir mediante JOIN
        String table = "usuarios u";
        String join1 = "LEFT JOIN roles r ON u.rol_id = r.id_rol";
        String join2 = "LEFT JOIN especialidades e ON u.especialidad_id = e.id_especialidad";

        // Construye la consulta SQL
        String query = "SELECT " + TextUtils.join(",", columns) + " FROM " + table + " " + join1 + " " + join2 + " WHERE " + selection;

        // Ejecuta la consulta y obtiene un cursor para recorrer los resultados
        Cursor cursor = db.rawQuery(query, new String[] {username,password} );

        // Inicializa un objeto Usuario como nulo
        Usuario user = null;

        // Si el cursor tiene resultados, crea un nuevo objeto Usuario y le asigna los valores de las columnas correspondientes
        if (cursor.moveToFirst()) {
            user = new Usuario(0,"","","","","","",0,0,"","");

            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(this.COLUMN_USUARIO_ID)));
            user.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(this.COLUMN_USUARIO_NOMBRE)));
            user.setApellidos(cursor.getString(cursor.getColumnIndexOrThrow(this.COLUMN_USUARIO_APELLIDOS)));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(this.COLUMN_USUARIO_EMAIL)));
            user.setDni(cursor.getString(cursor.getColumnIndexOrThrow(this.COLUMN_USUARIO_DNI)));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(this.COLUMN_USUARIO_EMAIL)));
            user.setUser(cursor.getString(cursor.getColumnIndexOrThrow(this.COLUMN_USUARIO_USER)));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(this.COLUMN_USUARIO_PASSWORD)));
            user.setRolId(cursor.getInt(cursor.getColumnIndexOrThrow(this.COLUMN_USUARIO_ROL_ID)));
            user.setEspecialidadId(cursor.getInt(cursor.getColumnIndexOrThrow(this.COLUMN_USUARIO_ESPECIALIDAD_ID)));
            user.setRol(cursor.getString(cursor.getColumnIndexOrThrow(this.COLUMN_ROL_NOMBRE)));
            user.setEspecialidad(cursor.getString(cursor.getColumnIndexOrThrow(this.COLUMN_ESPECIALIDAD_NOMBRE)));
        }
        // Cierra el cursor y la conexión a la base de datos
        cursor.close();
        db.close();

        // Retorna el objeto Usuario obtenido o null si no se encontraron resultados
        return user;
    }
}
