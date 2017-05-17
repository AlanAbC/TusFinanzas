package com.claresti.tusfinanzas;


public class Urls {

    private String urlLogin;
    private String urlCategorias;
    private String urlGastos;
    private String urlPresupuestos;

    public Urls() {
        this.urlLogin = "http://tusfinanzas.claresti.com/usuarios.php?a=";
        this.urlCategorias = "http://tusfinanzas.claresti.com/categorias.php?a=";
        this.urlGastos = "http://tusfinanzas.claresti.com/gastos.php?a=";
        this.urlPresupuestos = "http://tusfinanzas.claresti.com/presupuestos.php?a=";
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