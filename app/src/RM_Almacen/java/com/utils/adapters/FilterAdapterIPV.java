package com.utils.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Capa: Services
 * Adapter del filtro del almacen.
 */
public class FilterAdapterIPV {
    private Context context;
    private String[] filtros;
    private int resource;

    public FilterAdapterIPV(Context context, int resource, String[] filtros) {
        this.context = context;
        this.resource = resource;
        this.filtros = new String[filtros.length + 1];
        this.filtros[0] = "Seleccione...";
        System.arraycopy(filtros, 0, this.filtros, 1, filtros.length);
    }

    public ArrayAdapter<String> createAdapter() {
        return new ArrayAdapter<String>(context, resource, filtros);
    }
}
