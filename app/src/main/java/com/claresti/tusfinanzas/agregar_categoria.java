package com.claresti.tusfinanzas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class agregar_categoria extends AppCompatActivity {

    //Declaracion variables layout
    private EditText input_nombreCat;
    private EditText input_descripcionCat;
    private Button btn_registrar;
    private RelativeLayout ventana;
    private ProgressBar progreso;

    //Menu, Declaracion de variables
    private DrawerLayout drawerLayout;
    final List<MenuItem> items = new ArrayList<>();
    private Menu menu;
    private ImageView btnMenu;
    private NavigationView nav;

    //Declaracion de variable de base de datos y Urls
    private BD db;
    private Urls urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_agregar_categoria);

        //Asignacion variables de layout
        ventana = (RelativeLayout)findViewById(R.id.l_ventana);
        progreso = (ProgressBar)findViewById(R.id.progress);
        input_nombreCat = (EditText)findViewById(R.id.input_nombreCat);
        input_descripcionCat = (EditText)findViewById(R.id.input_descripcionCat);
        btn_registrar = (Button)findViewById(R.id.btn_registrar);


        //Asignacion de variable de la BD y Urls
        db = new BD(getApplicationContext());
        urls = new Urls();

        //Menu, Inicia las variables del menu y llama la funcion encargada de su manipulacion
        drawerLayout = (DrawerLayout) findViewById(R.id.dLayout);
        nav = (NavigationView)findViewById(R.id.navigation);
        menu = nav.getMenu();
        menuNav();

        //Asignacion listeners del boton
        listenerBoton();

        //Ocultar teclado al iniciar la activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * funcion que valida los ediText no esten vacios y en caso de que no lo esten ejecuta la funcion
     * de insertarCategoria
     */
    private void listenerBoton() {
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input_nombreCat.getText().toString().equals("")){
                    msg("Ingrese un nombre de categoria");
                }else if(input_descripcionCat.getText().toString().equals("")){
                    msg("Ingrese una descripcion de la categoria");
                }else{
                    ingresarCategoria();
                }
            }
        });
    }

    /**
     * funcion que se encarga de insertar la categoria a la base de datos
     */
    private void ingresarCategoria() {
        progreso.setVisibility(View.VISIBLE);
        Log.i("JSON", "Si entro");
        final Gson gson = new Gson();
        JsonObjectRequest request;
        VolleySingleton.getInstance(agregar_categoria.this).
                addToRequestQueue(
                        request = new JsonObjectRequest(
                                Request.Method.GET,
                                urls.getUrlCategorias() + "insertar&nom=" + input_nombreCat.getText().toString() +
                                        "&des=" + input_descripcionCat.getText().toString(),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            String res = response.getString("res");
                                            switch(res){
                                                case "1":
                                                    input_nombreCat.setText("");
                                                    input_descripcionCat.setText("");
                                                    msg("Se ingreso correctamente la categoria");
                                                    progreso.setVisibility(View.GONE);
                                                    break;
                                                case "0":
                                                    msg("Ocurrio un problema al conectarse con el sertvidor");
                                                    progreso.setVisibility(View.GONE);
                                                    break;
                                                default:

                                                    progreso.setVisibility(View.GONE);
                                                    msg("Ocurrio un problema al conectarse con el sertvidor");
                                                    break;
                                            }
                                        }catch(JSONException json){
                                            Log.e("JSON", json.toString());
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }
                        )
                );
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }

    /**
     * Funcion que da funcionalidad al menu
     */
    private void menuNav(){
        for(int i = 0; i < menu.size(); i++){
            items.add(menu.getItem(i));
        }
        items.get(1).setChecked(true);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                int pos = items.indexOf(item);
                if(pos == 0){
                    Intent i = new Intent(agregar_categoria.this, agregarGasto.class);
                    startActivity(i);
                }else if(pos == 1){

                }else if(pos == 2){
                    Intent i = new Intent(agregar_categoria.this, presupuestos.class);
                    startActivity(i);
                }else if(pos == 3) {
                    Intent i = new Intent(agregar_categoria.this, registros.class);
                    startActivity(i);
                }else if(pos == 4){
                    Intent i = new Intent(agregar_categoria.this, Configuracion.class);
                    startActivity(i);
                }else if(pos == 5){
                    Intent i = new Intent(agregar_categoria.this, acerca.class);
                    startActivity(i);
                }else if(pos == 6){
                    Usuario act = db.selectUsuario();
                    Usuario usu = new Usuario(act.getUsu_id(), "0", "0", "0", "0", "0", "0");
                    if(db.updateUsuario(usu).equals("1")){
                        Intent i = new Intent(agregar_categoria.this, Login.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                }
                drawerLayout.closeDrawer(nav);
                item.setChecked(false);
                return false;
            }
        });

        //Asignacion del header menu en una bariable
        View headerview = nav.getHeaderView(0);

        //Toma la imagen de ususario, la redondea y la coloca nuevamente
        ImageView imgUsuario = (ImageView)headerview.findViewById(R.id.img_Usuario);
        Drawable imgOriginal = imgUsuario.getDrawable(); //getResources().getDrawable(R.drawable.fondo3);
        Bitmap bitOriginal = ((BitmapDrawable) imgOriginal).getBitmap();
        RoundedBitmapDrawable rounderDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitOriginal);
        rounderDrawable.setCornerRadius(bitOriginal.getHeight());
        imgUsuario.setImageDrawable(rounderDrawable);

        //Funcionalidad del boton de menu
        btnMenu = (ImageView)findViewById(R.id.Btnmenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(nav);
            }
        });
    }

    /**
     * Funcion para crear un mensaje en pantalla
     * @param msg
     */
    public void msg(String msg){
        Snackbar.make(ventana, msg, Snackbar.LENGTH_LONG).setAction("Aceptar", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }
}
