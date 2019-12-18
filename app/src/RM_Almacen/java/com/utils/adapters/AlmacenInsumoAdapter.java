package com.utils.adapters;

import android.app.Activity;
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
 * Created by Jorge on 12/7/17.
 */
public class AlmacenInsumoAdapter extends ArrayAdapter<InsumoAlmacenModel> implements Filterable {
    public final int c = Color.GREEN;
    List<InsumoAlmacenModel> objects, displayedObjects;
    Activity context;
    View.OnLongClickListener listener;


    public AlmacenInsumoAdapter(Activity context, int resource, List<InsumoAlmacenModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        displayedObjects = objects;
        Collections.sort(objects);
    }

    public AlmacenInsumoAdapter(Activity context, int resource, List<InsumoAlmacenModel> objects, View.OnLongClickListener listener) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        displayedObjects = objects;
        this.listener = listener;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View item = convertView;
        ViewHolder holder;
        if (item == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.list_insumo_almacen, null);

            holder = new ViewHolder();
            holder.nombreInsumo = (TextView) item.findViewById(R.id.textNombre);
            holder.precioMedio = (TextView) item.findViewById(R.id.editPrecioPromedio);
            holder.cantidad = (TextView) item.findViewById(R.id.textCantidad);
            holder.entrada = (ImageButton) item.findViewById(R.id.entradaButton);
            holder.salida = (ImageButton) item.findViewById(R.id.salidaButton);
            holder.stock = (TextView) item.findViewById(R.id.editTextStock);
            holder.um = (TextView) item.findViewById(R.id.textUM);

            setLongClick(holder.entrada);
            item.setTag(holder);
        } else {
            holder = (ViewHolder) item.getTag();
        }
        InsumoAlmacenModel i = getPosition(position);

        holder.um.setText(i.getInsumoModel().getUm());
        holder.nombreInsumo.setText(i.getInsumoModel().getNombre());
        holder.cantidad.setText(String.valueOf(i.getCantidad()));
        holder.precioMedio.setText("Costo Medio: "
                + EnvironmentVariables.setDosLugaresDecimales(i.getInsumoModel().getCostoPorUnidad()));

        //escribiendo el stock
        float diferencia =  i.getCantidad()-i.getInsumoModel().getStockEstimation();
        String text = "-";
        int color = Color.BLACK;
        if (diferencia >= 0) {
            text = "+" + diferencia ;
            color = diferencia > i.getInsumoModel().getStockEstimation() * 0.5 ? Color.GREEN : Color.YELLOW;
        } else {
            text =  ""+ diferencia ;
            color = Color.RED;
        }

        holder.stock.setText(text);
        holder.stock.setTextColor(color);
        holder.entrada.setTag(position);
        holder.salida.setTag(position);


        return (item);


    }

    @Override
    public int getCount() {
        return displayedObjects.size();
    }

    public void setLongClick(View view) {
        view.setOnLongClickListener(listener);
    }

    public InsumoAlmacenModel getPosition(int position) {
        return displayedObjects.get(position);
    }

    @Override
    public InsumoAlmacenModel getItem(int position) {
       return displayedObjects.get(position);
    }

    public boolean contains(InsumoAlmacenModel p) {
        return displayedObjects.contains(p);
    }

    public void darEntrada(InsumoAlmacenModel p, AlmacenWebConnectionService o) {
        boolean existe = false;
//        ProductoVentaOrdenModel pv = null;
//        int i = 0;
//        for (; i< objects.size() && !existe ; i++){
//            pv = objects.get(i);
//           existe = pv.getProductoVentaModel().equals(p);
//
//        }
//        if(existe){
//            i--;
//            pv.getProductoVentaModel().increaceAmount();
//            objects.set(i,pv);
//
//        }
//        else{
//            ProductoVentaOrdenPKModel pk = new ProductoVentaOrdenPKModel(p.getPCod(),o.getCodOrden());
//            pv = new ProductoVentaOrdenModel(pk);
//            pv.setProductoVentaModel(p);
//            pv.getProductoVentaModel().setCantidad(1);
//            objects.add(pv);
//        }
//        notifyDataSetChanged();
    }

    public void darEntrada(InsumoAlmacenModel p, float ammount, AlmacenWebConnectionService o) {
        boolean existe = false;
//        ProductoVentaOrdenModel pv = null;
//        int i = 0;
//        for (; i< objects.size() && !existe ; i++){
//            pv = objects.get(i);
//            existe = pv.getProductoVentaModel().equals(p);
//        }
//        if(existe){
//            i--;
//            pv.getProductoVentaModel().setCantidad(pv.getProductoVentaModel().getCantidad()+ammount);
//            objects.set(i,pv);
//
//        }
//        else{
//            ProductoVentaOrdenPKModel pk = new ProductoVentaOrdenPKModel(p.getPCod(),o.getCodOrden());
//            pv = new ProductoVentaOrdenModel(pk);
//            pv.setProductoVentaModel(p);
//            pv.getProductoVentaModel().setCantidad(ammount);
//            objects.add(pv);
//        }
//        notifyDataSetChanged();
    }

    public void rebajar(InsumoAlmacenModel p) {

//        boolean existe = false;
//        ProductoVentaOrdenModel pv = null;
//        int i = 0;
//        for (; i< objects.size() && !existe ; i++){
//            pv = objects.get(i);
//            existe = pv.getProductoVentaModel().getPCod().equals(p.getPCod());
//        }
//        if(existe){
//            i--;
//            if( pv.getProductoVentaModel().getCantidad()>1){
//                pv.getProductoVentaModel().decreaseAmmount();
//                objects.set(i,pv);
//            }
//            else{
//                objects.remove(i);
//            }
//
//            notifyDataSetChanged();
//        }


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
