package com.frankdeveloper.realmtutorial.adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frankdeveloper.realmtutorial.R;
import com.frankdeveloper.realmtutorial.modelos.LibroModelo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class LibroAdaptador extends RecyclerView.Adapter<LibroAdaptador.ViewHolderLibro>  implements View.OnClickListener, View.OnLongClickListener{

    private Context context;
    private List<LibroModelo> listaCategoria;
    private View.OnClickListener listener;
    private View.OnLongClickListener onLongClickListener;

    public LibroAdaptador(Context context, List<LibroModelo> listaCategoria) {
        this.context = context;
        this.listaCategoria = listaCategoria;
    }



    @NonNull
    @Override
    public ViewHolderLibro onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria_recycler,parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolderLibro(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderLibro holder, int position) {
        holder.txtTitulo.setText(listaCategoria.get(position).getNombre());
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = dateFormat.format(listaCategoria.get(position).getFecha());
        holder.txtFecha.setText(fecha);
        holder.txtDescripcion.setText(listaCategoria.get(position).getDescripcion());
        Glide.with(context).load(listaCategoria.get(position).getImagen()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return listaCategoria.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener){
        this.onLongClickListener = onLongClickListener;
    }

    @Override
    public boolean onLongClick(View view) {
        if(onLongClickListener != null){
            onLongClickListener.onLongClick(view);
        }
        return false;
    }

    public class ViewHolderLibro extends RecyclerView.ViewHolder {

        TextView txtTitulo;
        TextView txtFecha;
        TextView txtDescripcion;
        ImageView imageView;

        public ViewHolderLibro(View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            imageView = itemView.findViewById(R.id.imgLibro);

        }
    }


}
