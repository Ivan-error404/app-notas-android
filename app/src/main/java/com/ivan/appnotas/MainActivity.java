package com.ivan.appnotas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private String usuario;
    private NotaAdapter adaptador;
    private List<Nota> notas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        usuario = getIntent().getStringExtra("usuario");

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setTitle("Notas de " + usuario);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.accionCerrar) {
                finish();
                return true;
            }
            return false;
        });

        RecyclerView listaNotas = findViewById(R.id.listaNotas);
        listaNotas.setLayoutManager(new LinearLayoutManager(this));

        notas = db.obtenerNotas(usuario);
        adaptador = new NotaAdapter(this, notas, db, usuario);
        listaNotas.setAdapter(adaptador);

        FloatingActionButton botonNueva = findViewById(R.id.botonNueva);
        botonNueva.setOnClickListener(v -> mostrarDialogoNuevaNota());
    }

    private void mostrarDialogoNuevaNota() {
        LayoutInflater inflador = getLayoutInflater();
        android.view.View vista = inflador.inflate(R.layout.dialogo_nueva_nota, null);
        EditText campoTitulo = vista.findViewById(R.id.campoTitulo);
        EditText campoContenido = vista.findViewById(R.id.campoContenido);
        EditText campoFecha = vista.findViewById(R.id.campoFecha);
        campoFecha.setText(java.text.DateFormat.getDateInstance().format(new java.util.Date()));

        new MaterialAlertDialogBuilder(this)
                .setTitle("Nueva nota")
                .setView(vista)
                .setPositiveButton("Guardar", (dialogo, cual) -> {
                    String titulo = campoTitulo.getText().toString().trim();
                    if (titulo.isEmpty()) {
                        android.widget.Toast.makeText(this, "El título es obligatorio",
                                android.widget.Toast.LENGTH_SHORT).show();
                        return;
                    }
                    db.guardarNota(usuario, titulo, campoContenido.getText().toString().trim());
                    notas.clear();
                    notas.addAll(db.obtenerNotas(usuario));
                    adaptador.notifyDataSetChanged();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
