package com.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.activities.R;
import com.services.models.VentaResumenModel;

import java.util.List;

/**
 * Capa: Adapter
 * Clase adapter de la lista general de ingresos y gastos, encargada de manejar la vista de la lista.
 *
 * @extends de ArrayAdapter para poder implementar un nuevo adapter basado en el modelo VentaResumenModel.
 */

public class GeneralAdapter extends ArrayAdapter<VentaResumenModel> {

    private Context context;
    private List<VentaResumenModel> ventaResumen;

    /**
     * Constructor
     */

    public GeneralAdapter(Context context, int textViewResourceId, List<VentaResumenModel> ventaResumen) {
        super(context, textViewResourceId, ventaResumen);
        this.context = context;
        this.ventaResumen = ventaResumen;
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
            holder.ventaNeta = (TextView) item.findViewById(R.id.editTextVentaNeta);
            holder.total = (TextView) item.findViewById(R.id.editTextTotal);
            holder.autorizos = (TextView) item.findViewById(R.id.editTextAutorizos);
            holder.salarios = (TextView) item.findViewById(R.id.editTextSalarios);
            holder.insumos = (TextView) item.findViewById(R.id.editTextInsumos);
            holder.otros = (TextView) item.findViewById(R.id.editTextOtros);
            item.setTag(holder);
        } else {
            holder = (ViewHolder) item.getTag();
        }
        holder.ventaNeta.setText(ventaResumen.get(position).getVentaNeta() + "");
        holder.total.setText(ventaResumen.get(position).getVentaTotal() + "");
        holder.autorizos.setText(ventaResumen.get(position).getAutorizos() + "");
        holder.salarios.setText(ventaResumen.get(position).getGastosSalario() + "");
        holder.insumos.setText(ventaResumen.get(position).getGastosInsumo() + "");
        holder.otros.setText(ventaResumen.get(position).getGastosOtros() + "");
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
