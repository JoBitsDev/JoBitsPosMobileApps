package com.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;

import com.controllers.CocinaController;
import com.services.models.IpvRegistroModel;
import com.services.models.orden.ProductoVentaOrdenModel;
import com.utils.EnvironmentVariables;
import com.utils.adapters.IPVsAdapter;
import com.utils.adapters.MenuAdapter;
import com.utils.exception.ExceptionHandler;
import com.utils.loading.LoadingHandler;
import com.utils.loading.LoadingProcess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PantallaPrincipalActivity extends BaseActivity {

    List<IpvRegistroModel> ipvRegistroModelList;
    private CocinaController controller;
    private String user;
    private int wichCocina;
    private TextView labelRestName, labelCocinaName, labelUsuario, pickDate, textViewToChange;
    private ExpandableListView lista;
    private Button cambiarAreaButton;
    private ImageButton refreshButton;
    private List<ProductoVentaOrdenModel> pedidos = new ArrayList<ProductoVentaOrdenModel>();
    private TabHost host;
    private float lastX;
    private Switch switchExistencia;
    /**
     * Cuadro de texto de busqueda.
     */
    private EditText searchTextIPV;
    private IPVsAdapter ipVsAdapter;
    private ListView listViewIPV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);

        initVarialbes();
        initTab();
        addListeners();
        onCambiarAreaButtonClick();
        configurarTabla();
    }

    @Override
    void initVarialbes() {
        try {
            if (controller == null) {
                controller = new CocinaController("");
            }
            cambiarAreaButton = (Button) findViewById(R.id.buttonCambiarArea);
            refreshButton = (ImageButton) findViewById(R.id.buttonRefresh);
            labelRestName = (TextView) findViewById(R.id.textViewNombreRest);
            labelCocinaName = (TextView) findViewById(R.id.textViewNombreCocina);
            labelCocinaName.setText(controller.getCodCocina());
            labelUsuario = (TextView) findViewById(R.id.labelUsuario);
            lista = (ExpandableListView) findViewById(R.id.listaMesas);
            wichCocina = 0;
            listViewIPV = (ListView) findViewById(R.id.listViewIPVs);
            searchTextIPV = (EditText) findViewById(R.id.editTextBuscarIPV);
            ipvRegistroModelList = new ArrayList<IpvRegistroModel>();
            pickDate = (TextView) findViewById(R.id.textViewFechaServidor);
            switchExistencia = (Switch) findViewById(R.id.switchExisIPV);
            textViewToChange = (TextView) findViewById(R.id.editTextCosumido);

            if (labelRestName != null) {
                labelRestName.setText(EnvironmentVariables.NOMBRE_REST);
            }

            user = getIntent().getExtras().getString(String.valueOf(R.string.user));
            labelUsuario.setText(user);



            labelCocinaName.setText(controller.getCodCocina());

        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    void addListeners() {
        searchTextIPV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (listViewIPV != null && listViewIPV.getAdapter() != null) {
                    ((IPVsAdapter) listViewIPV.getAdapter()).getFilter().filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cambiarAreaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCambiarAreaButtonClick();
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefreshButtonClick(v);
                onSwitchClick();
            }
        });

        lista.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                onListaChildClick(groupPosition, childPosition);
                return true;
            }
        });
        lista.setOnTouchListener(new View.OnTouchListener() {
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
        switchExistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSwitchClick();
            }
        });
    }

    public void onSwitchClick() {
        if (host.getCurrentTab() == 0) {
            return;
        }
        final String orden = switchExistencia.isChecked() ? String.valueOf(R.string.ipv) : String.valueOf(R.string.exist);
        final String change = switchExistencia.isChecked() ? "Cosumidos" : "Vendidos";
        textViewToChange.setText(change);

        if (orden.equalsIgnoreCase(String.valueOf(R.string.ipv))) {

            new LoadingHandler<Void>(act, new LoadingProcess<Void>() {
                @Override
                public Void process() throws Exception {
                    ipVsAdapter = new IPVsAdapter(act, R.layout.list_ipv_cocina, controller.getIPVRegistroIPVS(controller.getCodCocina()));
                    return null;
                }

                @Override
                public void post(Void answer) {
                    listViewIPV.setAdapter(ipVsAdapter);
                }
            });
        } else {
            new LoadingHandler<Void>(act, new LoadingProcess<Void>() {
                @Override
                public Void process() throws Exception {
                    ipVsAdapter = new IPVsAdapter(act, R.layout.list_ipv_cocina, controller.getIPVRegistroExistencias(controller.getCodCocina()));
                    return null;
                }

                @Override
                public void post(Void answer) {
                    listViewIPV.setAdapter(ipVsAdapter);
                }
            });
        }
        obtenerFecha();
    }

    private void onListaChildClick(final int groupPosition, final int childPosition) {
        new LoadingHandler<String>(act, new LoadingProcess<String>() {
            @Override
            public String process() throws Exception {
                return controller.notificar((ProductoVentaOrdenModel)
                        (lista.getExpandableListAdapter()).getChild(groupPosition, childPosition));
            }

            @Override
            public void post(String answer) {
                new AlertDialog.Builder(act).setMessage(answer).
                        setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                configurarTabla();
                            }
                        }).create().show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initVarialbes();
        addListeners();
        //onCambiarAreaButtonClick();
        configurarTabla();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_activity_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    public void onAddClick(final View view) {
        new LoadingHandler<String>(act, new LoadingProcess<String>() {
            @Override
            public String process() throws Exception {
                return controller.notificar((ProductoVentaOrdenModel)
                        lista.getAdapter().getItem((Integer) view.getTag()));
            }

            @Override
            public void post(String answer) {
                new AlertDialog.Builder(act).
                        setMessage(answer).
                        setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                configurarTabla();
                            }
                        }).create().show();
            }
        });
    }

    public void onCambiarAreaButtonClick() {//Cambiar de area, de cocina
        new LoadingHandler<String[]>(act, new LoadingProcess<String[]>() {
            @Override
            public String[] process() throws Exception {
                return controller.getCocinasNames();
            }

            @Override
            public void post(final String[] answer) {
                if (controller.getCodCocina() == null) {
                    controller.setCodCocina(answer[0]);
                }
                new AlertDialog.Builder(act).
                        setTitle(R.string.seleccionar_cocina).
                        setSingleChoiceItems(answer, wichCocina, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                wichCocina = which;
                                controller.setCodCocina(answer[wichCocina]);
                                dialog.dismiss();
                                configurarTabla();
                                onSwitchClick();
                                labelCocinaName.setText(controller.getCodCocina());
                            }
                        }).create().show();
            }
        });
    }

    public void configurarTabla() {
        final String cocina = controller.getCodCocina();
        new LoadingHandler<MenuAdapter>(act, new LoadingProcess<MenuAdapter>() {
            @Override
            public MenuAdapter process() throws Exception {
                if (!controller.getCodCocina().equals("-")) {
                    pedidos = controller.fetchPendingOrders(cocina);
                }
                if (pedidos == null) {
                    pedidos = new ArrayList<ProductoVentaOrdenModel>();
                }
                return new MenuAdapter(act, pedidos);
            }

            @Override
            public void post(MenuAdapter answer) {
                lista.setAdapter(answer);
            }
        });

    }

    public void onRefreshButtonClick(View view) {//refrescar
        configurarTabla();
        onSwitchClick();
    }

    private void obtenerFecha() {
        pickDate.setText(formatDate(new Date()));
    }

    private void initTab() {
        try {
            host = (TabHost) findViewById(R.id.tabHost);
            if (host != null) {//TODO: por que este if??
                host.setup();

                TabHost.TabSpec spec = host.newTabSpec("Estado");

                //Tab 1
                spec.setContent(R.id.Estado);
                spec.setIndicator("Estado");
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
