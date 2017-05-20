package com.claresti.tusfinanzas;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AdapterListViewCategorias extends BaseAdapter {

    LayoutInflater inflator;
    ArrayList<Categoria> categorias;
    Context context;


    public AdapterListViewCategorias(Context context, ArrayList<Categoria> categorias) {
        inflator = LayoutInflater.from(context);
        this.categorias = categorias;
        this.context = context;
    }


    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflator.inflate(R.layout.categoria, null);
        //Asignacion de variables del layout
        final TextView txt_categorias = (TextView)convertView.findViewById(R.id.txt_categoria);
        final ImageView btn_mas = (ImageView)convertView.findViewById(R.id.img_mas);
        final ImageView btn_menos = (ImageView)convertView.findViewById(R.id.img_menos);
        final EditText edit_porcentaje = (EditText)convertView.findViewById(R.id.edit_porcentaje);
        txt_categorias.setText(categorias.get(position).getCat_nombre());
        btn_mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_porcentaje.getText().toString().equals("")){
                    edit_porcentaje.setText("100" + "" + "");
                }else{
                    double cant = Double.parseDouble(edit_porcentaje.getText().toString()) + 100;
                    edit_porcentaje.setText(cant + "" + "");
                }
            }
        });
        btn_menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_porcentaje.getText().toString().equals("")){
                    edit_porcentaje.setText("-100" + "" + "");
                }else{
                    double cant = Double.parseDouble(edit_porcentaje.getText().toString()) - 100;
                    edit_porcentaje.setText(cant + "" + "");
                }
            }
        });
        return convertView;
    }

}
