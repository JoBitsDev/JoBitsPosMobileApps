package com.main.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.main.ProductoVenta;
import com.main.ProductovOrden;
import com.main.ProductovOrdenPK;
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


    public adapterProductoVOrden(Activity context, int resource, List<ProductovOrden> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
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
            holder.cantidad= (TextView)item.findViewById(R.id.textCantidad);
            holder.add = (ImageButton) item.findViewById(R.id.addButton);
            holder.nota = (TextView) item.findViewById(R.id.textViewNota);

            item.setTag(holder);
        }
        else {
            holder = (ViewHolder)item.getTag();
        }

        holder.nombreProducto.setText(objects.get(position).getProductoVenta().getNombre());
        holder.cantidad.setText(String.valueOf(objects.get(position).getCantidad()));
        holder.nota.setText(objects.get(position).getNota() != null
                ? objects.get(position).getNota() :"Sin Especifícaciones");
        objects.get(position).getOrden().getCodOrden();
        holder.add.setTag(position);

        return(item);


    }

    public boolean contains(ProductovOrden p) {
        return objects.contains(p);
    }

    public void notify(ProductovOrden p) {
        //objects.remove(p);

    }


    public void onAddClick(View View){


    }

    static class ViewHolder {
        TextView nombreProducto,nota,
                 cantidad;
        ImageButton add;
    }



}

