package com.activities;

import android.os.Bundle;
import android.widget.ListView;

import com.utils.adapter.DetallesVentasAdapter;
import com.utils.exception.ExceptionHandler;
import com.utils.loading.LoadingHandler;
import com.utils.loading.LoadingProcess;

public class DetallesVentasActivity extends BaseActivity {

    private ListView listViewDetallesVentas;
    private DetallesVentasAdapter detallesVentasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_detalles_ventas);

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
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    void addListeners() {

    }

    @Override
    protected void setAdapters() {
        try {

            new LoadingHandler<Void>(act, new LoadingProcess<Void>() {
                @Override
                public Void process() throws Exception {
                 //   operacionesAdapter = new OperacionesAdapter(act, R.layout.lista_operaciones, controller.getOperacionesRealizadas());
                    return null;
                }

                @Override
                public void post(Void answer) {
              //      listViewOperaciones.setAdapter(operacionesAdapter);
                }
            });
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }
}
