package com.utils.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.activities.R;
import com.services.models.ProductoVentaModel;
import com.utils.EnvironmentVariables;

import java.util.List;

/**
 * Capa: Adapter
 * Clase adapter del menu de productos, encargada de manejar la vista de la lista.
 *
 * @extends de ArrayAdapter para poder implementar un nuevo adapter basado en el modelo ProductoVentaModel.
 */

public class MenuAdapterThis extends ArrayAdapter<ProductoVentaModel> {

    public final int c = Color.GREEN;
    private Context context;
    private List<ProductoVentaModel> secciones;

    /**
     * Constructor
     */

    public MenuAdapterThis(Context context, int textViewResourceId, List<ProductoVentaModel> secciones) {
        super(context, textViewResourceId, secciones);
        this.context = context;
        this.secciones = secciones;
    }

    /**
     * Metodo que establece la vista a adaptar en la lista
     */
    public View getView(final int position, View convertView, ViewGroup parent) {

        View item = convertView;
        ViewHolder holder;
        if (item == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            item = inflater.inflate(R.layout.list_menu, null);

            holder = new ViewHolder();
            holder.nombreProducto = (TextView) item.findViewById(R.id.textViewNombre);
            holder.precioVenta = (TextView) item.findViewById(R.id.textViewPrecio);
            holder.view = (ImageButton) item.findViewById(R.id.imageButtonRestantes);
            item.setTag(holder);
        } else {
            holder = (ViewHolder) item.getTag();
        }
        holder.precioVenta.setTextColor(c);
        holder.nombreProducto.setText(secciones.get(position).getNombre());
        holder.precioVenta.setText(secciones.get(position).getPrecioVenta() + " " + EnvironmentVariables.MONEDA_PRINCIPAL);
        holder.view.setTag(position);
        return (item);
    }

    /**
     * El holder que contiene los textView que seran mdificados
     */

    static class ViewHolder {
        TextView nombreProducto,
                precioVenta;
        ImageButton view;
    }
}