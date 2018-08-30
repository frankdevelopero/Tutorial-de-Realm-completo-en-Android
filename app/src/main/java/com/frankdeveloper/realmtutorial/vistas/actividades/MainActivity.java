package com.frankdeveloper.realmtutorial.vistas.actividades;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.frankdeveloper.realmtutorial.R;
import com.frankdeveloper.realmtutorial.adaptadores.LibroAdaptador;
import com.frankdeveloper.realmtutorial.modelos.LibroModelo;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<LibroModelo>> {

    public Realm realm;

    private FloatingActionButton fabNuevaLibro;

    private RealmResults<LibroModelo> listaLibros;
    private RecyclerView recyclerViewLibros;
    private LibroAdaptador libroAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        findViewID();

    }

    private void findViewID() {

        fabNuevaLibro = findViewById(R.id.fabNuevoCategoria);

        listaLibros = realm.where(LibroModelo.class).findAll();
        listaLibros.addChangeListener(this);
        recyclerViewLibros = findViewById(R.id.recyclerCategoria);
        recyclerViewLibros.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        libroAdaptador = new LibroAdaptador(getApplicationContext(), listaLibros);
        recyclerViewLibros.setAdapter(libroAdaptador);

        listenOnClick();

    }

    private void listenOnClick() {

        fabNuevaLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertNuevoLibro();
            }
        });

        libroAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LibroModelo libroModelo = listaLibros.get(recyclerViewLibros.getChildAdapterPosition(view));
                alertEditarLibro(libroModelo);
            }
        });

        libroAdaptador.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                eliminarLibro(view);
                return false;
            }
        });

    }

    public void alertNuevoLibro(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialogo_nueva_categoria, null);
        builder.setView(view);

        final EditText edtTitulo = view.findViewById(R.id.edtTitulo);
        final EditText edtDescripcicon = view.findViewById(R.id.edtDescripcion);
        final EditText edtImagen = view.findViewById(R.id.edtImagen);
        builder.setMessage(getString(R.string.mensaje_alerta));
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nombre = edtTitulo.getText().toString().trim();
                String descripcion = edtDescripcicon.getText().toString();
                String imagen = edtImagen.getText().toString().trim();
                if (nombre.length()>0){
                    guardarLibro(nombre, imagen, descripcion);

                }else
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_mensaje_nombre_vacio), Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void alertEditarLibro(final LibroModelo libroModelo){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialogo_nueva_categoria, null);
        builder.setView(view);

        final EditText edtTitulo = view.findViewById(R.id.edtTitulo);
        final EditText edtDescripcicon = view.findViewById(R.id.edtDescripcion);
        final EditText edtImagen = view.findViewById(R.id.edtImagen);
        builder.setMessage(getString(R.string.mensaje_editar_alert));

        edtImagen.setText(libroModelo.getImagen());
        edtTitulo.setText(libroModelo.getNombre());
        edtDescripcicon.setText(libroModelo.getDescripcion());

        edtTitulo.setSelection(edtTitulo.getText().length());//posicionamos el cursos al final

        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nombreLibro = edtTitulo.getText().toString().trim();
                String descripcion = edtDescripcicon.getText().toString();
                String imagenLibro = edtImagen.getText().toString().trim();
                if (nombreLibro.length()>0){
                    realm.beginTransaction();
                    libroModelo.setNombre(nombreLibro);
                    libroModelo.setImagen(imagenLibro);
                    libroModelo.setDescripcion(descripcion);
                    realm.commitTransaction();
                }else
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_mensaje_nombre_vacio)
                            , Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void guardarLibro(String nombre, String imagen, String descripcion){
        realm.beginTransaction();
        LibroModelo libroModelo = new LibroModelo(nombre, descripcion,imagen);
        realm.copyToRealm(libroModelo);
        realm.commitTransaction();

    }

    public void eliminarLibro(View view){
        LibroModelo libroModelo = listaLibros.get(recyclerViewLibros.getChildAdapterPosition(view));
        realm.beginTransaction();
        assert libroModelo != null;
        libroModelo.deleteFromRealm();
        realm.commitTransaction();
    }

    @Override
    public void onChange(@NonNull RealmResults<LibroModelo> libroModelos) {
        libroAdaptador.notifyDataSetChanged();
    }
}
