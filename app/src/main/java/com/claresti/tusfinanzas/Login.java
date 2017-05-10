package com.claresti.tusfinanzas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    //Declaracion variables layout
    private EditText input_email;
    private EditText input_password;
    private TextView btn_registro;
    private Button btn_ingresar;

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
                Intent i = new Intent(Login.this,agregarGasto.class);
                startActivity(i);
            }
        });

        //Ocultar teclado al iniciar la activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
