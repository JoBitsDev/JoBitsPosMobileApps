package com.utils.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.activities.R;
import com.services.models.ProductoVentaModel;
import com.services.models.SeccionModel;
import com.utils.EnvironmentVariables;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jorge on 9/7/17.
 */
public class MenuAdapter extends BaseExpandableListAdapter implements Filterable {

    private Context _context;
    private List<SeccionModel> secciones;
    private List<SeccionModel> displayedSecciones;



    public MenuAdapter(Context context, List<SeccionModel> secciones) {
        this._context = context;
        this.secciones = secciones;
        this.displayedSecciones = secciones;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.displayedSecciones.get(groupPosition).getProductos().get(childPosititon);

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String
                childNombre = ((ProductoVentaModel) getChild(groupPosition, childPosition)).getNombre(),
                childPrecio = String.valueOf(((ProductoVentaModel)getChild(groupPosition,childPosition)).getPrecioVenta()) + " "+ EnvironmentVariables.MONEDA_PRINCIPAL;


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_child, null);
        }

        TextView txtNombre = (TextView) convertView
                .findViewById(R.id.boxTitulo);
        TextView txtPrecio = (TextView) convertView
                .findViewById(R.id.boxSubtitulo);

        txtNombre.setText(childNombre);
        txtPrecio.setText(childPrecio);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.displayedSecciones.get(groupPosition).getProductos().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.displayedSecciones.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.displayedSecciones.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = ((SeccionModel) getGroup(groupPosition)).getNombreSeccion();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_parent, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.boxParent);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

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



    //
    //Implementing filterable interface
    //

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
            protected void publishResults(CharSequence constraint,FilterResults results) {

                displayedSecciones = (List<SeccionModel>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<SeccionModel> filteredArrList = new ArrayList<SeccionModel>();

                if (secciones == null) {
                    secciones = new ArrayList<SeccionModel>(displayedSecciones); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = secciones.size();
                    results.values = secciones;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < secciones.size(); i++) {
                        boolean contain = false;
                        SeccionModel aux = new SeccionModel(secciones.get(i).getNombreSeccion(),
                                secciones.get(i).getCartacodCarta());
                        for (int j = 0;j<secciones.get(i).getProductos().size();j++){
                        String data = secciones.get(i).getProductos().get(j).getNombre();
                        if (data.toLowerCase().contains(constraint.toString())) {
                            if(!contain){
                                aux.addProducto(secciones.get(i).getProductos().get(j));
                                filteredArrList.add(aux);
                                contain = true;
                            }
                            else{
                                aux.addProducto(secciones.get(i).getProductos().get(j));
                            }
                        }
                    }}
                    // set the Filtered result to return
                    results.count = filteredArrList.size();
                    results.values = filteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

}

