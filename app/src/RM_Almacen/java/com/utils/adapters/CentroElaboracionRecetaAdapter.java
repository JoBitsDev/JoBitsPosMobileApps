package com.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.activities.R;
import com.services.models.InsumoAlmacenModel;

import java.util.List;

/**
 * Capa: Adapter
 * Clase adapter de las listas del centro de elaboracion encargada de manejar la vista de la lista.
 *
 * @extends de ArrayAdapter para poder implementar un nuevo adapter basado en el  InsumoAlmacenModel
 */
public class CentroElaboracionRecetaAdapter extends ArrayAdapter<InsumoAlmacenModel> {

    private Context context;
    private List<InsumoAlmacenModel> objects, displayedObjects;


    private View.OnLongClickListener addListener;
    private View.OnLongClickListener removeListener;

    /**
     * Constructor
     */

    public CentroElaboracionRecetaAdapter(Context context, int textViewResourceId, List<InsumoAlmacenModel> objects) {
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
    public InsumoAlmacenModel getItem(int position) {
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
            item = inflater.inflate(R.layout.list_elaboracion_receta, null);

            holder = new ViewHolder();
            holder.nombre = (TextView) item.findViewById(R.id.textViewNombreInsumo);
            holder.cant = (TextView) item.findViewById(R.id.textViewCant);
            holder.add = (Button) item.findViewById(R.id.buttonMas);
            holder.remove = (Button) item.findViewById(R.id.buttonMenos);

            holder.add.setOnLongClickListener(addListener);
            holder.remove.setOnLongClickListener(removeListener);
            item.setTag(holder);
        } else {
            holder = (ViewHolder) item.getTag();
        }
        holder.nombre.setText(displayedObjects.get(position).getInsumoModel().getNombre());
        holder.cant.setText(displayedObjects.get(position).getCantidad() + "");

        holder.add.setTag(position);
        holder.remove.setTag(position);
        return (item);
    }

    public View.OnLongClickListener getAddListener() {
        return addListener;
    }

    public void setAddListener(View.OnLongClickListener addListener) {
        this.addListener = addListener;
    }

    public View.OnLongClickListener getRemoveListener() {
        return removeListener;
    }

    public void setRemoveListener(View.OnLongClickListener removeListener) {
        this.removeListener = removeListener;
    }

    /**
     * El holder que contiene los textView que seran mdificados
     */

    static class ViewHolder {
        TextView nombre,
                cant;
        Button add,
                remove;
    }
}
