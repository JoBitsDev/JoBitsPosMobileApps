package com.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.activities.R;
import com.services.models.DpteListModel;
import com.utils.EnvironmentVariables;

import java.util.List;

/**
 * Capa: Adapter
 * Clase adapter de la lista de dependientes respecto ingresos y gastos, encargada de manejar la vista de la lista.
 *
 * @extends de ArrayAdapter para poder implementar un nuevo adapter basado en el modelo DpteListModel
 */

public class DependientesAdapter extends ArrayAdapter<DpteListModel> {

    private Context context;
    private List<DpteListModel> dpteListModels;

    /**
     * Constructor
     */

    public DependientesAdapter(Context context, int textViewResourceId, List<DpteListModel> dpteListModels) {
        super(context, textViewResourceId, dpteListModels);
        this.context = context;
        this.dpteListModels = dpteListModels;
    }

    /**
     * Metodo que establece la vista a adaptar en la lista
     */
    public View getView(final int position, View convertView, ViewGroup parent) {

        View item = convertView;
        ViewHolder holder;
        if (item == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            item = inflater.inflate(R.layout.dependientes_list, null);

            holder = new ViewHolder();
            holder.usuario = (TextView) item.findViewById(R.id.editTextUsuario);
            holder.ordenesAtendidas = (TextView) item.findViewById(R.id.editTextOrden);
            holder.monto = (TextView) item.findViewById(R.id.editTextMonto);
            holder.pagoVenta = (TextView) item.findViewById(R.id.editTextPago);
            item.setTag(holder);
        } else {
            holder = (ViewHolder) item.getTag();
        }
        holder.usuario.setText(dpteListModels.get(position).getUsuario());
        holder.ordenesAtendidas.setText(dpteListModels.get(position).getOrdenesAtendidas() + "");
        holder.monto.setText(dpteListModels.get(position).getMonto() + EnvironmentVariables.MONEDA_PRINCIPAL);
        holder.pagoVenta.setText(dpteListModels.get(position).getPagoPorVentas() + EnvironmentVariables.MONEDA_PRINCIPAL);
        return (item);
    }

    /**
     * El holder que contiene los textView que seran mdificados
     */

    static class ViewHolder {
        TextView usuario,
                ordenesAtendidas,
                monto,
                pagoVenta;
    }
}
