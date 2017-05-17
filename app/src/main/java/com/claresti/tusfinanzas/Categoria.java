package com.claresti.tusfinanzas;

public class Categoria {

    private String cat_id;
    private String cat_nombre;
    private String cat_descripcion;

    public Categoria() {

    }

    public Categoria(String cat_id, String cat_nombre, String cat_descripcion) {
        this.cat_id = cat_id;
        this.cat_nombre = cat_nombre;
        this.cat_descripcion = cat_descripcion;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_nombre() {
        return cat_nombre;
    }

    public void setCat_nombre(String cat_nombre) {
        this.cat_nombre = cat_nombre;
    }

    public String getCat_descripcion() {
        return cat_descripcion;
    }

    public void setCat_descripcion(String cat_descripcion) {
        this.cat_descripcion = cat_descripcion;
    }
}
