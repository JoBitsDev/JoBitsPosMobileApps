package com.utils.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Capa: Services
 * Adapter del filtro .
 */
public class FilterAdapter {

    private Context context;
    private String[] filtros= new String[4];
    private int resource;

    public FilterAdapter(Context context, int resource) {
        this.context = context;
        this.resource = resource;
        this.filtros[0] = "Nombre";
        this.filtros[1] = "Cantidad";
        this.filtros[2] = "Monto";
        this.filtros[3] = "Precio";
    }

    public ArrayAdapter<String> createAdapter() {
        return new ArrayAdapter<String>(context, resource, filtros);
    }
}
