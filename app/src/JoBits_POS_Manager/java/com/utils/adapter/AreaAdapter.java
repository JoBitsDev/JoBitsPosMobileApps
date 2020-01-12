package com.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.activities.R;

import java.util.List;

/**
 * Capa: Adapter
 * Clase adapter de la lista de areas respecto ingresos y gastos, encargada de manejar la vista de la lista.
 *
 * @extends de ArrayAdapter para poder implementar un nuevo adapter basado en el modelo ...
 */

public class AreaAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> change;

    /**
     * Constructor
     */

    public AreaAdapter(Context context, int textViewResourceId, List<String> change) {
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
            item = inflater.inflate(R.layout.area_list, null);

            holder = new ViewHolder();
            holder.codigo = (TextView) item.findViewById(R.id.editTextCodigo);
            holder.nombre = (TextView) item.findViewById(R.id.editTextNombre);
            holder.ventaNetaArea = (TextView) item.findViewById(R.id.editTextNeta);
            holder.ventaReal = (TextView) item.findViewById(R.id.editTextReal);
            item.setTag(holder);
        } else {
            holder = (ViewHolder) item.getTag();
        }
        holder.codigo.setText("");
        holder.nombre.setText("");
        holder.ventaNetaArea.setText("");
        holder.ventaReal.setText("");
        return (item);
    }

    /**
     * El holder que contiene los textView que seran mdificados
     */

    static class ViewHolder {
        TextView codigo,
                nombre,
                ventaNetaArea,
                ventaReal;
    }
}
