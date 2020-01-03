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
import com.services.models.ProductoVentaModel;
import com.services.models.ProductoVentaOrdenModel;
import com.services.models.ProductovOrdenPKModel;
import com.services.web_connections.OrdenWebConnectionService;

import java.util.List;


/**
 * Created by Jorge on 12/7/17.
 */
public class ProductoVentaOrdenAdapter extends ArrayAdapter<ProductoVentaOrdenModel> {
    public final int c = Color.GREEN;
    List<ProductoVentaOrdenModel> objects;
    Activity context;
    boolean read_only = false;
    private View.OnLongClickListener listener;

    public ProductoVentaOrdenAdapter(Activity context, int resource, List<ProductoVentaOrdenModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    public ProductoVentaOrdenAdapter(Activity context, int resource, List<ProductoVentaOrdenModel> objects, View.OnLongClickListener listener) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        this.listener = listener;
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
            holder.comensal = (ImageButton) item.findViewById(R.id.comensalButton);
            holder.add.setOnLongClickListener(listener);

            item.setTag(holder);
        } else {
            holder = (ViewHolder) item.getTag();
        }

        holder.precioVenta.setTextColor(c);
        holder.nombreProducto.setText(objects.get(position).getProductoVentaModel().getNombre());
        holder.precioVenta.setText(String.valueOf(
                objects.get(position).getProductoVentaModel().getPrecioVenta() *
                        objects.get(position).getCantidad()));
        holder.cantidad.setText(String.valueOf(objects.get(position).getCantidad()));
        holder.adjunto.setTag(position);
        holder.add.setTag(position);
        holder.remove.setTag(position);
        holder.comensal.setTag(position);

        if (read_only) {
            holder.add.setVisibility(View.INVISIBLE);
            holder.remove.setVisibility(View.INVISIBLE);
            holder.comensal.setVisibility(View.INVISIBLE);
            holder.adjunto.setVisibility(View.INVISIBLE);
        }

        return (item);
    }

    public boolean contains(ProductoVentaModel p) {
        return objects.contains(p);
    }

    public void increase(ProductoVentaModel p, OrdenWebConnectionService o) {
        boolean existe = false;
        ProductoVentaOrdenModel pv = null;
        int i = 0;
        for (; i < objects.size() && !existe; i++) {
            pv = objects.get(i);
            existe = pv.getProductoVentaModel().equals(p);

        }
        if (existe) {
            i--;
            pv.setCantidad(pv.getCantidad() + 1);
            objects.set(i, pv);
        } else {
            ProductovOrdenPKModel pk = new ProductovOrdenPKModel(p.getPCod(), o.getCodOrden());
            pv = new ProductoVentaOrdenModel(pk);
            pv.setProductoVentaModel(p);
            pv.setCantidad(1);
            pv.setEnviadosACocina((float) 0);
            objects.add(pv);
        }
        notifyDataSetChanged();
    }

    public void increase(ProductoVentaModel p, OrdenWebConnectionService o, float ammount) {
        boolean existe = false;
        ProductoVentaOrdenModel pv = null;
        int i = 0;
        for (; i < objects.size() && !existe; i++) {
            pv = objects.get(i);
            existe = pv.getProductoVentaModel().equals(p);
        }
        if (existe) {
            i--;
            pv.setCantidad(pv.getCantidad() + ammount);
            objects.set(i, pv);

        } else {
            ProductovOrdenPKModel pk = new ProductovOrdenPKModel(p.getPCod(), o.getCodOrden());
            pv = new ProductoVentaOrdenModel(pk);
            pv.setProductoVentaModel(p);
            pv.setCantidad(ammount);
            pv.setEnviadosACocina((float) 0);
            objects.add(pv);
        }
        notifyDataSetChanged();
    }

    public void decrease(ProductoVentaModel p) {

        boolean existe = false;
        ProductoVentaOrdenModel pv = null;
        int i = 0;
        for (; i < objects.size() && !existe; i++) {
            pv = objects.get(i);
            existe = pv.getProductoVentaModel().getPCod().equals(p.getPCod());
        }
        if (existe) {
            i--;
            if (pv.getCantidad() > 1) {
                pv.setCantidad(pv.getCantidad() - 1);
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
        this.objects.addAll(objects);
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
        ImageButton add, remove, adjunto, comensal;
    }


}

