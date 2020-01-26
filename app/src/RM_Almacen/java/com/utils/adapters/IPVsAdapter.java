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
import com.services.models.IpvRegistroModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Capa: Adapter
 * Clase adapter de la lista de areas respecto ingresos y gastos, encargada de manejar la vista de la lista.
 *
 * @extends de ArrayAdapter para poder implementar un nuevo adapter basado en el modelo AreaListModel
 */

public class IPVsAdapter extends ArrayAdapter<String> implements Filterable {

    private Context context;
    private List<IpvRegistroModel> objects, displayedObjects;

    /**
     * Constructor
     */

    public IPVsAdapter(Context context, int textViewResourceId, List<IpvRegistroModel> objects) {
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
        return displayedObjects.get(position).toString();
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
            item = inflater.inflate(R.layout.list_ipvs, null);

            holder = new ViewHolder();
            holder.inicio = (TextView) item.findViewById(R.id.editTextInicio);
            holder.entrada = (TextView) item.findViewById(R.id.editTextEntrada);
            holder.disponibles = (TextView) item.findViewById(R.id.editTextDisponible);
            holder.consumidos = (TextView) item.findViewById(R.id.editTextCosumido);
            holder.finals = (TextView) item.findViewById(R.id.editTextFinal);
            item.setTag(holder);
        } else {
            holder = (ViewHolder) item.getTag();
        }
        holder.inicio.setText(displayedObjects.get(position).getInicio()+"");
        holder.entrada.setText(displayedObjects.get(position).getEntrada()+"");
        holder.disponibles.setText(displayedObjects.get(position).getDisponible()+"");
        holder.consumidos.setText(displayedObjects.get(position).getConsumo()+"");
        holder.finals.setText(displayedObjects.get(position).getFinal1()+"");
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

                displayedObjects = (List<IpvRegistroModel>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<IpvRegistroModel> filteredArrList = new ArrayList<IpvRegistroModel>();

                if (objects == null) {
                    objects = new ArrayList<IpvRegistroModel>(displayedObjects); // saves the original data in mOriginalValues
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
                    for (IpvRegistroModel i : objects) {
                        String data = i.getIpvRegistroPK().getIpvinsumocodInsumo();
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
        TextView inicio,
                entrada,
                disponibles,
                consumidos,
                finals;
    }
}
