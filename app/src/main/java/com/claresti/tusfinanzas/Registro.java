package com.claresti.tusfinanzas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mansi on 04/05/2017.
 */

public class Registro extends AppCompatActivity {

    //Declaracion variables layout
    private EditText nombre;
    private EditText apellidos;
    private EditText mail;
    private EditText password1;
    private EditText password2;
    private Spinner spin_registro;
    private List<String> monedas = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registro);
        spin_registro=(Spinner)findViewById(R.id.sp_currency);
        monedas.add("MXN");
        monedas.add("USD");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, monedas);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_registro.setAdapter(dataAdapter);

    }

}
