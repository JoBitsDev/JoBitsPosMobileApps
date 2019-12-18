package com.utils.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.activities.R;
import com.services.models.MesaModel;
import com.utils.EnvironmentVariables;

import java.util.List;




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
        this.usuario = usuario;

    }



    public View getView(int position, View convertView, ViewGroup parent){

        View item = convertView;
        ViewHolder holder;
        if(item == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.list_row, null);

            holder = new ViewHolder();
            holder.codMesa = (TextView)item.findViewById(R.id.boxTitulo);
            holder.estadoMesa = (TextView)item.findViewById(R.id.boxSubtitulo);
            item.setTag(holder);







        }

        else {
            holder = (ViewHolder)item.getTag();
        }



        String est = objects.get(position).getEstado();
        int c;
        if(est.compareToIgnoreCase(EnvironmentVariables.ESTADO_MESA_VACIA) == 0){
            c = Color.GREEN;

        }
        else{

            if(est.equals(EnvironmentVariables.ESTADO_MESA_ESPERANDO_CONFIRMACION)){
                c = Color.BLUE;

            }
            else{

            boolean mine = est.split(" ")[1].equals(usuario);


            if(mine){
                c = Color.RED;
            }
            else{
                c = Color.YELLOW;
            }
            }

        }
        holder.estadoMesa.setTextColor(c);
        holder.codMesa.setTextColor(c);
        holder.codMesa.setText(objects.get(position).getCodMesa());
        holder.estadoMesa.setText(objects.get(position).getEstado());



        return(item);


}


    static class ViewHolder {
        TextView codMesa;
        TextView estadoMesa; }

    }

