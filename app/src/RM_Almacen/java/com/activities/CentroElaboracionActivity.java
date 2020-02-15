package com.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.controllers.CentroElaboracionController;
import com.services.models.InsumoAlmacenModel;
import com.utils.adapters.CentroElaboracionAdapter;
import com.utils.adapters.SelecElaboracionAdapter;
import com.utils.exception.ExceptionHandler;
import com.utils.loading.LoadingHandler;
import com.utils.loading.LoadingProcess;

import java.util.ArrayList;
import java.util.List;

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

    private CentroElaboracionController controller;
    private List<InsumoAlmacenModel> listaInsumosSelec;
    private List<InsumoAlmacenModel> listaInsumosReceta;
    private boolean isReceta;

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
            listViewIngredientes = (ListView) findViewById(R.id.listViewIngrediente);
            listViewReceta = (ListView) findViewById(R.id.listViewReceta);
            listViewSelecIngrediente = (ListView) findViewById(R.id.listViewSelecIng);

            buttonAgregarIngrediente = (Button) findViewById(R.id.buttonAgregarIngrediente);
            buttonAgregarReceta = (Button) findViewById(R.id.buttonAgregarReceta);
            buttonConfirmar = (Button) findViewById(R.id.buttonConfirmar);
            buttonTerminar = (Button) findViewById(R.id.buttonTerminar);

            controller = new CentroElaboracionController();
            listaInsumosSelec = new ArrayList<InsumoAlmacenModel>();
            listaInsumosReceta = new ArrayList<InsumoAlmacenModel>();
            isReceta = false;

            initTab();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    void addListeners() {
        buttonAgregarIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProductosDisponibles();
                host.setCurrentTab(1);
                isReceta = false;
            }
        });
        buttonAgregarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCombinacionesCon(listaInsumosSelec);
                host.setCurrentTab(1);
                isReceta = true;
            }
        });
        listViewSelecIngrediente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addProductosCant(1, position);
            }
        });
        listViewSelecIngrediente.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return onListViewElabAddLongClick(view, position);
            }
        });
        buttonTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReceta == false) {
                    setListProductSelec();
                } else {
                    setListRecetaSelec();
                }
                host.setCurrentTab(0);
            }
        });
        buttonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private boolean onListViewElabAddLongClick(final View v, final int position) {
        try {
            final EditText input = new EditText(v.getContext());
            input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            new AlertDialog.Builder(v.getContext()).
                    setView(input).
                    setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton(R.string.agregar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    addProductosCant(Integer.parseInt(input.getText().toString()), position);
                }
            }).create().show();
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return false;
        }
    }

    private void addProductosCant(int cant, int position) {
        InsumoAlmacenModel insumo = (InsumoAlmacenModel) listViewSelecIngrediente.getItemAtPosition(position);
        if (isReceta == false) {
            if (listaInsumosSelec.contains(insumo)) {
                for (int i = 0; i < listaInsumosSelec.size(); i++) {
                    if (listaInsumosSelec.get(i).equals(insumo)) {
                        listaInsumosSelec.get(i).setCantidad(listaInsumosSelec.get(i).getCantidad() + cant);
                    }
                }
            } else {
                insumo.setCantidad(insumo.getCantidad() + cant);
                listaInsumosSelec.add(insumo);
            }
        } else if (isReceta == true) {
            if (listaInsumosReceta.contains(insumo)) {
                for (int i = 0; i < listaInsumosReceta.size(); i++) {
                    if (listaInsumosReceta.get(i).equals(insumo)) {
                        listaInsumosReceta.get(i).setCantidad(listaInsumosReceta.get(i).getCantidad() + cant);
                    }
                }
            } else {
                insumo.setCantidad(insumo.getCantidad() + cant);
                listaInsumosReceta.add(insumo);
            }
        }
        Toast.makeText(getApplicationContext(), "Producto agregado.", Toast.LENGTH_SHORT).show();
    }

    private void getProductosDisponibles() {
        try {
            new LoadingHandler<Void>(act, new LoadingProcess<Void>() {
                @Override
                public Void process() throws Exception {
                    selecElaboracionAdapter = new SelecElaboracionAdapter(act, R.layout.list_selec_elab, controller.getProductosDisponibles());
                    return null;
                }

                @Override
                public void post(Void answer) {
                    listViewSelecIngrediente.setAdapter(selecElaboracionAdapter);
                }
            });
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    private void getCombinacionesCon(final List<InsumoAlmacenModel> lista) {
        try {
            new LoadingHandler<Void>(act, new LoadingProcess<Void>() {
                @Override
                public Void process() throws Exception {
                    selecElaboracionAdapter = new SelecElaboracionAdapter(act, R.layout.list_selec_elab, controller.getCombinacionesCon(lista));
                    return null;
                }

                @Override
                public void post(Void answer) {
                    listViewSelecIngrediente.setAdapter(selecElaboracionAdapter);
                }
            });
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    private void setListProductSelec() {
        try {
            new LoadingHandler<Void>(act, new LoadingProcess<Void>() {
                @Override
                public Void process() throws Exception {
                    centroElaboracionAdapter = new CentroElaboracionAdapter(act, R.layout.list_elaboracion, listaInsumosSelec);
                    return null;
                }

                @Override
                public void post(Void answer) {
                    listViewIngredientes.setAdapter(centroElaboracionAdapter);
                }
            });
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    private void setListRecetaSelec() {
        try {
            new LoadingHandler<Void>(act, new LoadingProcess<Void>() {
                @Override
                public Void process() throws Exception {
                    centroElaboracionAdapter = new CentroElaboracionAdapter(act, R.layout.list_elaboracion_receta, listaInsumosReceta);
                    return null;
                }

                @Override
                public void post(Void answer) {
                    listViewReceta.setAdapter(centroElaboracionAdapter);
                }
            });
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
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

    public void onAddProductoClick(View v) {
        try {
            InsumoAlmacenModel insumoAlmacenModel = ((InsumoAlmacenModel) listViewIngredientes.getAdapter().getItem((Integer) v.getTag()));
            for (int i = 0; i < listaInsumosSelec.size(); i++) {
                if (listaInsumosSelec.get(i).equals(insumoAlmacenModel)) {
                    listaInsumosSelec.get(i).setCantidad(listaInsumosSelec.get(i).getCantidad() + 1);
                }
            }
            setListProductSelec();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    public void onRemoveProductoClick(View v) {
        try {
            InsumoAlmacenModel insumoAlmacenModel = ((InsumoAlmacenModel) listViewIngredientes.getAdapter().getItem((Integer) v.getTag()));
            for (int i = 0; i < listaInsumosSelec.size(); i++) {
                if (listaInsumosSelec.get(i).equals(insumoAlmacenModel)) {
                    if (listaInsumosSelec.get(i).getCantidad() == 1) {
                        listaInsumosSelec.remove(i);
                        Toast.makeText(getApplicationContext(), "Producto eliminado.", Toast.LENGTH_SHORT).show();
                    } else {
                        listaInsumosSelec.get(i).setCantidad(listaInsumosSelec.get(i).getCantidad() - 1);
                    }
                }
            }
            setListProductSelec();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }
    public void onAddRecetaClick(View v) {
        try {
            InsumoAlmacenModel insumoAlmacenModel = ((InsumoAlmacenModel) listViewReceta.getAdapter().getItem((Integer) v.getTag()));
            for (int i = 0; i < listaInsumosSelec.size(); i++) {
                if (listaInsumosSelec.get(i).equals(insumoAlmacenModel)) {
                    listaInsumosSelec.get(i).setCantidad(listaInsumosSelec.get(i).getCantidad() + 1);
                }
            }
            setListProductSelec();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    public void onRemoveRecetaClick(View v) {
        try {
            InsumoAlmacenModel insumoAlmacenModel = ((InsumoAlmacenModel) listViewReceta.getAdapter().getItem((Integer) v.getTag()));
            for (int i = 0; i < listaInsumosSelec.size(); i++) {
                if (listaInsumosSelec.get(i).equals(insumoAlmacenModel)) {
                    if (listaInsumosSelec.get(i).getCantidad() == 1) {
                        listaInsumosSelec.remove(i);
                        Toast.makeText(getApplicationContext(), "Producto eliminado.", Toast.LENGTH_SHORT).show();
                    } else {
                        listaInsumosSelec.get(i).setCantidad(listaInsumosSelec.get(i).getCantidad() - 1);
                    }
                }
            }
            setListProductSelec();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }
}
