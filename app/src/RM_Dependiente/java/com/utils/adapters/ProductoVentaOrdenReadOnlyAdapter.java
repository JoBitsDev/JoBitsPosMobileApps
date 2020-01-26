package com.utils.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.activities.R;
import com.services.models.ProductoVentaOrdenModel;

import java.util.List;

/**
 * Created by Jessica on 27/12/2019
 */
public class ProductoVentaOrdenReadOnlyAdapter extends ArrayAdapter<ProductoVentaOrdenModel> {
    public final int c = Color.GREEN;
    List<ProductoVentaOrdenModel> objects;
    Context context;

    public ProductoVentaOrdenReadOnlyAdapter(Context context, int resource, List<ProductoVentaOrdenModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View item = convertView;
        ViewHolder holder;
        if (item == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            item = inflater.inflate(R.layout.list_orden_readonly, null);

            holder = new ViewHolder();
            holder.nombreProducto = (TextView) item.findViewById(R.id.textNombreReadOnly);
            holder.precioVenta = (TextView) item.findViewById(R.id.textPrecioReadOnly);
            holder.cantidad = (TextView) item.findViewById(R.id.textCantidadReadOnly);
            item.setTag(holder);
        } else {
            holder = (ViewHolder) item.getTag();
        }
        holder.precioVenta.setTextColor(c);
        holder.nombreProducto.setText(objects.get(position).getProductoVentaModel().getNombre());
        holder.precioVenta.setText(String.valueOf(
                objects.get(position).getProductoVentaModel().getPrecioVenta() *
                        objects.get(position).getCantidad()));
        holder.cantidad.setText(String.valueOf(objects.get(position).getCantidad()));
        return (item);
    }

    static class ViewHolder {
        TextView nombreProducto,
                precioVenta,
                cantidad;
    }
}

