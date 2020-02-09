package com.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.activities.R;
import com.services.models.DetallesVentasModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Capa: Adapter
 * Clase adapter de la lista de detalles de ventas encargada de manejar la vista de la lista.
 *
 * @extends de ArrayAdapter para poder implementar un nuevo adapter basado en el modelo
 */
public class DetallesVentasAdapter extends ArrayAdapter<DetallesVentasModel> {

    private Context context;
    private List<DetallesVentasModel> objects, displayedObjects;

    /**
     * Constructor
     */

    public DetallesVentasAdapter(Context context, int textViewResourceId, List<DetallesVentasModel> objects) {
        super(context, textViewResourceId);
        this.context = context;
        this.objects = objects;
        displayedObjects = objects;
    }

    @Override
    public int getCount() {
        return displayedObjects.size();
    }

    @Override
    public DetallesVentasModel getItem(int position) {
        return displayedObjects.get(position);
    }

    /**
     * Devuelve la vista del adapter.
     *
     * @param position    del elemento.
     * @param convertView View de la aplicacion.
     * @param parent      Padre que lo llama
     * @return el View del adapter.
     */
    public View getView(final int position, View convertView, ViewGroup parent) {

        View item = convertView;
        ViewHolder holder;
        if (item == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            item = inflater.inflate(R.layout.detalles_list, null);

            holder = new ViewHolder();
            holder.producto = (TextView) item.findViewById(R.id.editTextProducto);
            holder.cantidad = (TextView) item.findViewById(R.id.editTextCantidad);
            holder.precio = (TextView) item.findViewById(R.id.editTextPrecio);
            holder.monto = (TextView) item.findViewById(R.id.editTextMonto);
            item.setTag(holder);
        } else {
            holder = (ViewHolder) item.getTag();
        }
        DetallesVentasModel model = objects.get(position);
        holder.producto.setText(model.getNombreProducto());
        holder.cantidad.setText(model.getCantidad() + "");
        holder.precio.setText(model.getPrecioVenta());
        holder.monto.setText(model.getMontoVenta());
        return (item);
    }

    public DetallesVentasAdapter orderBy(String orden) {
        if (orden.toLowerCase().contains("nombre")) {
            orderByNombre();
        } else if (orden.toLowerCase().contains("cantidad")) {
            orderByCantidad();
        } else if (orden.toLowerCase().contains("monto")) {
            orderByMonto();
        } else if (orden.toLowerCase().contains("precio")) {
            orderByPrecio();
        }
        return this;
    }

    private void orderByNombre() {
        Collections.sort(objects, new Comparator<DetallesVentasModel>() {
            @Override
            public int compare(DetallesVentasModel first, DetallesVentasModel second) {
                return first.getNombreProducto().compareTo(second.getNombreProducto());
            }
        });
    }

    private void orderByCantidad() {
        Collections.sort(objects, new Comparator<DetallesVentasModel>() {
            @Override
            public int compare(DetallesVentasModel first, DetallesVentasModel second) {
                return -1 * (int) (first.getCantidad() - second.getCantidad());
            }
        });
    }

    private void orderByPrecio() {
        Collections.sort(objects, new Comparator<DetallesVentasModel>() {
            @Override
            public int compare(DetallesVentasModel first, DetallesVentasModel second) {
                float f = Float.parseFloat(first.getPrecioVenta().split(" ")[0]);
                float s = Float.parseFloat(second.getPrecioVenta().split(" ")[0]);
                return -1 * (int) (f - s);
            }
        });
    }

    private void orderByMonto() {
        Collections.sort(objects, new Comparator<DetallesVentasModel>() {
            @Override
            public int compare(DetallesVentasModel first, DetallesVentasModel second) {
                float f = Float.parseFloat(first.getMontoVenta().split(" ")[0]);
                float s = Float.parseFloat(second.getMontoVenta().split(" ")[0]);
                return -1 * (int) (f - s);
            }
        });
    }

    /**
     * El holder que contiene los textView que seran mdificados
     */

    static class ViewHolder {
        TextView producto,
                cantidad,
                precio,
                monto;
    }
}
