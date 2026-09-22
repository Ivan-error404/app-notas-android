package com.ivan.appnotas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private EditText campoUsuario;
    private EditText campoPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        campoUsuario = findViewById(R.id.campoUsuario);
        campoPassword = findViewById(R.id.campoPassword);
        Button botonEntrar = findViewById(R.id.botonEntrar);
        Button botonRegistrarse = findViewById(R.id.botonRegistrarse);

        botonEntrar.setOnClickListener(v -> iniciarSesion());
        botonRegistrarse.setOnClickListener(v ->
                startActivity(new Intent(this, RegistroActivity.class)));
    }

    private void iniciarSesion() {
        String usuario = campoUsuario.getText().toString().trim();
        String password = campoPassword.getText().toString().trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (db.iniciarSesion(usuario, password)) {
            Intent intento = new Intent(this, MainActivity.class);
            intento.putExtra("usuario", usuario);
            startActivity(intento);
            finish();
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }
}
