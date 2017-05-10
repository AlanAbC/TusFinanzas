package com.claresti.tusfinanzas;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

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

    //Declaracion array contenido Spinner
    private String[] monedas = {"MXN", "USD"};


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
        spin_registro= (Spinner)findViewById(R.id.sp_currency);
        btn_registrar = (Button)findViewById(R.id.btn_registrar);

        //Cargar Spinner
        spinnerLoad();
    }

    /**
     * funcion encargada de llenar el espiner
     */
    private void spinnerLoad(){
        spin_registro.setAdapter(new AdapterSpinerMoneda(getApplicationContext(), monedas));
        spin_registro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
