package com.ivan.appnotas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private EditText campoUsuario;
    private EditText campoPassword;
    private EditText campoConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        db = new DatabaseHelper(this);
        campoUsuario = findViewById(R.id.campoUsuario);
        campoPassword = findViewById(R.id.campoPassword);
        campoConfirmar = findViewById(R.id.campoConfirmar);
        Button botonCrear = findViewById(R.id.botonCrear);

        botonCrear.setOnClickListener(v -> registrar());
    }

    private void registrar() {
        String usuario = campoUsuario.getText().toString().trim();
        String password = campoPassword.getText().toString().trim();
        String confirmar = campoConfirmar.getText().toString().trim();

        if (usuario.isEmpty() || password.isEmpty() || confirmar.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirmar)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        if (db.registrar(usuario, password)) {
            Toast.makeText(this, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Ese usuario ya existe", Toast.LENGTH_SHORT).show();
        }
    }
}
