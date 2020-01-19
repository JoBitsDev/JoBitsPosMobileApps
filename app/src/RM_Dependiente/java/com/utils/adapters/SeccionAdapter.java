package com.utils.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.activities.R;
import com.services.models.SeccionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Capa: Adapter
 * Clase adapter del menu de secciones, encargada de manejar la vista de la lista.
 *
 * @extends de ArrayAdapter para poder implementar un nuevo adapter basado en el modelo SeccionModel.
 * @implements Filterable para poder filtrar las listas segun lo desee el usuario
 */

public class SeccionAdapter extends ArrayAdapter<SeccionModel> implements Filterable {

    private Context context;
    private List<SeccionModel> secciones;
    private List<SeccionModel> displayedSecciones;

    /**Constructor*/

    public SeccionAdapter(Context context, int textViewResourceId, List<SeccionModel> secciones) {
        super(context, textViewResourceId, secciones);
        this.context = context;
        this.secciones = secciones;
        this.displayedSecciones = secciones;
    }

    /**Metodo que devuelve la cantidad de secciones que hay en la lista*/

    @Override
    public int getCount() {
        return this.displayedSecciones.size();
    }

    /**Metodo que devuelve el item seleccionado en la lista*/

    @Override
    public SeccionModel getItem(int position) {
        return this.displayedSecciones.get(position);
    }

    /**Metodo que devuelve la posicion en la lista de un item selecconado*/

    public int getPosition(SeccionModel seccion) {
        int pos = -1;
        if (seccion != null) {
            for (int i = 0; i < displayedSecciones.size(); i++) {
                if (displayedSecciones.get(i).getNombreSeccion().equalsIgnoreCase(seccion.getNombreSeccion())) {
                    pos = i;
                }
            }
        }
        return pos;
    }

    /** Metodo que establece la vista a adaptar en la lista*/

    public View getView(final int position, View convertView, ViewGroup parent) {

        View item = convertView;
        SeccionViewHolder holder;
        if (item == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            item = inflater.inflate(R.layout.list_parent, null);

            holder = new SeccionViewHolder();
            holder.nombreSeccion = (TextView) item.findViewById(R.id.boxParent);
            item.setTag(holder);
        } else {
            holder = (SeccionViewHolder) item.getTag();
        }
        String nombre = displayedSecciones.get(position).getNombreSeccion();
       // String nombre = displayedSecciones.get(position).getNombreSeccion().replace(" ", "\n");//TODO: ni idea de esto
        holder.nombreSeccion.setText(nombre);
        return (item);
    }

    //
    //Implementing filterable interface
    //

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

                displayedSecciones = (List<SeccionModel>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<SeccionModel> filteredArrList = new ArrayList<SeccionModel>();

                if (secciones == null) {
                    secciones = new ArrayList<SeccionModel>(displayedSecciones); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = secciones.size();
                    results.values = secciones;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < secciones.size(); i++) {
                        boolean contain = false;
                        SeccionModel aux = new SeccionModel(secciones.get(i).getNombreSeccion(),
                                secciones.get(i).getCartacodCarta());
                        for (int j = 0; j < secciones.get(i).getProductos().size(); j++) {
                            String data = secciones.get(i).getProductos().get(j).getNombre();
                            if (data.toLowerCase().contains(constraint.toString())) {
                                if (!contain) {
                                    aux.addProducto(secciones.get(i).getProductos().get(j));
                                    filteredArrList.add(aux);
                                    contain = true;
                                } else {
                                    aux.addProducto(secciones.get(i).getProductos().get(j));
                                }
                            }
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

    static class SeccionViewHolder{
        TextView nombreSeccion;
    }

}
