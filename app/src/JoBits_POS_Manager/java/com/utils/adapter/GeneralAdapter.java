package com.utils.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.activities.R;
import com.utils.EnvironmentVariables;

import java.util.List;

/**
 * Capa: Adapter
 * Clase adapter de la lista general de ingresos y gastos, encargada de manejar la vista de la lista.
 *
 * @extends de ArrayAdapter para poder implementar un nuevo adapter basado en el modelo ...
 */

public class GeneralAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> change;

    /**
     * Constructor
     */

    public GeneralAdapter(Context context, int textViewResourceId, List<String> change) {
        super(context, textViewResourceId, change);
        this.context = context;
        this.change = change;
    }

    /**
     * Metodo que establece la vista a adaptar en la lista
     */
    public View getView(final int position, View convertView, ViewGroup parent) {

        View item = convertView;
        ViewHolder holder;
        if (item == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            item = inflater.inflate(R.layout.general_list, null);

            holder = new ViewHolder();
            holder.ventaNeta = (TextView)item.findViewById(R.id.editTextVentaNeta);
            holder.total = (TextView)item.findViewById(R.id.editTextTotal);
            holder.autorizos = (TextView)item.findViewById(R.id.editTextAutorizos);
            holder.salarios = (TextView)item.findViewById(R.id.editTextSalarios);
            holder.insumos = (TextView)item.findViewById(R.id.editTextInsumos);
            holder.otros = (TextView)item.findViewById(R.id.editTextOtros);
            item.setTag(holder);
        } else {
            holder = (ViewHolder) item.getTag();
        }
        holder.ventaNeta.setText("");
        holder.total.setText("");
        holder.autorizos.setText("");
        holder.salarios.setText("");
        holder.insumos.setText("");
        holder.otros.setText("");
        return (item);
    }

    /**
     * El holder que contiene los textView que seran mdificados
     */

    static class ViewHolder {
        TextView ventaNeta,
                total,
                autorizos,
                salarios,
                insumos,
                otros;
    }
}
