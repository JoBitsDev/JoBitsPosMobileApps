package com.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.utils.exception.ExceptionHandler;

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

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void setAdapters() {
    }
}
