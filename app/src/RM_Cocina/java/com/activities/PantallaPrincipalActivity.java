package com.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import com.controllers.CocinaController;
import com.services.models.ProductoVentaOrdenModel;
import com.services.web_connections.*;
import com.utils.adapters.MenuAdapter;
import com.utils.exception.ExceptionHandler;
import com.utils.loading.LoadingHandler;
import com.utils.loading.LoadingProcess;

import java.util.*;

public class PantallaPrincipalActivity extends BaseActivity {

    private CocinaController controller;
    private String user, cocinaTrabajo;
    private int wichCocina;
    private TextView labelRestName, labelCocinaName, labelUsuario;
    private ExpandableListView lista;
    private Button cambiarAreaButton;
    private ImageButton refreshButton;
    private List<ProductoVentaOrdenModel> pedidos = new ArrayList<ProductoVentaOrdenModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);

        initVarialbes();
        addListeners();
        onCambiarAreaButtonClick();
        configurarTabla();
    }


    @Override
    void initVarialbes() {
        try {
            controller = new CocinaController();
            cambiarAreaButton = (Button) findViewById(R.id.buttonCambiarArea);
            refreshButton = (ImageButton) findViewById(R.id.buttonRefresh);
            labelRestName = (TextView) findViewById(R.id.textViewNombreRest);
            labelCocinaName = (TextView) findViewById(R.id.textViewNombreCocina);
            labelCocinaName.setText("");
            labelUsuario = (TextView) findViewById(R.id.labelUsuario);
            lista = (ExpandableListView) findViewById(R.id.listaMesas);
            wichCocina = 0;

            if (labelRestName != null) {
                new LoadingHandler<String>(act, new LoadingProcess<String>() {
                    @Override
                    public String process() throws Exception {
                        return controller.getNombreRest();
                    }

                    @Override
                    public void post(String answer) {
                        labelRestName.setText(answer);
                    }
                });
            }

            user = getIntent().getExtras().getString(String.valueOf(R.string.user));
            cocinaTrabajo = getIntent().getExtras().getString(String.valueOf(R.string.cocina_cod));

            labelUsuario.setText(user);


            if (cocinaTrabajo == null) {
                cocinaTrabajo = "-";
            }
            labelCocinaName.setText(cocinaTrabajo);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    void addListeners() {
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
            }
        });

        lista.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                onListaChildClick(groupPosition, childPosition);
                return true;
            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }

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
                if (cocinaTrabajo == null) {
                    cocinaTrabajo = answer[0];
                }
                new AlertDialog.Builder(act).
                        setTitle(R.string.seleccionar_cocina).
                        setSingleChoiceItems(answer, wichCocina, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cocinaTrabajo = answer[which];
                                wichCocina = which;
                                dialog.dismiss();
                                configurarTabla();
                                labelCocinaName.setText(cocinaTrabajo);
                            }
                        }).create().show();
            }
        });
    }

    public void configurarTabla() {
        final String cocina = cocinaTrabajo;
        new LoadingHandler<MenuAdapter>(act, new LoadingProcess<MenuAdapter>() {
            @Override
            public MenuAdapter process() throws Exception {
                if (!cocinaTrabajo.equals("-")) {
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
    }

}
