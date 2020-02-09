package com.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.services.models.DetallesVentasModel;
import com.utils.adapter.DetallesVentasAdapter;
import com.utils.adapter.FilterAdapter;
import com.utils.exception.ExceptionHandler;
import com.utils.loading.LoadingHandler;
import com.utils.loading.LoadingProcess;

import java.util.List;

public class DetallesVentasActivity extends BaseActivity {

    private ListView listViewDetallesVentas;

    private Spinner spinnerOrdenBy;

    private DetallesVentasAdapter detallesVentasAdapter;
    private FilterAdapter filterAdapter;

    private List<DetallesVentasModel> lista;

    private TextView textViewBy;

    private String by;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_detalles_ventas);

            final Bundle bundleExtra = getIntent().getExtras();
            lista = (List<DetallesVentasModel>) bundleExtra.getSerializable("lista");
            by = bundleExtra.getString("by");

            initVarialbes();//inicializa las  variables
            addListeners();//agrega los listener
            setAdapters();//agrega los adapters
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    void initVarialbes() {
        try {
            listViewDetallesVentas = (ListView) findViewById(R.id.listViewVentas);
            spinnerOrdenBy = (Spinner) findViewById(R.id.spinnerOrdenar);
            textViewBy = (TextView) findViewById(R.id.textViewBy);
            textViewBy.setText(by);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    void addListeners() {
        spinnerOrdenBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSpinnerOrderByItemSelected(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void onSpinnerOrderByItemSelected(View view) {
        listViewDetallesVentas.setAdapter(detallesVentasAdapter.orderBy((String) spinnerOrdenBy.getSelectedItem()));
    }

    @Override
    protected void setAdapters() {
        detallesVentasAdapter = new DetallesVentasAdapter(act, R.id.listViewVentas, lista);
        listViewDetallesVentas.setAdapter(detallesVentasAdapter);

        filterAdapter = new FilterAdapter(act, R.layout.simple_spinner_dropdow_item);
        spinnerOrdenBy.setAdapter(filterAdapter.createAdapter());
    }
}
