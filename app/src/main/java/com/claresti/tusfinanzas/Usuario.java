package com.claresti.tusfinanzas;

public class Usuario {

    private String usu_id;
    private String usu_nombre;
    private String usu_apellidos;
    private String usu_mail;
    private String usu_password;
    private String usu_premium;
    private String usu_moneda;

    public Usuario(){

    }

    public Usuario(String usu_id, String usu_nombre, String usu_apellidos, String usu_mail, String usu_password, String usu_premium, String usu_moneda) {
        this.usu_id = usu_id;
        this.usu_nombre = usu_nombre;
        this.usu_apellidos = usu_apellidos;
        this.usu_mail = usu_mail;
        this.usu_password = usu_password;
        this.usu_premium = usu_premium;
        this.usu_moneda = usu_moneda;
    }

    public String getUsu_id() {
        return usu_id;
    }

    public void setUsu_id(String usu_id) {
        this.usu_id = usu_id;
    }

    public String getUsu_nombre() {
        return usu_nombre;
    }

    public void setUsu_nombre(String usu_nombre) {
        this.usu_nombre = usu_nombre;
    }

    public String getUsu_apellidos() {
        return usu_apellidos;
    }

    public void setUsu_apellidos(String usu_apellidos) {
        this.usu_apellidos = usu_apellidos;
    }

    public String getUsu_mail() {
        return usu_mail;
    }

    public void setUsu_mail(String usu_mail) {
        this.usu_mail = usu_mail;
    }

    public String getUsu_password() {
        return usu_password;
    }

    public void setUsu_password(String usu_password) {
        this.usu_password = usu_password;
    }

    public String getUsu_premium() {
        return usu_premium;
    }

    public void setUsu_premium(String usu_premium) {
        this.usu_premium = usu_premium;
    }

    public String getUsu_moneda() {
        return usu_moneda;
    }

    public void setUsu_moneda(String usu_moneda) {
        this.usu_moneda = usu_moneda;
    }
}
