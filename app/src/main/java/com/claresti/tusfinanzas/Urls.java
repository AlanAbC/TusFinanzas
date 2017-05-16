package com.claresti.tusfinanzas;


public class Urls {

    private String urlLogin;
    private String urlCategorias;
    private String urlGastos;
    private String urlPresupuestos;

    public Urls() {
        this.urlLogin = "http://localhost/TusFinanzas/usuarios.php?a=";
        this.urlCategorias = "http://localhost/TusFinanzas/categorias.php?a=";
        this.urlGastos = "http://localhost/TusFinanzas/gastos.php?a=";
        this.urlPresupuestos = "http://localhost/TusFinanzas/presupuestos.php?a=";
    }

    public String getUrlLogin() {
        return urlLogin;
    }

    public String getUrlCategorias() {
        return urlCategorias;
    }

    public String getUrlGastos() {
        return urlGastos;
    }

    public String getUrlPresupuestos() {
        return urlPresupuestos;
    }
}