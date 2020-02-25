package com.utils.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.activities.R;
import com.services.models.ProductoVentaOrdenModel;

import java.util.List;




/**
 * Created by Jorge on 12/7/17.
 */
public class ProductoVentaOrdenAdapter extends ArrayAdapter<ProductoVentaOrdenModel> {
    public final int c = Color.GREEN;
    List<ProductoVentaOrdenModel> objects;
    Activity context;


    public ProductoVentaOrdenAdapter(Activity context, int resource, List<ProductoVentaOrdenModel> objects) {
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

    public boolean contains(ProductoVentaOrdenModel p) {
        return objects.contains(p);
    }

    public void notify(ProductoVentaOrdenModel p) {
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

