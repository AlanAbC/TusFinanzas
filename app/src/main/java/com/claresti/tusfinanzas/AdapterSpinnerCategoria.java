package com.claresti.tusfinanzas;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterSpinnerCategoria extends BaseAdapter {

    private LayoutInflater inflator;
    private ArrayList<Categoria> categoria;

    public AdapterSpinnerCategoria(Context context, ArrayList<Categoria> categoria) {
        inflator = LayoutInflater.from(context);
        this.categoria = categoria;
    }

    @Override
    public int getCount() {
        return categoria.size();
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
        convertView = inflator.inflate(R.layout.spiner_categoria, null);
        TextView txt_cat = (TextView) convertView.findViewById(R.id.txt_categoria);
        txt_cat.setText(categoria.get(position).getCat_nombre());
        return convertView;
    }
}
