package com.utils.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.*;
import android.widget.*;

import com.activities.R;
import com.services.models.orden.MesaModel;
import com.utils.EnvironmentVariables;

import java.util.*;


/**
 * Created by Jorge on 2/7/17.
 */
public class MesaAdapter extends ArrayAdapter<MesaModel> {

    Activity context;
    List<MesaModel> objects;

    private static String usuario;

    public MesaAdapter(Activity context, int textViewResourceId, List<MesaModel> objects, String usuario) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
        Collections.sort(objects);
        this.usuario = usuario;
    }

    public MesaModel getMesa(int pos) {
        return objects.get(pos);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ViewHolder holder;
        if (item == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.list_row, null);

            holder = new ViewHolder();
            holder.codMesa = (TextView) item.findViewById(R.id.boxTitulo);
            holder.estadoMesa = (TextView) item.findViewById(R.id.boxSubtitulo);
            item.setTag(holder);
        } else {
            holder = (ViewHolder) item.getTag();
        }

        String est = objects.get(position).getEstado();
        int c;
        if (est.compareToIgnoreCase(EnvironmentVariables.ESTADO_MESA_VACIA) == 0) {
            c = Color.parseColor("#ebf3e2");
        } else {
            if (est.equals(EnvironmentVariables.ESTADO_MESA_ESPERANDO_CONFIRMACION)) {
                c = Color.BLUE;
            } else {
                boolean mine = est.split(" ")[1].equals(usuario);
                if (mine) {
                    c = Color.RED;
                } else {
                    c = Color.YELLOW;
                }
            }
        }
        holder.estadoMesa.setTextColor(c);
        holder.codMesa.setTextColor(c);
        holder.codMesa.setText(objects.get(position).getCodMesa());
        holder.estadoMesa.setText(objects.get(position).getEstado());

        return (item);
    }

    public MesaAdapter orderBy(String orden) {
        if (orden.matches(String.valueOf(R.string.orden))) {
            orderByOrden();
        } else if (orden.matches(String.valueOf(R.string.mesa))) {
            orderByMesas();
        }
        return this;
    }

    private void orderByMesas() {
        Collections.sort(objects);
    }

    private void orderByOrden() {
        Collections.sort(objects, new Comparator<MesaModel>() {
            @Override
            public int compare(MesaModel first, MesaModel second) {
                //los primero if son para verificar si las mesas tienen orden
                if (first.getEstado().compareToIgnoreCase(EnvironmentVariables.ESTADO_MESA_VACIA) == 0) {
                    return 1;//si esta no tiene orden va a ser > que la otra, PAL FINAL
                } else if (second.getEstado().compareToIgnoreCase(EnvironmentVariables.ESTADO_MESA_VACIA) == 0) {
                    return -1;//si esta no tiene orden va a ser < que la otra,
                } else if (first.getEstado().equals(EnvironmentVariables.ESTADO_MESA_ESPERANDO_CONFIRMACION)) {
                    return 1;//si esta no tiene orden va a ser < que la otra
                } else if (second.getEstado().equals(EnvironmentVariables.ESTADO_MESA_ESPERANDO_CONFIRMACION)) {
                    return -1;//si esta no tiene orden va a ser > que la otra
                } else {//si las dos tienen orden ver las ordenes
                    long orden1 = Long.parseLong(first.getEstado().split(" ")[0].split("-")[1].trim());
                    long orden2 = Long.parseLong(second.getEstado().split(" ")[0].split("-")[1].trim());
                    if (orden1 > orden2) {
                        return 1;
                    } else if (orden2 > orden1) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }
        });
    }

    static class ViewHolder {
        TextView codMesa;
        TextView estadoMesa;
    }

}

