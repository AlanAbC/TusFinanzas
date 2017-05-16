package com.claresti.tusfinanzas;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    //Declaracion variables layout
    private RelativeLayout ventana;
    private ProgressBar progreso;
    private EditText input_email;
    private EditText input_password;
    private TextView btn_registro;
    private Button btn_ingresar;
    //Variables de urls
    private Urls urls;
    private static final String TAG = "Json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        //Asignacion de variables del layout
        input_email = (EditText)findViewById(R.id.input_user);
        input_password = (EditText)findViewById(R.id.input_password);
        btn_registro = (TextView) findViewById(R.id.btn_registro);
        btn_ingresar = (Button) findViewById(R.id.btn_ingresar);
        ventana = (RelativeLayout)findViewById(R.id.l_ventana);
        progreso = (ProgressBar)findViewById(R.id.progress);

        //Asignacion de variables
        urls = new Urls();

        //Listener de los botones
        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Registro.class);
                startActivity(i);
            }
        });
        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarLogin();
            }
        });

        //Ocultar teclado al iniciar la activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * funcion que se encarga de obtener los valores de los EditText y compureva
     * que no esten vacios, en caso de que esten vacios regresa mensaje con
     * cual campo esta vacio y en caso contrario ejecuta la funcion de login
     */
    private void validarLogin() {
        String mail = input_email.getText().toString();
        String pass = input_password.getText().toString();
        if(mail.equals("")){
            msg("Ingrese su correo electronico");
        }else if(pass.equals("")){
            msg("Ingrese su contraseña");
        }else{
            login(mail, pass);
        }
    }

    /**
     * funcion que solicita a la api los datos del login y los valida si existe
     * el usuario en caso de existir guarda la informacion en la base de
     * datos local y en caso contrario marca mensaje de que no existe el
     * usuario o contraseña incorrecta
     * @param mail correo del usuario
     * @param pass contraseña del usuario
     */
    private void login(String mail, String pass) {
        progreso.setVisibility(View.VISIBLE);
        Log.i("JSON", "Si entro");
        final Gson gson = new Gson();
        JsonObjectRequest request;
        VolleySingleton.getInstance(Login.this).
                addToRequestQueue(
                        request = new JsonObjectRequest(
                                Request.Method.GET,
                                urls.getUrlLogin() + "login&mail=" + mail + "&pas=" + pass,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            String res = response.getString("res");
                                            switch(res){
                                                case "1":
                                                    JSONArray jArrayMarcadores = response.getJSONArray("usuario");
                                                    Usuario[] arrayUsuario = gson.fromJson(jArrayMarcadores.toString(), Usuario[].class);
                                                    if(arrayUsuario.length == 1) {
                                                        Log.i("JSON", arrayUsuario.length + "");
                                                        for (Usuario usuaro : arrayUsuario) {
                                                            Log.i("JSON - for", "Si entra");
                                                            //crear base de datos local i guardar la informacion del objeto
                                                            progreso.setVisibility(View.GONE);
                                                            msg("Login correcto " + usuaro.getUsu_mail());
                                                        }
                                                    }
                                                    progreso.setVisibility(View.GONE);
                                                    break;
                                                case "0":
                                                    //Regresar mensaje de que no hay registros
                                                    progreso.setVisibility(View.GONE);
                                                    msg("Correo o contraseña in correctos");
                                                    animar();
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
     * Funcion que anima los editText
     */
    private void animar() {

    }

    /**
     * funcion encargada de crear un snackbar con un mensaje y un boton de aceptar para ocultarla
     * @param msg String que contiene el mensaje
     */
    private void msg(String msg){
        Snackbar.make(ventana, msg, Snackbar.LENGTH_SHORT).setAction("Aceptar", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }

}
