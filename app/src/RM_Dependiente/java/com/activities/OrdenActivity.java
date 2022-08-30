package com.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.controllers.OrdenController;
import com.services.models.orden.OrdenModel;
import com.services.models.orden.ProductoVentaModel;
import com.services.models.orden.ProductoVentaOrdenModel;
import com.services.models.orden.SeccionModel;
import com.utils.EnvironmentVariables;
import com.utils.adapters.MenuAdapterThis;
import com.utils.adapters.ProductoVentaOrdenAdapter;
import com.utils.adapters.SeccionAdapter;
import com.utils.exception.DayClosedException;
import com.utils.exception.ExceptionHandler;
import com.utils.loading.LoadingHandler;
import com.utils.loading.LoadingProcess;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrdenActivity extends BaseActivity {

    private OrdenController controller;
    private Button cerrarOrdenButton, despacharACocinaButton;
    private TextView mesaNoLabel, ordenNoLabel, dependienteLabel, totalPrincipalLabel;
    private EditText searchText;
    private ListView menuProductosListView, menuSeccionListView;
    private ListView listaOrden;
    private CheckBox deLaCasaCheckBox;
    private List<SeccionModel> secciones;
   // private List<ProductoVentaModel> productos;
    private List<ProductoVentaOrdenModel> productosVentaOrden;
    private ProductoVentaModel lastClickedMenu = null;
    private ProductoVentaOrdenModel lastClickedOrden = null;
    private SeccionAdapter seccionAdapter;
    private MenuAdapterThis menuAdapter;
    private SeccionModel seccionSelected;
    private ProductoVentaOrdenAdapter productoVentaOrdenAdapter;
    private TabHost host;
    private float lastX;
    private String idMesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_orden);

            initVarialbes();

            //separado porque es un init y un set adapter en paralelo
            Bundle bundleExtra = getIntent().getExtras();
            idMesa = bundleExtra.getString(String.valueOf(R.string.mesa));
            initMenu(idMesa);

            setAdapters();
            addListeners();

            createOldOrden(bundleExtra);//crea la orden vieja
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    private void actDeLaCasa() {
        deLaCasaCheckBox.setChecked(controller.isDeLaCasa());
    }

    @Override
    protected void initVarialbes() {
        try {
            mesaNoLabel = (TextView) findViewById(R.id.mesaNoLabel);
            ordenNoLabel = (TextView) findViewById(R.id.ordenNoLabel);
            dependienteLabel = (TextView) findViewById(R.id.dependienteLabel);
            totalPrincipalLabel = (TextView) findViewById(R.id.totalPrincipalLabel);
            deLaCasaCheckBox = (CheckBox) findViewById(R.id.deLaCasaCheckBox);
            menuProductosListView = (ListView) findViewById(R.id.menuListView);
            menuSeccionListView = (ListView) findViewById(R.id.menuPrincipalListView);

            listaOrden = (ListView) findViewById(R.id.listaOrden);
            cerrarOrdenButton = (Button) findViewById(R.id.buttonCerrarOrden);
            despacharACocinaButton = (Button) findViewById(R.id.buttondespacharCocina);
            searchText = (EditText) findViewById(R.id.searchText);

            Bundle bundleExtra = getIntent().getExtras();
            mesaNoLabel.setText(bundleExtra.getString(String.valueOf(R.string.mesa)));//set el No de la mesa

            String user = bundleExtra.getString(String.valueOf(R.string.user));
            dependienteLabel.setText(user);//set el label con el dependiente
            controller = new OrdenController(user);

            ordenNoLabel.setText(bundleExtra.getString(String.valueOf(R.string.cod_Orden)));//set el label de la orden
            productosVentaOrden = new ArrayList<ProductoVentaOrdenModel>();
            menuAdapter = new MenuAdapterThis(act, R.layout.list_menu, new ArrayList<ProductoVentaModel>());

            initTab();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    protected void addListeners() {
        try {
            if (cerrarOrdenButton != null) {
                cerrarOrdenButton.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        onCerrarOrdenButtonLongClick();
                        return true;
                    }
                });
            }

            if (despacharACocinaButton != null) {
                despacharACocinaButton.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        onDespacharACocinaLongClick();
                        return true;
                    }
                });
            }

            menuSeccionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onSeccionClick(position);
                }
            });

            menuProductosListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    onMenuProductosListViewLongClick(view, position);
                    return true;
                }
            });

            menuProductosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onMenuListViewClick(position);
                }
            });

            listaOrden.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return onTabChangeTouchEvent(event);

                }
            });

            menuProductosListView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return onTabChangeTouchEvent(event);
                }
            });

            searchText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSearchTextClick(v);
                }
            });

            searchText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //metodo abstracto en el padre y necesita reimplementacion, pero no se usa aqui y se deja vacio
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    onSearchTextTextChange(s);
                }

                @Override
                public void afterTextChanged(Editable s) {
                    //metodo abstracto en el padre y necesita reimplementacion, pero no se usa aqui y se deja vacio
                }
            });

            deLaCasaCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeLaCasaCheckBoxClick();
                }
            });

        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    private void onSearchTextTextChange(CharSequence s) {
        if (!s.toString().isEmpty()) {
            seccionAdapter.getFilter().filter(s.toString());
        } else {
            seccionAdapter.getFilter().filter("");
        }
        onSeccionClick(seccionAdapter.getPosition(seccionSelected));
    }

    private void onSearchTextClick(View v) {
        searchText.setText("");
        seccionAdapter.getFilter().filter("");
        menuAdapter = new MenuAdapterThis(getApplicationContext(), R.layout.list_menu, new ArrayList<ProductoVentaModel>());
        menuProductosListView.setAdapter(menuAdapter);
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


    private void onDeLaCasaCheckBoxClick() {
        new LoadingHandler<Void>(act, new LoadingProcess<Void>() {
            @Override
            public Void process() throws Exception {
                controller.setDeLaCasa(deLaCasaCheckBox.isChecked());
                return null;
            }

            @Override
            public void post(Void value) {

            }
        });
    }

    private void getRestante(final ProductoVentaModel prod) {
        new LoadingHandler<Integer>(act, new LoadingProcess<Integer>() {
            @Override
            public Integer process() throws Exception {
                return controller.getRestantes(prod.getPCod());

            }

            @Override
            public void post(Integer value) {
                Toast.makeText(act, "Quedan minimo " + value + " de " + prod.getNombre(), Toast.LENGTH_LONG).show();//TODO: como que error al autenticar??
            }
        });
    }

    private void onSeccionClick(int position) {
        try {
            if (position >= 0) {
                seccionSelected = seccionAdapter.getItem(position);
                List<ProductoVentaModel> productosSelected = seccionSelected.getProductoVentaList();
                if (!productosSelected.isEmpty()) {
                    menuAdapter = new MenuAdapterThis(act, R.layout.list_menu, productosSelected);
                    menuProductosListView.setAdapter(menuAdapter);
                }
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    private boolean onMenuProductosListViewLongClick(final View v, final int position) {
        try {
            lastClickedMenu = menuAdapter.getItem(position);
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
                    addProductosVarios(Float.parseFloat(input.getText().toString()));
                }
            }).create().show();
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return false;
        }
    }

    private boolean onMenuOrdenListViewAddLongClick(final View v, final int position) {
        try {
            lastClickedMenu = productoVentaOrdenAdapter.getItem(position).getProductoVenta();
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
                    addProductosVarios(Float.parseFloat(input.getText().toString()));
                }
            }).create().show();
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return false;
        }
    }

    private boolean onMenuOrdenListViewRemoveLongClick(final View v, final int position) {
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
                    }).setPositiveButton("Quitar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    lastClickedOrden = ((ProductoVentaOrdenModel) listaOrden.getAdapter().getItem((Integer) v.getTag()));
                    removeProductosVarios(Float.parseFloat(input.getText().toString()));
                }
            }).create().show();
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return false;
        }
    }

    private boolean onMenuListViewClick(int position) {
        try {
            lastClickedMenu = menuAdapter.getItem(position);
            addProductosVarios(1);
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return false;
        }
    }

    @Override
    protected void setAdapters() {
        try {

            productoVentaOrdenAdapter = new ProductoVentaOrdenAdapter(act, R.id.listaOrden, productosVentaOrden);
            productoVentaOrdenAdapter.setAddListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onMenuOrdenListViewAddLongClick(v, (Integer) v.getTag());
                }
            });

            productoVentaOrdenAdapter.setRemoveListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onMenuOrdenListViewRemoveLongClick(v, (Integer) v.getTag());
                }
            });

            listaOrden.setAdapter(productoVentaOrdenAdapter);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    private void setMenuSeccionListViewAdapters() {
        seccionAdapter = new SeccionAdapter(act, R.id.menuListView, secciones);
        menuSeccionListView.setAdapter(seccionAdapter);
    }

    private void createOldOrden(final Bundle bundleExtra) {
        try {

            OrdenActivity old = (OrdenActivity) getLastNonConfigurationInstance();//TODO: porque un metodo deprecated??
            final String codOrden = old != null ? old.getController().getCodOrden() : bundleExtra.getString(String.valueOf(R.string.cod_Orden));

            final String mesa = bundleExtra.getString(String.valueOf(R.string.mesa));
            final String dependiente = bundleExtra.getString(String.valueOf(R.string.user));

            if (codOrden != null && mesa != null && dependiente != null) {
                new LoadingHandler<List<ProductoVentaOrdenModel>>(act, new LoadingProcess<List<ProductoVentaOrdenModel>>() {
                    @Override
                    public List<ProductoVentaOrdenModel> process() throws Exception {
                        return controller.starService(codOrden, mesa).getProductoVentaOrden(codOrden);
                    }

                    @Override
                    public void post(List<ProductoVentaOrdenModel> value) {
                        fillAct(value);
                        ordenNoLabel.setText(controller.getCodOrden());
                        actDeLaCasa();
                    }
                });

            } else {

                new LoadingHandler<Void>(act, new LoadingProcess<Void>() {
                    @Override
                    public Void process() throws Exception {
                        controller.starService(mesa);
                        if (!controller.initOrden()) {
                            throw new DayClosedException(getResources().getString(R.string.dayClosedError));
                        }
                        return null;
                    }

                    @Override
                    public void post(Void value) {
                        ordenNoLabel.setText(controller.getCodOrden());
                        actDeLaCasa();
                    }
                });

            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    private void initTab() {
        try {
            host = (TabHost) findViewById(R.id.tabHost);
            if (host != null) {//TODO: por que este if??
                host.setup();

                TabHost.TabSpec spec = host.newTabSpec("Pedido");


                //Tab 2
                spec.setContent(R.id.ordenModel);
                spec.setIndicator("Pedido");
                host.addTab(spec);

                //Tab 1
                spec = host.newTabSpec("Menu");
                spec.setContent(R.id.menu);
                spec.setIndicator("Menu");
                host.addTab(spec);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        return this;
    }

    private void fillAct(final List<ProductoVentaOrdenModel> orden) {
        try {
            final ProductoVentaOrdenAdapter adapter = (ProductoVentaOrdenAdapter) listaOrden.getAdapter();
            adapter.clear();
            adapter.setObjects(orden);
            updateCosto();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    private void initMenu(final String codArea) {
        new LoadingHandler<Void>(act, new LoadingProcess<Void>() {
            @Override
            public Void process() throws Exception {
                secciones = controller.getSecciones(idMesa);
               // productos = controller.getProductos(codArea);
                return null;
            }

            @Override
            public void post(Void value) {
                for (Iterator<SeccionModel> it = secciones.iterator(); it.hasNext(); ) {//TODO: Revisar el for que asi es como mejor funciona
                    SeccionModel secc = it.next();
                    if (secc.getProductoVentaList().isEmpty()) {
                        it.remove();
                    }
                }

                setMenuSeccionListViewAdapters();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            // Inflate the menuProductosListView; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_orden, menu);
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
                case R.id.moverAMesa:
                    moverAMesa();
                    break;
                case R.id.enviarCocina:
                    despacharACocina();
                    break;
                case R.id.cerrarOrden:
                    cerrarOrden();
                    break;
                case R.id.compartirPedido:
                    compartirPedido();
                    break;
                case R.id.cederACamarero:
                    cederACamarero();
                    break;
                default:
                    super.onOptionsItemSelected(item);
                    break;
            }

            return super.onOptionsItemSelected(item);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return false;
        }

    }

    private void cederACamarero() {
        new LoadingHandler<String[]>(act, new LoadingProcess<String[]>() {
            @Override
            public String[] process() throws Exception {
                return controller.getUsuariosActivos();
            }

            @Override
            public void post(final String[] value) {
                if (value.length > 0) {//hay usuarios
                    new AlertDialog.Builder(act).
                            setTitle(R.string.seleccioneElUsuarioACeder).
                            setSingleChoiceItems(value, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(final DialogInterface dialog, final int which) {

                                    new LoadingHandler<Boolean>(act, new LoadingProcess<Boolean>() {
                                        @Override
                                        public Boolean process() throws Exception {
                                            return controller.cederAUsuario(value[which]);
                                        }

                                        @Override
                                        public void post(Boolean value) {
                                            if (value) {//TODO: ver bien esto, xq el finish()
                                                dialog.dismiss();
                                                finish();
                                                Toast.makeText(getApplicationContext(), "Orden cedida al camarero.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                dialog.dismiss();
                                                Toast.makeText(getApplicationContext(), "No se pudo seder la orden.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }
                            }).setNeutralButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
                } else {
                    Toast.makeText(act, "No existe ningun otro usuario trabajando para ceder orden", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void compartirPedido() {//TODO: vacio???

    }

    public void onAddClick(View v) {
        try {
            lastClickedMenu = ((ProductoVentaOrdenModel) listaOrden.getAdapter().getItem((Integer) v.getTag())).getProductoVenta();
            addProductosVarios(1);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    public void onViewRestantes(View v) {
        try {
            ProductoVentaModel last = ((ProductoVentaModel) menuProductosListView.getAdapter().getItem((Integer) v.getTag()));
            getRestante(last);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    public void onRemoveClick(View v) {//TODO: listener en el xml??
        try {
            lastClickedOrden = ((ProductoVentaOrdenModel) listaOrden.getAdapter().getItem((Integer) v.getTag()));
            removeProductosVarios(1);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    public void onAdjuntoClick(View v) {//TODO: listener en el xml??
        try {
            lastClickedOrden = (ProductoVentaOrdenModel) listaOrden.getAdapter().getItem((Integer) v.getTag());

            final AlertDialog.Builder builder = new AlertDialog.Builder(act);
            builder.setTitle("Agregar Nota");

            // Get the layout inflater
            final EditText nota = new EditText(act);

            new LoadingHandler<String>(act, new LoadingProcess<String>() {
                @Override
                public String process() throws Exception {
                    return controller.getNota(lastClickedOrden);
                }

                @Override
                public void post(String value) {
                    nota.getText().insert(0, value);

                    // Inflate and set the layout for the dialog
                    // Pass null as the parent view because its going in the dialog layout
                    builder.setView(nota)
                            // Add action buttons
                            .setPositiveButton(R.string.agregar, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    addNota(toHTMLString(nota.getText().toString()));
                                }
                            })
                            .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder.create().show();
                }
            });


        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    public void onComensalClick(View v) {//TODO: listener en el xml??
        try {
            lastClickedOrden = ((ProductoVentaOrdenModel) listaOrden.getAdapter().getItem((Integer) v.getTag()));

            final AlertDialog.Builder builder = new AlertDialog.Builder(act);
            builder.setTitle("Agregar Comensal");

            final EditText nota = new EditText(act);

            new LoadingHandler<String>(act, new LoadingProcess<String>() {
                @Override
                public String process() throws Exception {
                    return controller.getComensal(lastClickedOrden);
                }

                @Override
                public void post(String value) {
                    nota.getText().insert(0, value);

                    // Inflate and set the layout for the dialog
                    // Pass null as the parent view because its going in the dialog layout
                    builder.setView(nota)
                            // Add action buttons
                            .setPositiveButton(R.string.agregar, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    addComensal(nota.getText().toString());
                                }
                            })
                            .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder.create().show();
                }
            });

        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    private void addComensal(final String comensal) {
        new LoadingHandler<Boolean>(act, new LoadingProcess<Boolean>() {
            @Override
            public Boolean process() throws Exception {
                return controller.addComensal(lastClickedOrden, comensal);
            }

            @Override
            public void post(Boolean value) {
                if (value) {
                    Toast.makeText(act, lastClickedOrden.getNombreProductoVendido() + " Comensal Agregado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(act, "Error agragando comensal.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void moverAMesa() {//TODO: terminar esto
        new LoadingHandler<String[]>(act, new LoadingProcess<String[]>() {
            @Override
            public String[] process() throws Exception {
                return controller.getMesas();
            }

            @Override
            public void post(final String[] value) {
                new AlertDialog.Builder(act).
                        setTitle(R.string.seleccioneLaMesaAMover).
                        setSingleChoiceItems(value, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                new LoadingHandler<Boolean>(act, new LoadingProcess<Boolean>() {
                                    @Override
                                    public Boolean process() throws Exception {
                                        return controller.moverAMesa(value[which]);
                                    }

                                    @Override
                                    public void post(Boolean value) {
                                        if (value) {//TODO: ver bien esto, xq el finish()
                                            dialog.dismiss();
                                            finish();
                                            Toast.makeText(getApplicationContext(), "Movido a mesa", Toast.LENGTH_SHORT).show();//TODO: ver bien el texto
                                        } else {
                                            dialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "No se pudo mover a mesa.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }).
                        setNeutralButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });
    }

    private void updateCosto() {
        try {
            float tot = 0;
            for (ProductoVentaOrdenModel x : productosVentaOrden) {
                tot += x.getPrecioVendido()* x.getCantidad();
            }
            if (EnvironmentVariables.MONEDA_PRINCIPAL.equals(" MN")) {
                totalPrincipalLabel.setText(EnvironmentVariables.setDosLugaresDecimales(tot) + EnvironmentVariables.MONEDA_PRINCIPAL);
            } else {
                totalPrincipalLabel.setText(EnvironmentVariables.setDosLugaresDecimales(tot) + EnvironmentVariables.MONEDA_PRINCIPAL);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    public void addProductosVarios(final float cantidad) {
        if (lastClickedMenu != null) {
            new LoadingHandler<OrdenModel>(act, new LoadingProcess<OrdenModel>() {
                @Override
                public OrdenModel process() throws Exception {
                    return controller.addProducto(lastClickedMenu, cantidad);
                }

                @Override
                public void post(OrdenModel value) {
                    ProductoVentaOrdenAdapter adapter =  (ProductoVentaOrdenAdapter) listaOrden.getAdapter();
                    adapter.clear();
                    adapter.addAll(value.getProductovOrdenList());
                    adapter.notifyDataSetChanged();
                    updateCosto();
                    Toast.makeText(act, "Agregado " + cantidad + " " + lastClickedMenu.getNombre() + " al pedido.", Toast.LENGTH_SHORT).show();
                      //  controller.increasePoducto(lastClickedMenu, (ProductoVentaOrdenAdapter) listaOrden.getAdapter(), cantidad);
                        //updateCosto();
                        //Toast.makeText(act, "Agregado " + cantidad + " " + lastClickedMenu.getNombre() + " al pedido.", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(act, R.string.noItemSeleccionado, Toast.LENGTH_SHORT).show();
        }
    }

    private void removeProductosVarios(final float cantidad) {
        if (lastClickedOrden != null) {
            new LoadingHandler<Boolean>(act, new LoadingProcess<Boolean>() {
                @Override
                public Boolean process() throws Exception {
                    return controller.removeProducto(lastClickedOrden, cantidad);
                }

                @Override
                public void post(Boolean value) {
                    if (value) {
                        ProductoVentaOrdenAdapter adapter = (ProductoVentaOrdenAdapter) listaOrden.getAdapter();
                        adapter.decrease(lastClickedOrden, cantidad);
                        updateCosto();
                        Toast.makeText(act, "Removido " + cantidad + " " + lastClickedOrden.getNombreProductoVendido() + " del pedido.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(act, R.string.errorAlBorrar, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(act, R.string.noItemSeleccionado, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean onCerrarOrdenButtonLongClick() {
        try {
            cerrarOrden();
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return false;
        }
    }

    private void cerrarOrden() {
        new LoadingHandler<Boolean>(act, new LoadingProcess<Boolean>() {
            @Override
            public Boolean process() throws Exception {
                return controller.finishOrden();
            }

            @Override
            public void post(Boolean value) {
                if (value) {
                    new AlertDialog.Builder(act).
                            setMessage(R.string.cerrarOrden).
                            setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    finish();
                                }
                            }).create().show();
                } else {
                    new AlertDialog.Builder(act).setMessage(R.string.error_al_cerrar_falta_enviar_cocina).
                            setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                }
            }
        });
    }

    private boolean onDespacharACocinaLongClick() {
        try {
            despacharACocina();
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return false;
        }
    }

    private void despacharACocina() {
        new LoadingHandler<List<ProductoVentaOrdenModel>>(act, new LoadingProcess<List<ProductoVentaOrdenModel>>() {
            @Override
            public List<ProductoVentaOrdenModel> process() throws Exception {
                controller.sendToKitchen();
                return controller.getProductoVentaOrden();
            }

            @Override
            public void post(List<ProductoVentaOrdenModel> value) {
                fillAct(value);
                new AlertDialog.Builder(act).setMessage(R.string.enviarAcocina).create().show();
            }
        });
    }

    private void addNota(final String nota) {
        new LoadingHandler<Boolean>(act, new LoadingProcess<Boolean>() {
            @Override
            public Boolean process() throws Exception {
                return controller.addNota(lastClickedOrden, nota);
            }

            @Override
            public void post(Boolean value) {
                Toast.makeText(act, lastClickedOrden.getNombreProductoVendido() + " Nota Agregada", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String toHTMLString(String s) {
        return s.replace(' ', '-');
    }

    public OrdenController getController() {
        return controller;
    }
}

