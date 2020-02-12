package com.utils.adapters;

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
 * Clase adapter de las listas del centro de elaboracion encargada de manejar la vista de la lista.
 *
 * @extends de ArrayAdapter para poder implementar un nuevo adapter basado en el modelo
 */
public class CentroElaboracionAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> objects, displayedObjects;

    /**
     * Constructor
     */

    public CentroElaboracionAdapter(Context context, int textViewResourceId, List<String> objects) {
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
            item = inflater.inflate(R.layout.list_elaboracion, null);

            holder = new ViewHolder();
            holder.nombre = (TextView) item.findViewById(R.id.textViewNombreInsumo);
            holder.cant = (TextView) item.findViewById(R.id.textViewCant);

            item.setTag(holder);
        } else {
            holder = (ViewHolder) item.getTag();
        }
        holder.nombre.setText("");
        holder.cant.setText("");
        return (item);
    }

    /**
     * El holder que contiene los textView que seran mdificados
     */

    static class ViewHolder {
        TextView nombre,
                cant;
    }
}
