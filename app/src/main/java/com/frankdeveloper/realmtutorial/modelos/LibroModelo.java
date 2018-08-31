package com.frankdeveloper.realmtutorial.modelos;

import com.frankdeveloper.realmtutorial.aplicacion.MiAplicacion;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;

public class LibroModelo extends RealmObject{

    private int id;
    private String nombre;
    private String imagen;
    private String descripcion;
    private Date fecha;

    //Para relacionar con otros modelos
    //private RealmList<TuModelo> listaObjeto;

    //Real necesita un contructo vacio
    public LibroModelo() {

    }

    public LibroModelo(String nombre, String descripcion, String imagen) {
        this.id = MiAplicacion.libroId.incrementAndGet();
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.fecha = new Date();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getImagen() {
        return imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
