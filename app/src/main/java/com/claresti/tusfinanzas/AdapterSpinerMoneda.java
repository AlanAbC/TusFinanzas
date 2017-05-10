package com.claresti.tusfinanzas;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterSpinerMoneda extends BaseAdapter {

    private LayoutInflater inflator;
    private String[] monedas;

    public AdapterSpinerMoneda(Context context, String[] monedas) {
        inflator = LayoutInflater.from(context);
        this.monedas = monedas;
    }

    @Override
    public int getCount() {
        return monedas.length;
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
        convertView = inflator.inflate(R.layout.spiner_registro, null);
        TextView txt_moneda = (TextView) convertView.findViewById(R.id.txt_moneda);
        txt_moneda.setText(monedas[position]);
        return convertView;
    }
}
