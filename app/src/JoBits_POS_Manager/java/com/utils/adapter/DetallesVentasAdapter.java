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
 * Clase adapter de la lista de detalles de ventas encargada de manejar la vista de la lista.
 *
 * @extends de ArrayAdapter para poder implementar un nuevo adapter basado en el modelo
 */
public class DetallesVentasAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> objects, displayedObjects;

    /**
     * Constructor
     */

    public DetallesVentasAdapter(Context context, int textViewResourceId, List<String> objects) {
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
    public String getItem(int position) {
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
        holder.producto.setText("");
        holder.cantidad.setText("");
        holder.precio.setText("");
        holder.monto.setText("");
        return (item);
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
