package com.utils.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.activities.R;
import com.services.models.ProductoVentaModel;
import com.services.models.ProductoVentaOrdenModel;
import com.services.models.SeccionModel;
import com.utils.EnvironmentVariables;

import java.util.List;

public class MenuAdapterThis extends ArrayAdapter<ProductoVentaModel> {

    public final int c = Color.GREEN;
    private Context context;
    private List<ProductoVentaModel> secciones;

    public MenuAdapterThis(Context context, int textViewResourceId, List<ProductoVentaModel> secciones) {
        super(context, textViewResourceId, secciones);
        this.context = context;
        this.secciones = secciones;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View item = convertView;
        ViewHolder holder;
        if (item == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            item = inflater.inflate(R.layout.list_menu, null);

            holder = new ViewHolder();
            holder.nombreProducto = (TextView) item.findViewById(R.id.textViewNombre);
            holder.precioVenta = (TextView) item.findViewById(R.id.textViewPrecio);
            item.setTag(holder);
        } else {
            holder = (ViewHolder) item.getTag();
        }
        holder.precioVenta.setTextColor(c);
        holder.nombreProducto.setText(secciones.get(position).getNombre());
        holder.precioVenta.setText(secciones.get(position).getPrecioVenta() + " " + EnvironmentVariables.MONEDA_PRINCIPAL);
        return (item);
    }

    static class ViewHolder {
        TextView nombreProducto,
                precioVenta;
    }
}