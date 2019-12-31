package com.utils.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.activities.R;
import com.services.models.InsumoAlmacenModel;
import com.services.web_connections.AlmacenWebConnectionService;
import com.utils.EnvironmentVariables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Capa: Services
 * Adapter de los insumos del almacen.
 */
public class AlmacenInsumoAdapter extends ArrayAdapter<InsumoAlmacenModel> implements Filterable {

    public final int c = Color.GREEN;
    private List<InsumoAlmacenModel> objects, displayedObjects;
    private Context context;
    private View.OnLongClickListener listener;


    public AlmacenInsumoAdapter(Context context, int resource, List<InsumoAlmacenModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        displayedObjects = objects;
        Collections.sort(objects);
    }

    /**
     * Devuelve la vista del adapter.
     * @param position del elemento.
     * @param v View de la aplicacion.
     * @param parent Padre que lo llama
     * @return el View del adapter.
     */
    public View getView(final int position, View v, ViewGroup parent) {

        ViewHolder holder;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.list_insumo_almacen, null);

            holder = new ViewHolder();
            holder.nombreInsumo = (TextView) v.findViewById(R.id.textNombre);
            holder.precioMedio = (TextView) v.findViewById(R.id.editPrecioPromedio);
            holder.cantidad = (TextView) v.findViewById(R.id.textCantidad);
            holder.entrada = (ImageButton) v.findViewById(R.id.entradaButton);
            holder.salida = (ImageButton) v.findViewById(R.id.salidaButton);
            holder.stock = (TextView) v.findViewById(R.id.editTextStock);
            holder.um = (TextView) v.findViewById(R.id.textUM);

            setLongClick(holder.entrada);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        InsumoAlmacenModel i = getPosition(position);

        holder.um.setText(i.getInsumoModel().getUm());
        holder.nombreInsumo.setText(i.getInsumoModel().getNombre());
        holder.cantidad.setText(String.valueOf(i.getCantidad()));
        holder.precioMedio.setText("Costo Medio: "
                + EnvironmentVariables.setDosLugaresDecimales(i.getInsumoModel().getCostoPorUnidad()));

        //escribiendo el stock
        float diferencia = i.getCantidad() - i.getInsumoModel().getStockEstimation();
        String text = "-";
        int color = Color.BLACK;
        if (diferencia >= 0) {
            text = "+" + diferencia;
            color = diferencia > i.getInsumoModel().getStockEstimation() * 0.5 ? Color.GREEN : Color.YELLOW;
        } else {
            text = "" + diferencia;
            color = Color.RED;
        }

        holder.stock.setText(text);
        holder.stock.setTextColor(color);
        holder.entrada.setTag(position);
        holder.salida.setTag(position);


        return (v);


    }

    @Override
    public int getCount() {
        return displayedObjects.size();
    }

    /**
     * Accion cuando se hace el toque largo.
     *
     * @param view View de la aplicaicon.
     */
    public void setLongClick(View view) {
        view.setOnLongClickListener(listener);
    }

    /**
     * Devuelve el insumo en una posicion.
     *
     * @param position
     * @return
     */
    public InsumoAlmacenModel getPosition(int position) {
        return displayedObjects.get(position);
    }

    @Override
    public InsumoAlmacenModel getItem(int position) {
        return displayedObjects.get(position);
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

                displayedObjects = (List<InsumoAlmacenModel>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<InsumoAlmacenModel> filteredArrList = new ArrayList<InsumoAlmacenModel>();

                if (objects == null) {
                    objects = new ArrayList<InsumoAlmacenModel>(displayedObjects); // saves the original data in mOriginalValues
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
                    for (InsumoAlmacenModel i : objects) {
                        String data = i.getInsumoModel().getNombre();
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


    //
    //Implementing filterable interface
    //

    static class ViewHolder {
        TextView nombreInsumo,
                um,
                cantidad,
                precioMedio,
                stock;
        ImageButton entrada, salida;
    }


}

