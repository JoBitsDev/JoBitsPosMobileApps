package com.main.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.main.objetos.ProductoVenta;
import com.main.objetos.ProductovOrden;
import com.main.objetos.ProductovOrdenPK;
import com.main.webServerCon.ordenConn;

import java.util.List;

import firstdream.rm.R;

/**
 * Created by Jorge on 12/7/17.
 */
public class adapterProductoVOrden extends ArrayAdapter<ProductovOrden> {
    public final int c = Color.GREEN;
    List<ProductovOrden> objects;
    Activity context;
    View.OnLongClickListener listener;
    boolean read_only = false;


    public adapterProductoVOrden(Activity context, int resource, List<ProductovOrden> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    public adapterProductoVOrden(Activity context, int resource, List<ProductovOrden> objects,boolean readOnly) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        this.read_only = readOnly;
    }

    public adapterProductoVOrden(Activity context, int resource, List<ProductovOrden> objects, View.OnLongClickListener listener) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        this.listener = listener;
    }

    public View getView(final int position, View convertView, ViewGroup parent){

        View item = convertView;
        ViewHolder holder;
        if(item == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.list_orden, null);

            holder = new ViewHolder();
            holder.nombreProducto = (TextView)item.findViewById(R.id.textNombre);
            holder.precioVenta = (TextView)item.findViewById(R.id.textPrecio);
            holder.cantidad= (TextView)item.findViewById(R.id.textCantidad);
            holder.adjunto = (ImageButton) item.findViewById(R.id.adjuntoButton);
            holder.add = (ImageButton) item.findViewById(R.id.addButton);
            holder.remove = (ImageButton) item.findViewById(R.id.removeButton);
            holder.comensal = (ImageButton) item.findViewById(R.id.comensalButton);
            setLongClick(holder.add);
            item.setTag(holder);
        }
        else {
            holder = (ViewHolder)item.getTag();
        }

        holder.precioVenta.setTextColor(c);
        holder.nombreProducto.setText(objects.get(position).getProductoVenta().getNombre());
        holder.precioVenta.setText(String.valueOf(
                objects.get(position).getProductoVenta().getPrecioVenta()*
                        objects.get(position).getCantidad()));
        holder.cantidad.setText(String.valueOf(objects.get(position).getCantidad()));
        holder.adjunto.setTag(position);
        holder.add.setTag(position);
        holder.remove.setTag(position);
        holder.comensal.setTag(position);

        if(read_only){
            holder.add.setVisibility(View.INVISIBLE);
            holder.remove.setVisibility(View.INVISIBLE);
            holder.comensal.setVisibility(View.INVISIBLE);
            holder.adjunto.setVisibility(View.INVISIBLE);
        }



        return(item);


    }

    public void setLongClick(View view){
        view.setOnLongClickListener(listener);
    }


    public boolean contains(ProductoVenta p) {
        return objects.contains(p);
    }

    public void increase(ProductoVenta p,ordenConn o) {
        boolean existe = false;
        ProductovOrden pv = null;
        int i = 0;
        for (; i< objects.size() && !existe ; i++){
            pv = objects.get(i);
           existe = pv.getProductoVenta().equals(p);

        }
        if(existe){
            i--;
            pv.setCantidad(pv.getCantidad() + 1);
            objects.set(i,pv);

        }
        else{
            ProductovOrdenPK pk = new ProductovOrdenPK(p.getPCod(),o.getCodOrden());
            pv = new ProductovOrden(pk);
            pv.setProductoVenta(p);
            pv.setCantidad(1);
            pv.setEnviadosACocina((float)0);
            objects.add(pv);
        }
        notifyDataSetChanged();
    }

    public void increase(ProductoVenta p, float ammount,ordenConn o) {
        boolean existe = false;
        ProductovOrden pv = null;
        int i = 0;
        for (; i< objects.size() && !existe ; i++){
            pv = objects.get(i);
            existe = pv.getProductoVenta().equals(p);
        }
        if(existe){
            i--;
            pv.setCantidad(pv.getCantidad()+ammount);
            objects.set(i,pv);

        }
        else{
            ProductovOrdenPK pk = new ProductovOrdenPK(p.getPCod(),o.getCodOrden());
            pv = new ProductovOrden(pk);
            pv.setProductoVenta(p);
            pv.setCantidad(ammount);
            pv.setEnviadosACocina((float)0);
            objects.add(pv);
        }
        notifyDataSetChanged();
    }

    public void decrease(ProductoVenta p) {

        boolean existe = false;
        ProductovOrden pv = null;
        int i = 0;
        for (; i< objects.size() && !existe ; i++){
            pv = objects.get(i);
            existe = pv.getProductoVenta().getPCod().equals(p.getPCod());
        }
        if(existe){
            i--;
            if( pv.getCantidad()>1){
                pv.setCantidad(pv.getCantidad()-1);
                objects.set(i,pv);
            }
            else{
                objects.remove(i);
            }

            notifyDataSetChanged();
        }


    }

    public List<ProductovOrden> getObjects() {
        return objects;
    }

    public void setObjects(List<ProductovOrden> objects) {
        this.objects.clear();
        this.objects.addAll(objects);
        notifyDataSetChanged();
    }

    /**
     * {@inheritDoc}
     *
     * @param position
     */
    @Override
    public ProductovOrden getItem(int position) {
        return objects.get(position);
    }

    static class ViewHolder {
        TextView nombreProducto,
                 precioVenta,
                 cantidad;
        ImageButton add,remove,adjunto,comensal;
    }



}

