package com.claresti.tusfinanzas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class presupuestos extends AppCompatActivity {

    //Declaracion variables layout
    private ListView lis_categoria;

    //Menu, Declaracion de variables
    private DrawerLayout drawerLayout;
    final List<MenuItem> items = new ArrayList<>();
    private Menu menu;
    private ImageView btnMenu;
    private NavigationView nav;

    //Declaracion de variable de base de datos
    private BD db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_presupuestos);

        //Declaracion de variables layout
        lis_categoria = (ListView)findViewById(R.id.lis_categoria);

        //Asignacion de variable de la BD
        db = new BD(getApplicationContext());

        //Menu, Inicia las variables del menu y llama la funcion encargada de su manipulacion
        drawerLayout = (DrawerLayout) findViewById(R.id.dLayout);
        nav = (NavigationView)findViewById(R.id.navigation);
        menu = nav.getMenu();
        menuNav();
    }

    /**
     * Funcion que da funcionalidad al menu
     */
    private void menuNav(){
        for(int i = 0; i < menu.size(); i++){
            items.add(menu.getItem(i));
        }
        items.get(2).setChecked(true);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                int pos = items.indexOf(item);
                if(pos == 0){
                    Intent i = new Intent(presupuestos.this, agregarGasto.class);
                    startActivity(i);
                }else if(pos == 1){
                    Intent i = new Intent(presupuestos.this, agregar_categoria.class);
                    startActivity(i);
                }else if(pos == 2){

                }else if(pos == 3) {
                    Intent i = new Intent(presupuestos.this, registros.class);
                    startActivity(i);
                }else if(pos == 4){
                    Intent i = new Intent(presupuestos.this, Configuracion.class);
                    startActivity(i);
                }else if(pos == 5){
                    Intent i = new Intent(presupuestos.this, acerca.class);
                    startActivity(i);
                }else if(pos == 6){
                    Usuario act = db.selectUsuario();
                    Usuario usu = new Usuario(act.getUsu_id(), "0", "0", "0", "0", "0", "0");
                    if(db.updateUsuario(usu).equals("1")){
                        Intent i = new Intent(presupuestos.this, Login.class);
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
}
