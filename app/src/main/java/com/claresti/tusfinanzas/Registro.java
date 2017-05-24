package com.claresti.tusfinanzas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Registro extends AppCompatActivity {

    //Declaracion variables layout
    private EditText input_nombre;
    private EditText input_apellidos;
    private EditText input_email;
    private EditText input_password1;
    private EditText input_password2;
    private Spinner spin_registro;
    private Button btn_registrar;
    private RelativeLayout ventana;
    private ProgressBar progreso;

    //Declaracion array contenido Spinner
    private String[] monedas = {"MXN", "USD"};

    //Declaracion variable para la seleccion del spinner
    private int seleccionSpinner;

    //Declaracion de Urls y la base de datos
    private Urls urls;
    private BD db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registro);

        //Asignacion de variables layout
        ventana = (RelativeLayout)findViewById(R.id.lay_principal);
        input_nombre = (EditText)findViewById(R.id.input_name);
        input_apellidos = (EditText)findViewById(R.id.input_lastName);
        input_email = (EditText)findViewById(R.id.input_email);
        input_password1 = (EditText)findViewById(R.id.input_password);
        input_password2 = (EditText)findViewById(R.id.input_valid_password);
        spin_registro = (Spinner)findViewById(R.id.sp_currency);
        btn_registrar = (Button)findViewById(R.id.btn_registrar);
        progreso = (ProgressBar)findViewById(R.id.progress);

        //Asignacion variables de urls y la bd
        urls = new Urls();
        db = new BD(getApplicationContext());

        //Asignar valor o si no se cambia la seleccion del spinner
        seleccionSpinner = 0;

        //Cargar Spinner
        spinnerLoad();

        //Asignacion de listeners
        listenerBoton();
    }

    private void listenerBoton() {
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input_nombre.getText().toString().equals("")){
                    msg("Ingrese un nombre");
                }else if(input_apellidos.getText().toString().equals("")){
                    msg("Ingrese sus apellidos");
                }else if(input_email.getText().toString().equals("")){
                    msg("Ingrese un correo electronico");
                }else if(input_password1.getText().toString().equals("")){
                    msg("Ingrese una contraseña");
                }else if(!input_password1.getText().toString().equals(input_password2.getText().toString())){
                    msg("Las contraseñas no coinciden");
                }else{
                    ingresarUsuario();
                }
            }
        });
    }

    private void ingresarUsuario() {
        progreso.setVisibility(View.VISIBLE);
        Log.i("JSON", "Si entro");
        final Gson gson = new Gson();
        JsonObjectRequest request;
        VolleySingleton.getInstance(Registro.this).
                addToRequestQueue(
                        request = new JsonObjectRequest(
                                Request.Method.GET,
                                urls.getUrlLogin() + "insertar&nom=" + input_nombre.getText().toString() +
                                        "&ape=" + input_apellidos.getText().toString() +
                                        "&mail=" + input_email.getText().toString() +
                                        "&pas=" + input_password1.getText().toString() +
                                        "&pre=0" +
                                        "&mon=" + monedas[seleccionSpinner],
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            String res = response.getString("res");
                                            switch(res){
                                                case "1":
                                                    Intent i = new Intent(Registro.this, Login.class);
                                                    startActivity(i);
                                                    finish();
                                                    progreso.setVisibility(View.GONE);
                                                    break;
                                                case "0":
                                                    msg("Ocurrio un problema al conectarse con el sertvidor");
                                                    progreso.setVisibility(View.GONE);
                                                    break;
                                                default:

                                                    progreso.setVisibility(View.GONE);
                                                    msg("El correo ya esta registrado, prueba con otro correo");
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
     * funcion encargada de llenar el espiner
     */
    private void spinnerLoad(){
        spin_registro.setAdapter(new AdapterSpinerMoneda(getApplicationContext(), monedas));
        spin_registro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seleccionSpinner = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Funcion para crear un mensaje en pantalla
     * @param msg
     */
    public void msg(String msg){
        Snackbar.make(ventana, msg, Snackbar.LENGTH_SHORT).setAction("Aceptar", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }

}
