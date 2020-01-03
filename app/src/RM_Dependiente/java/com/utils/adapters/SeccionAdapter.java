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

public class SeccionAdapter extends ArrayAdapter <SeccionModel> implements Filterable {

    public final int c = Color.GREEN;
    private Context context;
    private List<SeccionModel> secciones;
    private List<SeccionModel> displayedSecciones;

    public SeccionAdapter(Context context, int textViewResourceId, List<SeccionModel> secciones) {
        super(context, textViewResourceId, secciones);
        this.context = context;
        this.secciones = secciones;
        this.displayedSecciones = secciones;
    }


    @Override
    public int getCount() {
        return this.displayedSecciones.size();
    }

    @Override
    public SeccionModel getItem(int position) {
        return this.displayedSecciones.get(position);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View item = convertView;
        MenuAdapterThis.ViewHolder holder;
        if (item == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            item = inflater.inflate(R.layout.list_menu, null);

            holder = new MenuAdapterThis.ViewHolder();
            holder.nombreProducto = (TextView) item.findViewById(R.id.textViewNombre);
            holder.precioVenta = (TextView) item.findViewById(R.id.textViewPrecio);
            item.setTag(holder);
        } else {
            holder = (MenuAdapterThis.ViewHolder) item.getTag();
        }
        //String nombre = displayedSecciones.get(position).getNombreSeccion();
        String nombre = displayedSecciones.get(position).getNombreSeccion().replace(" ","\n");
        holder.nombreProducto.setText(nombre);
        holder.precioVenta.setVisibility(View.GONE);
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
            protected void publishResults(CharSequence constraint,FilterResults results) {

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
                        for (int j = 0;j<secciones.get(i).getProductos().size();j++){
                            String data = secciones.get(i).getProductos().get(j).getNombre();
                            if (data.toLowerCase().contains(constraint.toString())) {
                                if(!contain){
                                    aux.addProducto(secciones.get(i).getProductos().get(j));
                                    filteredArrList.add(aux);
                                    contain = true;
                                }
                                else{
                                    aux.addProducto(secciones.get(i).getProductos().get(j));
                                }
                            }
                        }}
                    // set the Filtered result to return
                    results.count = filteredArrList.size();
                    results.values = filteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

}
