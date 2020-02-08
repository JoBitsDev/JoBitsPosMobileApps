package com.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.controllers.PantallaPrincipalController;
import com.services.models.TransaccionModel;
import com.utils.adapters.AlmacenInsumoAdapter;
import com.utils.adapters.OperacionesAdapter;
import com.utils.exception.ExceptionHandler;
import com.utils.loading.LoadingHandler;
import com.utils.loading.LoadingProcess;

import java.util.List;

/**
 * Capa: Activities
 * Clase que controla el XML de la pantalla de lista de operaciones del Almacen.
 *
 * @extends BaseActivity ya que es una activity propia de la aplicacion.
 */
public class VerOperacionesActivity extends BaseActivity {

    private EditText editTextBuscar;
    private ImageButton buttonActualizar;
    private ListView listViewOperaciones;
    private PantallaPrincipalController controller;
    private OperacionesAdapter operacionesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.ver_operaciones_activity);

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
            editTextBuscar = (EditText) findViewById(R.id.editTextBuscar);
            buttonActualizar = (ImageButton) findViewById(R.id.imageButtonActualizar);
            listViewOperaciones = (ListView) findViewById(R.id.listViewOperaciones);
            controller = new PantallaPrincipalController();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    void addListeners() {
        editTextBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((OperacionesAdapter) listViewOperaciones.getAdapter()).getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        buttonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });
    }

    @Override
    protected void setAdapters() {
        try {

            new LoadingHandler<Void>(act, new LoadingProcess<Void>() {
                @Override
                public Void process() throws Exception {
                    operacionesAdapter = new OperacionesAdapter(act, R.layout.lista_operaciones, controller.getOperacionesRealizadas());
                    return null;
                }

                @Override
                public void post(Void answer) {
                    listViewOperaciones.setAdapter(operacionesAdapter);
                }
            });
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    private void actualizar() {
        new LoadingHandler<OperacionesAdapter>(act, new LoadingProcess<OperacionesAdapter>() {
            @Override
            public OperacionesAdapter process() throws Exception {
                return controller.getOperacionesAdapter(act, R.id.listViewOperaciones);
            }

            @Override
            public void post(OperacionesAdapter answer) {
                listViewOperaciones.setAdapter(answer);
            }
        });
    }
}
