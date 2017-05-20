package com.claresti.tusfinanzas;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class agregarGasto extends AppCompatActivity {

    //Declaracion de variables layout
    private EditText input_monto;
    private EditText input_concepto;
    private TextView categoriaSpinner;
    private Spinner spin_categoria;
    private DatePicker date_fecha;
    private ImageView btn_mas;
    private ImageView btn_menos;
    private Button btn_registrar;
    private ProgressBar progreso;
    private RelativeLayout ventana;

    //Menu, Declaracion de variables
    private DrawerLayout drawerLayout;
    final List<MenuItem> items = new ArrayList<>();
    private Menu menu;
    private ImageView btnMenu;
    private NavigationView nav;

    //declaracion de array de categorias de la peticion
    private ArrayList<Categoria> categorias;

    //Declaracion de variable de base de datos
    private BD db;

    //Varibale de usuario
    private Usuario usu;

    //Declaracion de variables de urls+
    private Urls urls;

    //Declaracion para variable seleccionada del spinner
    private int spinnerSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_agregar_gasto);

        //Asignacion variables layout
        input_monto = (EditText)findViewById(R.id.input_monto);
        input_concepto = (EditText)findViewById(R.id.input_concepto);
        spin_categoria = (Spinner)findViewById(R.id.spin_categoria);
        categoriaSpinner = (TextView)findViewById(R.id.txt_categoria);
        date_fecha = (DatePicker)findViewById(R.id.date_fecha);
        btn_mas = (ImageView)findViewById(R.id.img_mas);
        btn_menos = (ImageView)findViewById(R.id.img_menos);
        btn_registrar = (Button)findViewById(R.id.btn_registrar);
        progreso = (ProgressBar)findViewById(R.id.progress);
        ventana = (RelativeLayout)findViewById(R.id.l_ventana);

        //Asignacion de variable de la BD
        db = new BD(getApplicationContext());

        //Asignacion la variable urls
        urls = new Urls();

        //Asignacion del usuario
        usu = db.selectUsuario();

        //asignacion de variable de array categorias
        categorias = new ArrayList<Categoria>();

        //Menu, Inicia las variables del menu y llama la funcion encargada de su manipulacion
        drawerLayout = (DrawerLayout) findViewById(R.id.dLayout);
        nav = (NavigationView)findViewById(R.id.navigation);
        menu = nav.getMenu();
        menuNav();

        //llamada a los listeners
        lsiteners();

        //llamada a cargar spinner
        cargarSpinner();

        //Ocultar teclado al iniciar la activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void llenarSpinner() {
        spin_categoria.setAdapter(new AdapterSpinnerCategoria(getApplicationContext(), categorias));
        spin_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoriaSpinner.setText(categorias.get(position).getCat_nombre());
                spinnerSelect = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * funcion encargada de realizar la peticion al servidor y obtener en un array las categorias
     * @return un array de las categorias obtenidas en la peticion
     */
    private void cargarSpinner() {
        progreso.setVisibility(View.VISIBLE);
        Log.i("JSON", "Si entro");
        final Gson gson = new Gson();
        JsonObjectRequest request;
        VolleySingleton.getInstance(agregarGasto.this).
                addToRequestQueue(
                        request = new JsonObjectRequest(
                                Request.Method.GET,
                                urls.getUrlCategorias() + "todo",
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            String res = response.getString("res");
                                            switch(res){
                                                case "1":
                                                    Log.i("peticion", "caso 1");
                                                    JSONArray jArrayMarcadores = response.getJSONArray("categorias");
                                                    Categoria[] arrayCategoria = gson.fromJson(jArrayMarcadores.toString(), Categoria[].class);
                                                    Log.i("peticion", "tamaño: " + arrayCategoria.length);
                                                    for(int i = 0; i < arrayCategoria.length; i++){
                                                        categorias.add(arrayCategoria[i]);
                                                    }
                                                    llenarSpinner();
                                                    progreso.setVisibility(View.GONE);
                                                    break;
                                                case "0":
                                                    Log.i("peticion", "caso 0");
                                                    arrayCategoria = null;
                                                    //Regresar mensaje de que no hay registros
                                                    progreso.setVisibility(View.GONE);
                                                    break;
                                                default:
                                                    Log.i("peticion", "caso default");
                                                    arrayCategoria = null;
                                                    progreso.setVisibility(View.GONE);
                                                    //msg("Ocurrio un problema al conectarse con el sertvidor");
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
     * funcion encargada de asignar los listener del activity
     */
    private void lsiteners() {
        //accion click al boton más
        btn_mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input_monto.getText().toString().equals("")){
                    input_monto.setText("100" + "" + "");
                }else{
                    double can = Double.parseDouble(input_monto.getText().toString()) + 100;
                    input_monto.setText(can + "" + "");
                }
            }
        });
        //accion click boton menos
        btn_menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input_monto.getText().toString().equals("")){
                    input_monto.setText("-100" + "" + "");
                }else{
                    double can = Double.parseDouble(input_monto.getText().toString()) - 100;
                    input_monto.setText(can + "" + "");
                }
            }
        });
        ////accion click boton registrar
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevoGasto();
            }
        });
    }

    /**
     * funcion encargada de registrar el gasto nuevo
     */
    private void nuevoGasto() {
        progreso.setVisibility(View.VISIBLE);
        Log.i("JSON", "Si entro");
        final Gson gson = new Gson();
        JsonObjectRequest request;
        VolleySingleton.getInstance(agregarGasto.this).
                addToRequestQueue(
                        request = new JsonObjectRequest(
                                Request.Method.GET,
                                urls.getUrlGastos() + "insertar&can=" + input_monto.getText().toString() +
                                        "&fec=" + date_fecha.getYear() + "-" + (date_fecha.getMonth() + 1) + "-" + date_fecha.getDayOfMonth() + " 00:00" +
                                        "&usuid=" + usu.getUsu_id() +
                                        "&catid=" + spinnerSelect ,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            String res = response.getString("res");
                                            switch(res){
                                                case "1":
                                                    msg("Se ingreso correctamnbte el registro");
                                                    input_monto.setText("");
                                                    input_concepto.setText("");
                                                    spin_categoria.setSelection(0);
                                                    spinnerSelect = 0;
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
        items.get(0).setChecked(true);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                int pos = items.indexOf(item);
                if(pos == 0){

                }else if(pos == 1){
                    Intent i = new Intent(agregarGasto.this, agregar_categoria.class);
                    startActivity(i);
                }else if(pos == 2){
                    Intent i = new Intent(agregarGasto.this, presupuestos.class);
                    startActivity(i);
                }else if(pos == 3) {
                    Intent i = new Intent(agregarGasto.this, registros.class);
                    startActivity(i);
                }else if(pos == 4){
                    Intent i = new Intent(agregarGasto.this, Configuracion.class);
                    startActivity(i);
                }else if(pos == 5){
                    Intent i = new Intent(agregarGasto.this, acerca.class);
                    startActivity(i);
                }else if(pos == 6){
                    Usuario act = db.selectUsuario();
                    Usuario usu = new Usuario(act.getUsu_id(), "0", "0", "0", "0", "0", "0");
                    if(db.updateUsuario(usu).equals("1")){
                        Intent i = new Intent(agregarGasto.this, Login.class);
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
