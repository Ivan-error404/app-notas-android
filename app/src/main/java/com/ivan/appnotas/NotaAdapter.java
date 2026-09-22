package com.ivan.appnotas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotaAdapter extends RecyclerView.Adapter<NotaAdapter.NotaViewHolder> {

    private final Context contexto;
    private final List<Nota> notas;
    private final DatabaseHelper db;
    private final String usuario;

    public NotaAdapter(Context contexto, List<Nota> notas, DatabaseHelper db, String usuario) {
        this.contexto = contexto;
        this.notas = notas;
        this.db = db;
        this.usuario = usuario;
    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(contexto).inflate(R.layout.item_nota, parent, false);
        return new NotaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder holder, int posicion) {
        Nota nota = notas.get(posicion);
        holder.textoTitulo.setText(nota.getTitulo());
        holder.textoContenido.setText(nota.getContenido());
        holder.textoFecha.setText(nota.getFecha());
        holder.tarjeta.setOnClickListener(v -> confirmarBorrar(nota, holder.getAdapterPosition()));
    }

    private void confirmarBorrar(Nota nota, int posicion) {
        androidx.appcompat.app.AlertDialog.Builder builder =
                new androidx.appcompat.app.AlertDialog.Builder(contexto);
        builder.setTitle("Eliminar nota");
        builder.setMessage("¿Eliminar \"" + nota.getTitulo() + "\"?");
        builder.setPositiveButton("Eliminar", (dialogo, cual) -> {
            db.eliminarNota(nota.getId());
            notas.remove(posicion);
            notifyItemRemoved(posicion);
            Toast.makeText(contexto, "Nota eliminada", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    static class NotaViewHolder extends RecyclerView.ViewHolder {
        final CardView tarjeta;
        final TextView textoTitulo;
        final TextView textoContenido;
        final TextView textoFecha;

        NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            tarjeta = itemView.findViewById(R.id.tarjeta);
            textoTitulo = itemView.findViewById(R.id.textoTitulo);
            textoContenido = itemView.findViewById(R.id.textoContenido);
            textoFecha = itemView.findViewById(R.id.textoFecha);
        }
    }
}
