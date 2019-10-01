package com.main.adapters;

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

import com.main.InsumoAlmacen;
import com.main.res;
import com.main.webServerCon.almacenConn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import firstdream.restaurantmanageralmacen.R;

/**
 * Created by Jorge on 12/7/17.
 */
public class adapterAlmacenInsumo extends ArrayAdapter<InsumoAlmacen> implements Filterable {
    public final int c = Color.GREEN;
    List<InsumoAlmacen> objects, displayedObjects;
    Activity context;
    View.OnLongClickListener listener;


    public adapterAlmacenInsumo(Activity context, int resource, List<InsumoAlmacen> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        displayedObjects = objects;
        Collections.sort(objects);
    }

    public adapterAlmacenInsumo(Activity context, int resource, List<InsumoAlmacen> objects, View.OnLongClickListener listener) {
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
        InsumoAlmacen i = getPosition(position);

        holder.um.setText(i.getInsumo().getUm());
        holder.nombreInsumo.setText(i.getInsumo().getNombre());
        holder.cantidad.setText(String.valueOf(i.getCantidad()));
        holder.precioMedio.setText("Costo Medio: "
                + res.setDosLugaresDecimales(i.getInsumo().getCostoPorUnidad()));

        //escribiendo el stock
        float diferencia =  i.getCantidad()-i.getInsumo().getStockEstimation();
        String text = "-";
        int color = Color.BLACK;
        if (diferencia >= 0) {
            text = "+" + diferencia ;
            color = diferencia > i.getInsumo().getStockEstimation() * 0.5 ? Color.GREEN : Color.YELLOW;
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

    public InsumoAlmacen getPosition(int position) {
        return displayedObjects.get(position);
    }

    @Override
    public InsumoAlmacen getItem(int position) {
       return displayedObjects.get(position);
    }

    public boolean contains(InsumoAlmacen p) {
        return displayedObjects.contains(p);
    }

    public void darEntrada(InsumoAlmacen p, almacenConn o) {
        boolean existe = false;
//        ProductovOrden pv = null;
//        int i = 0;
//        for (; i< objects.size() && !existe ; i++){
//            pv = objects.get(i);
//           existe = pv.getProductoVenta().equals(p);
//
//        }
//        if(existe){
//            i--;
//            pv.getProductoVenta().increaceAmount();
//            objects.set(i,pv);
//
//        }
//        else{
//            ProductovOrdenPK pk = new ProductovOrdenPK(p.getPCod(),o.getCodOrden());
//            pv = new ProductovOrden(pk);
//            pv.setProductoVenta(p);
//            pv.getProductoVenta().setCantidad(1);
//            objects.add(pv);
//        }
//        notifyDataSetChanged();
    }

    public void darEntrada(InsumoAlmacen p, float ammount, almacenConn o) {
        boolean existe = false;
//        ProductovOrden pv = null;
//        int i = 0;
//        for (; i< objects.size() && !existe ; i++){
//            pv = objects.get(i);
//            existe = pv.getProductoVenta().equals(p);
//        }
//        if(existe){
//            i--;
//            pv.getProductoVenta().setCantidad(pv.getProductoVenta().getCantidad()+ammount);
//            objects.set(i,pv);
//
//        }
//        else{
//            ProductovOrdenPK pk = new ProductovOrdenPK(p.getPCod(),o.getCodOrden());
//            pv = new ProductovOrden(pk);
//            pv.setProductoVenta(p);
//            pv.getProductoVenta().setCantidad(ammount);
//            objects.add(pv);
//        }
//        notifyDataSetChanged();
    }

    public void rebajar(InsumoAlmacen p) {

//        boolean existe = false;
//        ProductovOrden pv = null;
//        int i = 0;
//        for (; i< objects.size() && !existe ; i++){
//            pv = objects.get(i);
//            existe = pv.getProductoVenta().getPCod().equals(p.getPCod());
//        }
//        if(existe){
//            i--;
//            if( pv.getProductoVenta().getCantidad()>1){
//                pv.getProductoVenta().decreaseAmmount();
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

                displayedObjects = (List<InsumoAlmacen>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<InsumoAlmacen> filteredArrList = new ArrayList<InsumoAlmacen>();

                if (objects == null) {
                    objects = new ArrayList<InsumoAlmacen>(displayedObjects); // saves the original data in mOriginalValues
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
                    for (InsumoAlmacen i : objects) {
                        String data = i.getInsumo().getNombre();
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

