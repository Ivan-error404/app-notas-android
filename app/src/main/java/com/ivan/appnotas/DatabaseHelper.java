package com.ivan.appnotas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "appnotas.db";
    private static final int VERSION_BD = 1;

    public DatabaseHelper(Context contexto) {
        super(contexto, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL)");
        db.execSQL(
                "CREATE TABLE notas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario TEXT NOT NULL, " +
                "titulo TEXT NOT NULL, " +
                "contenido TEXT, " +
                "fecha TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS notas");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }

    public boolean registrar(String usuario, String password) {
        if (usuarioExiste(usuario)) {
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("usuario", usuario);
        valores.put("password", password);
        return db.insert("usuarios", null, valores) != -1;
    }

    public boolean iniciarSesion(String usuario, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id FROM usuarios WHERE usuario = ? AND password = ?",
                new String[]{usuario, password});
        boolean existe = cursor.moveToFirst();
        cursor.close();
        return existe;
    }

    private boolean usuarioExiste(String usuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id FROM usuarios WHERE usuario = ?",
                new String[]{usuario});
        boolean existe = cursor.moveToFirst();
        cursor.close();
        return existe;
    }

    public long guardarNota(String usuario, String titulo, String contenido) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("usuario", usuario);
        valores.put("titulo", titulo);
        valores.put("contenido", contenido);
        valores.put("fecha", java.text.DateFormat.getDateInstance().format(new java.util.Date()));
        return db.insert("notas", null, valores);
    }

    public List<Nota> obtenerNotas(String usuario) {
        List<Nota> notas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id, titulo, contenido, fecha FROM notas WHERE usuario = ? ORDER BY id DESC",
                new String[]{usuario});
        while (cursor.moveToNext()) {
            notas.add(new Nota(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)));
        }
        cursor.close();
        return notas;
    }

    public void eliminarNota(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("notas", "id = ?", new String[]{String.valueOf(id)});
    }
}
