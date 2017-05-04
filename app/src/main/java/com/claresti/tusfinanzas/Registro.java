package com.claresti.tusfinanzas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mansi on 04/05/2017.
 */

public class Registro extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registro);
    }

}
