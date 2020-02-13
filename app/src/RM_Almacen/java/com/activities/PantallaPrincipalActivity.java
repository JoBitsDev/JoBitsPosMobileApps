package com.activities;

import android.content.Intent;
import android.app.Dialog;
import android.text.*;
import android.view.*;
import android.widget.*;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;

import com.controllers.CentroElaboracionController;
import com.services.models.IpvRegistroModel;
import com.utils.adapters.*;
import com.utils.exception.ExceptionHandler;
import com.services.models.InsumoAlmacenModel;
import com.controllers.PantallaPrincipalController;
import com.utils.loading.LoadingHandler;
import com.utils.loading.LoadingProcess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Capa: Activities
 * Clase que controla el XML de la pantalla principal del Almacen.
 *
 * @extends BaseActivity ya que es una activity propia de la aplicacion.
 */
public class PantallaPrincipalActivity extends BaseActivity {

    /**
     * Controller del {@link PantallaPrincipalActivity} para manejar las peticiones a la capa inferior.
     */
    private PantallaPrincipalController controller;

    /**
     * Lista que muestra los productos en el almacen.
     */
    private ListView listView;
    private ListView listViewIPV;

    /**
     * Textos con el usuario y el almacen
     */
    private TextView almacenText, pickDate;

    /**
     * Cuadro de texto de busqueda.
     */
    private EditText searchText, searchTextIPV;

    /**
     * Radio Button para darle salida a producto.
     */
    private RadioButton radioButtonSalida;

    /**
     * NO SE USAN
     */
    private RadioButton radioButtonRebaja;
    private ImageButton salidaButton;
    private ImageButton entradaButton;

    /**
     * Spinner para filtrar por cocina.
     */
    private Spinner spinnerFiltrar, spinnerFiltrarIPV;

    /**
     * Adapter para el filtro.
     */
    private FilterAdapter filterAdapter;
    private FilterAdapterIPV filterAdapterIPV;

    private AlmacenInsumoAdapter almacenInsumoAdapter;
    private IPVsAdapter ipVsAdapter;
    private ImageButton imageButtonActualizar;
    private Button agregarInsumo;
    private TabHost host;
    private float lastX;
    List<IpvRegistroModel> ipvRegistroModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_almacenstate);
            setBundle(getIntent().getExtras());

            initVarialbes();//inicializa las  variables
            addListeners();//agrega los listener
            setAdapters();//agrega los adapters
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }

        new LoadingHandler<List<InsumoAlmacenModel>>(act, new LoadingProcess<List<InsumoAlmacenModel>>() {
            @Override
            public List<InsumoAlmacenModel> process() throws Exception {
                CentroElaboracionController cont = new CentroElaboracionController();
                List<InsumoAlmacenModel> prod = cont.getProductosDisponibles();
                List<InsumoAlmacenModel> filter = new ArrayList<InsumoAlmacenModel>();
                filter.add(prod.get(0));
                filter.add(prod.get(1));
                List<InsumoAlmacenModel> comb = cont.getCombinacionesCon(filter);
                return null;
            }

            @Override
            public void post(List<InsumoAlmacenModel> answer) {

            }
        });
    }


    @Override
    protected void initVarialbes() {
        try {
            listView = (ListView) findViewById(R.id.listaInsumos);
            listViewIPV = (ListView) findViewById(R.id.listViewIPVs);
            almacenText = (TextView) findViewById(R.id.textViewNombreAlmacen);

            pickDate = (TextView) findViewById(R.id.textViewFechaServidor);

            controller = new PantallaPrincipalController();
            searchText = (EditText) findViewById(R.id.editText);
            searchTextIPV = (EditText) findViewById(R.id.editTextBuscarIPV);
            radioButtonSalida = (RadioButton) findViewById(R.id.radioButtonSalida);
            radioButtonRebaja = (RadioButton) findViewById(R.id.radioButtonRebaja);
            salidaButton = (ImageButton) findViewById(R.id.salidaButton);
            entradaButton = (ImageButton) findViewById(R.id.entradaButton);
            imageButtonActualizar = (ImageButton) findViewById(R.id.imageButtonActualizar);
            agregarInsumo = (Button) findViewById(R.id.butonAgregarInsumo);
            spinnerFiltrar = (Spinner) findViewById(R.id.filtrarBy);
            spinnerFiltrarIPV = (Spinner) findViewById(R.id.filtrarByIPV);
            ipvRegistroModelList = new ArrayList<IpvRegistroModel>();
            initTab();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    protected void addListeners() {
        try {
            searchText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    ((AlmacenInsumoAdapter) listView.getAdapter()).getFilter().filter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            searchTextIPV.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    ((IPVsAdapter) listViewIPV.getAdapter()).getFilter().filter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            spinnerFiltrar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    onSpinnerFiltrarItemSelected(view);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            spinnerFiltrarIPV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    onSpinnerFiltrarItemSelectedIPV(view);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            agregarInsumo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAgregarInsumoClick();
                }
            });

            imageButtonActualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actualizar(v);
                }
            });
            //TODO: faltan dos listener que estan directo en el xml
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTabChangeTouchEvent(event);
            }
        });
        listViewIPV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTabChangeTouchEvent(event);
            }
        });
    }

    /**
     * Accion a llamar cuando se selecciona una cocina en el spinner de filtrar
     *
     * @param view View de la aplicacion.
     */
    private void onSpinnerFiltrarItemSelected(View view) {
        if (spinnerFiltrar.getSelectedItemPosition() == 0) {

            new LoadingHandler<AlmacenInsumoAdapter>(act, new LoadingProcess<AlmacenInsumoAdapter>() {
                @Override
                public AlmacenInsumoAdapter process() throws Exception {
                    return controller.getAdapter(act, R.id.listaInsumos);
                }

                @Override
                public void post(AlmacenInsumoAdapter answer) {
                    listView.setAdapter(answer);
                }
            });

        } else {

            new LoadingHandler<AlmacenInsumoAdapter>(act, new LoadingProcess<AlmacenInsumoAdapter>() {
                @Override
                public AlmacenInsumoAdapter process() throws Exception {
                    return controller.getAdapter(act, R.id.listaInsumos, spinnerFiltrar.getSelectedItem().toString());
                }

                @Override
                public void post(AlmacenInsumoAdapter answer) {
                    obtenerFecha();
                    listView.setAdapter(answer);
                }
            });

        }
    }

    private void onSpinnerFiltrarItemSelectedIPV(View view) {
        if (spinnerFiltrarIPV.getSelectedItemPosition() == 0) {

            new LoadingHandler<IPVsAdapter>(act, new LoadingProcess<IPVsAdapter>() {
                @Override
                public IPVsAdapter process() throws Exception {
                    return controller.getIPVAdapter(act, R.id.listViewIPVs);
                }

                @Override
                public void post(IPVsAdapter answer) {
                    listViewIPV.setAdapter(answer);
                }
            });

        } else {

            new LoadingHandler<IPVsAdapter>(act, new LoadingProcess<IPVsAdapter>() {
                @Override
                public IPVsAdapter process() throws Exception {
                    return controller.getIPVAdapter(act, R.id.listViewIPVs, spinnerFiltrarIPV.getSelectedItem().toString());

                }

                @Override
                public void post(IPVsAdapter answer) {
                    listViewIPV.setAdapter(answer);
                    obtenerFecha();
                }
            });
        }
    }

    private void obtenerFecha() {
        new LoadingHandler<Date>(act, new LoadingProcess<Date>() {
            @Override
            public Date process() throws Exception {
                List<IpvRegistroModel> models = controller.getIPVRegistro(spinnerFiltrarIPV.getSelectedItem().toString());
                if (models.isEmpty()) {
                    return new Date();
                }
                return models.get(0).getIpvRegistroPK().getFecha();
            }

            @Override
            public void post(Date answer) {
                pickDate.setText(formatDate(answer));
            }
        });
    }

    @Override
    protected void setAdapters() {
        try {

            new LoadingHandler<Void>(act, new LoadingProcess<Void>() {
                @Override
                public Void process() throws Exception {
                    filterAdapter = new FilterAdapter(act, R.layout.simple_spinner_dropdow_item, controller.getCocinasNames());
                    filterAdapterIPV = new FilterAdapterIPV(act, R.layout.simple_spinner_dropdow_item, controller.getCocinasNames());
                    ipVsAdapter = new IPVsAdapter(act, R.layout.list_ipvs, ipvRegistroModelList);
                    almacenInsumoAdapter = new AlmacenInsumoAdapter(act, R.id.listaInsumos, controller.getPrimerAlmacen());
                    return null;
                }

                @Override
                public void post(Void answer) {
                    spinnerFiltrar.setAdapter(filterAdapter.createAdapter());
                    spinnerFiltrarIPV.setAdapter(filterAdapterIPV.createAdapter());
                    listViewIPV.setAdapter(ipVsAdapter);
                    listView.setAdapter(almacenInsumoAdapter);
                }
            });


        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    public void actualizar(View view) {
        onSpinnerFiltrarItemSelected(view);
        onSpinnerFiltrarItemSelectedIPV(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_almacenstate, menu);
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            switch (id) {
                case R.id.action_imprimirEstado:
                    imprimirEstadoAlmacen();
                    return true;
                case R.id.action_ticket_compra:
                    imprimirTicketCompra();
                    return true;
                case R.id.action_listar_operaciones:
                    openOperacionesRealizadas();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return false;
        }
    }

    public void openOperacionesRealizadas() {
        Intent intent = new Intent(PantallaPrincipalActivity.this, VerOperacionesActivity.class);
        startActivity(intent);
    }


    /**
     * Manda a imprimir el ticket de compra.
     *
     * @return true si se puede imprimir el ticket, false de lo contrario.
     */
    private void imprimirTicketCompra() {
        new LoadingHandler<Boolean>(act, new LoadingProcess<Boolean>() {
            @Override
            public Boolean process() throws Exception {
                return controller.imprimirTicketCompra();

            }

            @Override
            public void post(Boolean answer) {
                if (answer) {
                    showMessage("Imprimiendo...");
                    //Toast.makeText(act, "Imprimiendo...", Toast.LENGTH_LONG);
                } else {
                    ExceptionHandler.handleException(new Exception("Error imprimiendo"), act);
                }
            }
        });
    }

    /**
     * Manda a imprimir el estado del almacen.
     *
     * @return true si se puede imprimir el ticket, false de lo contrario.
     */
    private void imprimirEstadoAlmacen() {
        new LoadingHandler<Boolean>(act, new LoadingProcess<Boolean>() {
            @Override
            public Boolean process() throws Exception {
                return controller.imprimirEstadoActualAlmacen();
            }

            @Override
            public void post(Boolean answer) {
                if (answer) {
                    showMessage("Imprimiendo...");
                } else {
                    ExceptionHandler.handleException(new Exception("Error imprimiendo"), act);
                }
            }
        });
    }

    /**
     * Accion a llamar cuando se le da entrada a un producto.
     *
     * @param v View de la aplicacion.
     */
    public void onEntradaClick(final View v) {
        try {
            final int pos = (Integer) v.getTag();
            final InsumoAlmacenModel insumoModel = ((InsumoAlmacenModel) listView.getAdapter().getItem(pos));

            final EditText input = new EditText(v.getContext());
            input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);

            final EditText amount = new EditText(v.getContext());
            amount.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            amount.setRawInputType(Configuration.KEYBOARD_12KEY);

            new AlertDialog.Builder(v.getContext()).
                    setView(input).
                    setTitle("Entrada de Insumo").
                    setMessage("Introduzca la cantidad de " + insumoModel.getInsumoModel() + " a dar entrada").
                    setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton(R.string.agregar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Float cantidad = Float.valueOf(0);
                    try {
                        cantidad = Float.parseFloat(input.getText().toString()) * insumoModel.getInsumoModel().getCostoPorUnidad();
                    } catch (Exception e) {
                        ExceptionHandler.handleException(e, act);
                    }
                    amount.setText(cantidad.toString());
                    amount.selectAll();
                    new AlertDialog.Builder(v.getContext()).setView(amount).setTitle("Monto").
                            setMessage("Introduzca el valor de la entrada ").setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {

                            new LoadingHandler<Void>(act, new LoadingProcess<Void>() {
                                @Override
                                public Void process() throws Exception {
                                    float cantidad = Float.parseFloat(input.getText().toString());
                                    float monto = Float.parseFloat(amount.getText().toString());
                                    controller.darEntrada(insumoModel, cantidad, monto);
                                    return null;
                                }

                                @Override
                                public void post(Void answer) {
                                    updateListView(v);
                                    dialog.dismiss();
                                }
                            });
                        }
                    }).create().show();
                    dialog.dismiss();
                }
            }).create().show();

            actualizar(v);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    private void onAgregarInsumoClick() {
        final Dialog d = new Dialog(act);
        d.setContentView(R.layout.agregar_insumo_dialog);
        final EditText nombre = (EditText) d.findViewById(R.id.nombreInsumo);
        final EditText estimacion = (EditText) d.findViewById(R.id.estimacionStock);
        final Spinner um = (Spinner) d.findViewById(R.id.insummoUM);
        final Button button = (Button) d.findViewById(R.id.ok);
        d.setTitle("Insertar nuevo insumo");//TODO arreglar Strings para los values del xml
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                try {
                    String n = nombre.getText().toString();
                    float est = Float.parseFloat(estimacion.getText().toString().isEmpty() ? "0" : estimacion.getText().toString());
                    String u = (String) um.getSelectedItem();
                    insertarNuevoInsumo(n, est, u);
                } catch (Exception e) {
                    ExceptionHandler.handleException(e, act);
                }
            }
        });
        d.show();
    }

    private void insertarNuevoInsumo(final String n, final float est, final String u) {
        new LoadingHandler<Void>(act, new LoadingProcess<Void>() {
            @Override
            public Void process() throws Exception {
                if (n.isEmpty() || est < 0) {
                    throw new NullPointerException("Error en los datos de entrada");
                }
                controller.agregarInsumo(n, est, u);
                return null;
            }

            @Override
            public void post(Void ans) {
                setAdapters();//update all
            }
        });
    }

    private void updateListView(View v) {
        listView.post(new Runnable() {
            @Override
            public void run() {
                try {
                    int index = listView.getFirstVisiblePosition();
                    View v = listView.getChildAt(0);
                    int top = v.getTop();

                    onSpinnerFiltrarItemSelected(v);
                    //listView.setAdapter(controller.getAdapter(act, R.id.listaInsumos));

                    listView.setSelectionFromTop(index, top);
                } catch (Exception e) {
                    ExceptionHandler.handleException(e, act);
                }
            }
        });
    }

    /**
     * Accion a llamar cuando se le dar rebaja a un producto o salida dependiendo del estado del combo box.
     *
     * @param v View de la aplicacion.
     */
    public void onSalidaRebajaClick(View v) {
        try {
            if (radioButtonSalida.isChecked()) {
                onSalidaClick(v);
            } else {
                onRebajaClick(v);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    /**
     * Accion a llamar cuando se le da salida a un producto.
     *
     * @param v View de la aplicacion.
     */
    private void onSalidaClick(final View v) {
        try {
            int pos = (Integer) v.getTag();
            final InsumoAlmacenModel i = ((InsumoAlmacenModel) listView.getAdapter().getItem(pos));

            new LoadingHandler<String[]>(act, new LoadingProcess<String[]>() {
                @Override
                public String[] process() throws Exception {
                    return controller.getCocinasNamesForIPV(i.getInsumoModel().getCodInsumo());
                }

                @Override
                public void post(final String[] ipvs) {
                    final EditText input = new EditText(v.getContext());
                    input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    input.setRawInputType(Configuration.KEYBOARD_12KEY);

                    new AlertDialog.Builder(v.getContext()).
                            setView(input).
                            setTitle("Salida a punto de elaboracion").
                            setMessage("Introduzca la cantidad de " + i.getInsumoModel() + " a dar salida").
                            setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).
                            setPositiveButton(R.string.agregar, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, final int which) {
                                    new AlertDialog.Builder(v.getContext())
                                            .setItems(ipvs, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(final DialogInterface dialog, final int whichAct) {
                                                    final float cantidad = Float.parseFloat(input.getText().toString());
                                                    if (cantidad > i.getCantidad()) {
                                                        Toast.makeText(v.getContext(), R.string.saldo_insuficiente, Toast.LENGTH_LONG);
                                                        dialog.dismiss();
                                                    } else {

                                                        new LoadingHandler<Void>(act, new LoadingProcess<Void>() {
                                                            @Override
                                                            public Void process() throws Exception {
                                                                controller.darSalida(i, cantidad, ipvs[whichAct]);
                                                                return null;
                                                            }

                                                            @Override
                                                            public void post(Void answer) {
                                                                updateListView(v);
                                                                dialog.dismiss();
                                                            }
                                                        });

                                                    }
                                                }
                                            }).setTitle("Destino")
                                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            }).
                                            create().show();
                                    dialog.dismiss();
                                }
                            }).create().show();
                }
            });
            actualizar(v);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    /**
     * Accion a llamar cuando se va a rebajar un producto.
     *
     * @param v View de la aplicacion.
     */
    private void onRebajaClick(final View v) {
        try {
            int pos = (Integer) v.getTag();
            final InsumoAlmacenModel insumoModel = ((InsumoAlmacenModel) listView.getAdapter().getItem(pos));

            final EditText input = new EditText(v.getContext());
            input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);

            final EditText razon = new EditText(v.getContext());
            razon.setInputType(InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);

            new AlertDialog.Builder(v.getContext()).
                    setView(input).
                    setTitle("Merma").
                    setMessage("Introduzca la cantidad de " + insumoModel.getInsumoModel() + " a rebajar").
                    setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).
                    setPositiveButton(R.string.agregar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new AlertDialog.Builder(v.getContext()).
                                    setView(razon).
                                    setTitle("Razon de rebaja").
                                    setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).setPositiveButton("Mermar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(final DialogInterface dialog, int which) {
                                    final float cantidad = Float.parseFloat(input.getText().toString());
                                    if (cantidad > insumoModel.getCantidad()) {
                                        Toast.makeText(v.getContext(), R.string.saldo_insuficiente, Toast.LENGTH_LONG);
                                        dialog.dismiss();
                                    } else {
                                        new LoadingHandler<Void>(act, new LoadingProcess<Void>() {
                                            @Override
                                            public Void process() throws Exception {
                                                controller.rebajar(insumoModel, cantidad, razon.getText().toString());
                                                return null;
                                            }

                                            @Override
                                            public void post(Void answer) {
                                                updateListView(v);
                                                dialog.dismiss();
                                            }
                                        });

                                    }
                                }

                            }).create().show();

                            dialog.dismiss();
                        }
                    }).create().show();
            actualizar(v);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    /**
     * Obtiene la informacion de los IPVs segun un insumo.
     *
     * @param insumoCod Codigo del insumo.
     * @return arreglo de String con los IPVs.
     */
    private String[] getIPVData(String insumoCod) {
        try {
            return controller.getCocinasNamesForIPV(insumoCod);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return null;
        }
    }

    private void initTab() {
        try {
            host = (TabHost) findViewById(R.id.tabHost);
            if (host != null) {//TODO: por que este if??
                host.setup();

                TabHost.TabSpec spec = host.newTabSpec("Almacen");

                //Tab 1
                spec.setContent(R.id.Almacen);
                spec.setIndicator("Almacen");
                host.addTab(spec);

                //Tab 2
                spec = host.newTabSpec("IPVs");
                spec.setContent(R.id.IPV);
                spec.setIndicator("IPVs");
                host.addTab(spec);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return onTabChangeTouchEvent(event);
    }

    private String formatDate(Date date) {
        return new SimpleDateFormat("dd'/'MM'/'yyyy").format(date);
    }

    private boolean onTabChangeTouchEvent(MotionEvent event) {
        try {
            float currentX = 0;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = event.getX();
                    break;
                case MotionEvent.ACTION_UP:
                    currentX = event.getX();
                    boolean dirRight = Math.abs(lastX - currentX) > 200;
                    switchTab(dirRight);
                    return dirRight;
            }
            return false;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return false;
        }
    }

    private boolean switchTab(boolean change) {
        try {
            if (change) {
                if (host.getCurrentTab() == 1) {
                    host.setCurrentTab(0);
                } else {
                    host.setCurrentTab(1);
                }
            }
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return false;
        }
    }
}
