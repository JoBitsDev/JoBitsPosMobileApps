package com.activities;

import android.view.*;
import android.widget.*;
import android.content.*;
import android.os.Bundle;
import android.app.AlertDialog;

import com.services.models.MesaModel;
import com.utils.EnvironmentVariables;
import com.controllers.MesasController;

import java.util.*;

import com.utils.exception.*;


public class PantallaPrincipalActivity extends BaseActivity {

    private MesasController controller;

    private TextClock clockText;
    private TextView restNameLabel;
    private TextView userLabel;

    private ListView lista;

    private Button cambiarAreaButton;//TODO: Esto no manda a barra, sino cambia de area
    private Button pedidoDomicilioButton;
    private Button RButton;
    private List<MesaModel> mesaModels = new ArrayList<MesaModel>();
    private String selectedArea = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);

        try {
            initVarialbes();
            addListeners();
            configurarTabla();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    @Override
    protected void initVarialbes() {
        try {
            controller = new MesasController();
            String user = getIntent().getExtras().getString(String.valueOf(R.string.user));
            controller.setUser(user);

            userLabel = ((TextView) findViewById(R.id.textviewusuario));
            userLabel.setText(user);

            restNameLabel = (TextView) findViewById(R.id.textViewNombreRest);
            if (restNameLabel != null) {
                restNameLabel.setText(controller.getNombreRest());
            }

            lista = (ListView) findViewById(R.id.listaMesas);
            clockText = (TextClock) findViewById(R.id.textClock);

            cambiarAreaButton = (Button) findViewById(R.id.cambiarArea);
            pedidoDomicilioButton = (Button) findViewById(R.id.pedidoDomicilio);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    @Override
    protected void addListeners() {
        try {
            final BaseActivity act = this;
            cambiarAreaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        cambiarArea(v);
                    } catch (Exception e) {
                        ExceptionHandler.handleException(e, act);
                    }
                }
            });
            lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        configurarTabla();
                        lista.setAdapter(controller.getData(mesaModels, selectedArea, act));
                        continuar(mesaModels.get(position));
                        return true;
                    } catch (Exception e) {
                        ExceptionHandler.handleException(e, act);
                        return false;
                    }
                }
            });
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        configurarTabla();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_principal, menu);
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

    private void continuar(MesaModel m) {
        try {

            final Bundle data = new Bundle();
            data.putString(String.valueOf(R.string.user), controller.getUser());
            data.putString("mesa", m.getCodMesa());

            controller.starService(m.getCodMesa());
            if (!m.getEstado().equals(EnvironmentVariables.ESTADO_MESA_VACIA)) {
                String cod_orden = m.getEstado().split(" ")[0];
                controller.setCodOrden(cod_orden);
                if (!controller.validate()) {
                    throw new NoExistingException("La orden a acceder ya no se encuentra abierta", this);
                }
                data.putString("codOrden", cod_orden);

                if (!controller.getUser().equals(m.getEstado().split(" ")[1])) {//si no es el usuario pide confirmacion
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("La mesa que quiere acceder " +
                            "la esta atendiendo otro camarero");
                    builder.setNegativeButton("No entrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//no entrar
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("Entrar en modo solo lectura", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//entrar en solo lectura
                            entrarSoloLectura(data);
                        }
                    });
                    builder.show();

                } else {
                    entrarSoloLectura(data);
                }
            } else {//es el usuario
                entrarMiOrden(data);
            }

        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    public void cambiarArea(View view) {
        try {
            final String[] areas = controller.getAreas();
            new AlertDialog.Builder(this).
                    setTitle(R.string.seleccionararea).
                    setSingleChoiceItems(areas, 1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            selectedArea = areas[which];
                            dialog.dismiss();
                            configurarTabla();
                        }
                    }).create().show();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    public void configurarTabla() {
        try {
            final BaseActivity act = this;
            showProgressDialog();
            lista.post(new Runnable() {
                @Override
                public void run() {
                    lista.setAdapter(controller.getData(mesaModels, selectedArea, act));
                    hideProgressDialog();
                }
            });
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    private void entrarSoloLectura(Bundle data) {
        try {
            Intent launch = new Intent(this, OrdenReadOnlyActivity.class);
            launch.putExtras(data);
            startActivity(launch);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    private void entrarMiOrden(Bundle data) {
        try {
            Intent launch = new Intent(this, OrdenActivity.class);
            launch.putExtras(data);
            startActivity(launch);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

}
