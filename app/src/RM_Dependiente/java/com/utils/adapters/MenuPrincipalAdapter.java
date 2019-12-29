package com.utils.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.activities.R;
import com.services.models.SeccionModel;
import java.util.List;

public class MenuPrincipalAdapter extends ArrayAdapter <SeccionModel> {

    public final int c = Color.GREEN;
    private Context context;
    private List<SeccionModel> secciones;

    public MenuPrincipalAdapter(Context context, int textViewResourceId, List<SeccionModel> secciones) {
        super(context, textViewResourceId, secciones);
        this.context = context;
        this.secciones = secciones;
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
        //String nombre = secciones.get(position).getNombreSeccion();
        String nombre = secciones.get(position).getNombreSeccion().replace(" ","\n");
        holder.nombreProducto.setText(nombre);
        holder.precioVenta.setVisibility(View.GONE);
        return (item);
    }

    static class ViewHolder {
        TextView nombreProducto;
    }

}
