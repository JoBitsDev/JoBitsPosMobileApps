package com.utils.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.activities.R;
import com.services.models.*;
import com.services.models.orden.ProductoVentaOrdenModel;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Jorge on 9/7/17.
 */
public class MenuAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<ProductoVentaOrdenModel> displayedSecciones;
    private List<Contenedor> contenedores;
    private int[] colors = {Color.RED,Color.GRAY,Color.MAGENTA,Color.CYAN,Color.DKGRAY,Color.LTGRAY};


    public MenuAdapter(Context context, List<ProductoVentaOrdenModel> pedidos) {
        this._context = context;
        this.displayedSecciones = pedidos;

       contenedores = createContenedores();
    }

    private List<Contenedor> createContenedores() {
        ArrayList<Contenedor> ret = new ArrayList<Contenedor>();
        for(ProductoVentaOrdenModel x: displayedSecciones){
            Contenedor newContenedor = new Contenedor(x,x.getCodMesa()
                    + " " + x.getCodOrden());
            int index = -1;
            if((index = ret.indexOf(newContenedor)) != -1){
            ret.get(index).addContenedor(newContenedor);
            }else{
                ret.add(newContenedor);
            }

        }
        return ret;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.contenedores.get(groupPosition).getPedido().get(childPosititon);

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String
                childNombre = ((ProductoVentaOrdenModel) getChild(groupPosition, childPosition)).getProductoVenta().getNombre(),
                cantidad = String.valueOf(((ProductoVentaOrdenModel)getChild(groupPosition,childPosition)).getCantidad()),
                nota = ((ProductoVentaOrdenModel) getChild(groupPosition, childPosition)).getNota();


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_orden, null);
        }

        TextView txtNombre = (TextView) convertView
                .findViewById(R.id.textNombre);
        TextView txtPrecio = (TextView) convertView
                .findViewById(R.id.textCantidad);
        TextView txtNota = (TextView) convertView
                .findViewById(R.id.textViewNota);

        txtNombre.setText(childNombre);
        txtPrecio.setText(cantidad);
        txtNota.setText(nota != null ? nota : "Sin Especificaciones");
        if (nota == null){
        txtNota.setTextColor(Color.BLACK);}
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.contenedores.get(groupPosition).getPedido().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.contenedores.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.contenedores.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = ((Contenedor) getGroup(groupPosition)).getCodOrden();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_parent, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.boxParent);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        lblListHeader.setTextColor(colors[groupPosition%colors.length]);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    private class Contenedor {
        private List<ProductoVentaOrdenModel> pedido;
        private String codOrden;

        public Contenedor(ProductoVentaOrdenModel pedido, String codOrden) {
            ArrayList<ProductoVentaOrdenModel> n = new ArrayList<ProductoVentaOrdenModel>();
            n.add(pedido);
            this.pedido = n;
            this.codOrden = codOrden;
        }

        public List<ProductoVentaOrdenModel> getPedido() {
            return pedido;
        }

        public void setPedido(List<ProductoVentaOrdenModel> pedido) {
            this.pedido = pedido;
        }

        public String getCodOrden() {
            return codOrden;
        }

        public void setCodOrden(String codOrden) {
            this.codOrden = codOrden;
        }

        public void addContenedor(Contenedor c){
             pedido.addAll(c.getPedido());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Contenedor that = (Contenedor) o;
            return getCodOrden().equals(that.getCodOrden());
        }

    }

}

