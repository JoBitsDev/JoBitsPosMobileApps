package com.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.activities.R;
import com.services.models.TransaccionModel;
import com.utils.EnvironmentVariables;

import java.util.ArrayList;
import java.util.List;

/**
 * Capa: Adapter
 * Clase adapter de la lista de operaciones realizadas encargada de manejar la vista de la lista.
 *
 * @extends de ArrayAdapter para poder implementar un nuevo adapter basado en el modelo TransaccionModel
 */
public class OperacionesAdapter extends ArrayAdapter<TransaccionModel> implements Filterable {
    private Context context;
    private List<TransaccionModel> objects, displayedObjects;

    /**
     * Constructor
     */

    public OperacionesAdapter(Context context, int textViewResourceId, List<TransaccionModel> objects) {
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
    public TransaccionModel getItem(int position) {
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
            item = inflater.inflate(R.layout.lista_operaciones, null);

            holder = new ViewHolder();
            holder.fecha = (TextView) item.findViewById(R.id.editTextFecha);
            holder.hora = (TextView) item.findViewById(R.id.editTextHora);
            holder.insumo = (TextView) item.findViewById(R.id.editTextInsumo);
            holder.cant = (TextView) item.findViewById(R.id.editTextCant);
            holder.tipoOperacion = (TextView) item.findViewById(R.id.editTextTipoOp);
            item.setTag(holder);
        } else {
            holder = (ViewHolder) item.getTag();
        }
        holder.fecha.setText(EnvironmentVariables.DATE_FORMAT.format(displayedObjects.get(position).getFecha()));
        holder.hora.setText(EnvironmentVariables.HOUR_FORMAT.format(displayedObjects.get(position).getHora()));
        holder.insumo.setText(displayedObjects.get(position).getInsumocodInsumo().getNombre());
        holder.cant.setText(displayedObjects.get(position).getCantidad() + "");
        holder.tipoOperacion.setText(displayedObjects.get(position).getDescripcion() + "");
        return (item);
    }


    /**
     * <p>Returns a filter that can be used to constrain data with a filtering
     * pattern.</p>
     * <p>
     * <p>This method is usually implemented by {@link Adapter}
     * classes.</p>
     *
     * @return a filter used to constrain data
     */
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                displayedObjects = (List<TransaccionModel>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<TransaccionModel> filteredArrList = new ArrayList<TransaccionModel>();

                if (objects == null) {
                    objects = new ArrayList<TransaccionModel>(displayedObjects); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0 || constraint.toString().isEmpty()) {
                    // set the Original result to return
                    results.count = objects.size();
                    results.values = objects;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (TransaccionModel i : objects) {
                        String data = i.toString();
                        if (data.toLowerCase().contains(constraint.toString())) {
                            filteredArrList.add(i);
                        }
                    }
                    // set the Filtered result to return
                    results.count = filteredArrList.size();
                    results.values = filteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    /**
     * El holder que contiene los textView que seran mdificados
     */

    static class ViewHolder {
        TextView fecha,
                hora,
                insumo,
                cant,
                tipoOperacion;
    }
}
