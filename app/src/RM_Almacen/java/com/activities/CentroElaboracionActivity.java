package com.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;

import com.utils.adapters.CentroElaboracionAdapter;
import com.utils.adapters.SelecElaboracionAdapter;
import com.utils.exception.ExceptionHandler;

/**
 * Capa: Activities
 * Clase que controla el XML de la pantalla principal del Punto de Elaboracion.
 *
 * @extends BaseActivity ya que es una activity propia de la aplicacion.
 */
public class CentroElaboracionActivity extends BaseActivity {

    private ListView listViewIngredientes;
    private ListView listViewReceta;
    private ListView listViewSelecIngrediente;

    private Button buttonAgregarIngrediente;
    private Button buttonAgregarReceta;
    private Button buttonConfirmar;
    private Button buttonTerminar;

    private TabHost host;
    private CentroElaboracionAdapter centroElaboracionAdapter;
    private SelecElaboracionAdapter selecElaboracionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_centro_elaboracion);

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
            listViewIngredientes = (ListView)findViewById(R.id.listViewIngrediente);
            listViewReceta = (ListView) findViewById(R.id.listViewReceta);
            listViewSelecIngrediente = (ListView)findViewById(R.id.listViewSelecIng);

            buttonAgregarIngrediente = (Button)findViewById(R.id.buttonAgregarIngrediente);
            buttonAgregarReceta = (Button)findViewById(R.id.buttonAgregarReceta);
            buttonConfirmar = (Button)findViewById(R.id.buttonConfirmar);
            buttonTerminar = (Button)findViewById(R.id.buttonTerminar);

            initTab();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    void addListeners() {

    }

    @Override
    protected void setAdapters() {

    }

    private void initTab() {
        try {
            host = (TabHost) findViewById(R.id.tabHost);
            if (host != null) {//TODO: por que este if??
                host.setup();

                TabHost.TabSpec spec = host.newTabSpec("Centro");

                //Tab 1
                spec.setContent(R.id.tab1);
                spec.setIndicator("Centro");
                host.addTab(spec);

                //Tab 2
                spec = host.newTabSpec("List");
                spec.setContent(R.id.tab2);
                spec.setIndicator("List");
                host.addTab(spec);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }
}
