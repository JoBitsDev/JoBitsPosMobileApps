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
import com.services.models.orden.ProductoVentaModel;
import com.services.models.orden.ProductoVentaOrdenModel;
import com.services.web_connections.OrdenWCS;
import com.utils.EnvironmentVariables;

import java.util.List;


/**
 * Created by Jorge on 12/7/17.
 */
public class ProductoVentaOrdenAdapter extends ArrayAdapter<ProductoVentaOrdenModel> {
    public final int c = Color.GREEN;
    boolean read_only = false;
    private List<ProductoVentaOrdenModel> objects;
    private Activity context;
    private View.OnLongClickListener addListener;
    private View.OnLongClickListener removeListener;

    public ProductoVentaOrdenAdapter(Activity context, int resource, List<ProductoVentaOrdenModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    public View.OnLongClickListener getAddListener() {
        return addListener;
    }

    public void setAddListener(View.OnLongClickListener addListener) {
        this.addListener = addListener;
    }

    public View.OnLongClickListener getRemoveListener() {
        return removeListener;
    }

    public void setRemoveListener(View.OnLongClickListener removeListener) {
        this.removeListener = removeListener;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View item = convertView;
        ViewHolder holder;
        if (item == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.list_orden, null);

            holder = new ViewHolder();
            holder.nombreProducto = (TextView) item.findViewById(R.id.textNombre);
            holder.precioVenta = (TextView) item.findViewById(R.id.textPrecio);
            holder.cantidad = (TextView) item.findViewById(R.id.textCantidad);
            holder.adjunto = (ImageButton) item.findViewById(R.id.adjuntoButton);
            holder.add = (ImageButton) item.findViewById(R.id.addButton);
            holder.remove = (ImageButton) item.findViewById(R.id.removeButton);
            holder.add.setOnLongClickListener(addListener);
            holder.remove.setOnLongClickListener(removeListener);

            item.setTag(holder);
        } else {
            holder = (ViewHolder) item.getTag();
        }

        holder.precioVenta.setTextColor(c);
        holder.nombreProducto.setText(objects.get(position).getNombreProductoVendido());
        holder.precioVenta.setText(
                objects.get(position).getPrecioVendido() *
                        objects.get(position).getCantidad() + EnvironmentVariables.MONEDA_PRINCIPAL);
        holder.cantidad.setText("x " + String.valueOf(objects.get(position).getCantidad()));
        holder.adjunto.setTag(position);
        holder.add.setTag(position);
        holder.remove.setTag(position);
        if (read_only) {
            holder.add.setVisibility(View.INVISIBLE);
            holder.remove.setVisibility(View.INVISIBLE);
            holder.adjunto.setVisibility(View.INVISIBLE);
        }

        return (item);
    }

    public boolean contains(ProductoVentaModel p) {
        return objects.contains(p);
    }


    public void increase(ProductoVentaModel p, OrdenWCS o, float ammount) {
        boolean existe = false;
        ProductoVentaOrdenModel pv = null;
        int i = 0;
        for (; i < objects.size() && !existe; i++) {
            pv = objects.get(i);
            existe = pv.getNombreProductoVendido().equals(p.getNombre());
        }
        if (existe) {
            i--;
            pv.setCantidad(pv.getCantidad() + ammount);
            objects.set(i, pv);

        } else {
            pv = new ProductoVentaOrdenModel();
            pv.setNombreProductoVendido(p.getNombre());
            pv.setPrecioVendido(p.getPrecioVenta());
            pv.setCantidad(ammount);
            pv.setEnviadosacocina((float) 0);
            objects.add(pv);
        }
        notifyDataSetChanged();
    }

    public void decrease(ProductoVentaOrdenModel p, float cant) {

        boolean existe = false;
        ProductoVentaOrdenModel pv = null;
        int i = 0;
        for (; i < objects.size() && !existe; i++) {
            pv = objects.get(i);
            existe = pv.getId() == p.getId();
        }
        if (existe) {
            i--;
            if (pv.getCantidad() > cant) {
                pv.setCantidad(pv.getCantidad() - cant);
                objects.set(i, pv);
            } else {
                objects.remove(i);
            }

            notifyDataSetChanged();
        }


    }

    public List<ProductoVentaOrdenModel> getObjects() {
        return objects;
    }

    public void setObjects(List<ProductoVentaOrdenModel> objects) {
        this.objects.clear();
        if (objects != null) {
            this.objects.addAll(objects);
        }
        notifyDataSetChanged();
    }

    /**
     * {@inheritDoc}
     *
     * @param position
     */
    @Override
    public ProductoVentaOrdenModel getItem(int position) {
        return objects.get(position);
    }

    static class ViewHolder {
        TextView nombreProducto,
                precioVenta,
                cantidad;
        ImageButton add, remove, adjunto;
    }


}

