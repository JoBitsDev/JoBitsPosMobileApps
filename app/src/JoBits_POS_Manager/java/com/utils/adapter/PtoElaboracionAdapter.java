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
 * Clase adapter de la lista de puntos de elaboracion respecto ingresos y gastos, encargada de manejar la vista de la lista.
 *
 * @extends de ArrayAdapter para poder implementar un nuevo adapter basado en el modelo ...
 */

public class PtoElaboracionAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> change;

    /**
     * Constructor
     */

    public PtoElaboracionAdapter(Context context, int textViewResourceId, List<String> change) {
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
            item = inflater.inflate(R.layout.pto_elaboracion_list, null);

            holder = new ViewHolder();
            holder.codigoPto = (TextView) item.findViewById(R.id.editTextCodigoPto);
            holder.nombrePto = (TextView) item.findViewById(R.id.editTextNombrePto);
            holder.montoPto = (TextView) item.findViewById(R.id.editTextMontoPto);
            item.setTag(holder);
        } else {
            holder = (ViewHolder) item.getTag();
        }
        holder.codigoPto.setText("");
        holder.nombrePto.setText("");
        holder.montoPto.setText("");
        return (item);
    }

    /**
     * El holder que contiene los textView que seran mdificados
     */

    static class ViewHolder {
        TextView codigoPto,
                nombrePto,
                montoPto;
    }
}
