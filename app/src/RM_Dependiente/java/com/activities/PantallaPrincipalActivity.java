package com.activities;

import android.view.*;
import android.widget.*;
import android.content.*;
import android.os.Bundle;
import android.app.AlertDialog;

import com.services.models.MesaModel;
import com.utils.EnvironmentVariables;
import com.controllers.MesasController;

import com.utils.exception.*;
import com.utils.adapters.MesaAdapter;
import com.utils.loading.LoadingHandler;
import com.utils.loading.LoadingProcess;


public class PantallaPrincipalActivity extends BaseActivity {

    private MesasController controller;

    private TextView restNameLabel;
    private TextView userLabel;

    private ListView listaMesas;

    private Switch switchOrden;

    private Button cambiarAreaButton;//TODO: Esto no manda a barra, sino cambia de area
    private Button pedidoDomicilioButton;
    private Button RButton;
    private String selectedArea = null;
    private int selectedAreaWich = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);

        try {
            initVarialbes();
            addListeners();
            configurarTabla();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
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
                new LoadingHandler<String>(act, new LoadingProcess<String>() {
                    @Override
                    public String process() {
                        return controller.getNombreRest();
                    }

                    @Override
                    public void post(String value) {
                        restNameLabel.setText(value);
                    }
                });
            }

            listaMesas = (ListView) findViewById(R.id.listaMesas);

            cambiarAreaButton = (Button) findViewById(R.id.cambiarArea);
            pedidoDomicilioButton = (Button) findViewById(R.id.pedidoDomicilio);

            switchOrden = (Switch) findViewById(R.id.switchOrden);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    protected void addListeners() {
        try {
            cambiarAreaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCambiarAreaButtonClick(v);
                }
            });

            listaMesas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    return onListaMesasItemLongClick(position);
                }
            });

            switchOrden.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSwitchOrdenClick();
                }
            });
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    private void onSwitchOrdenClick() {//cambiar orden
        /*
        //no llama al servidor
        String orden = switchOrden.isChecked() ? String.valueOf(R.string.orden) : String.valueOf(R.string.mesa);
        MesaAdapter ad = ((MesaAdapter) listaMesas.getAdapter()).orderBy(orden);
        listaMesas.setAdapter(ad);*/

        configurarTabla();//llama al servidor
    }

    private boolean onListaMesasItemLongClick(int position) {
        try {
            configurarTabla();
            continuar(((MesaAdapter) listaMesas.getAdapter()).getMesa(position));
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return false;
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

    private void continuar(final MesaModel m) throws NoExistingException {
        try {
            final Bundle data = new Bundle();
            data.putString(String.valueOf(R.string.mesa), m.getCodMesa());

            controller.starService(m.getCodMesa());

            if (!m.getEstado().equals(EnvironmentVariables.ESTADO_MESA_VACIA)) {

                final String cod_orden = m.getEstado().split(" ")[0];
                data.putString(String.valueOf(R.string.user), m.getUsuario());

                controller.setCodOrden(cod_orden);

                new LoadingHandler<Boolean>(act, new LoadingProcess<Boolean>() {
                    @Override
                    public Boolean process() throws Exception {
                        boolean res = controller.validate();
                        if (!res) {
                            throw new NoExistingException("La orden a acceder ya no se encuentra abierta");
                        }
                        return res;
                    }

                    @Override
                    public void post(Boolean value) {
                        if (value) {
                            data.putString(String.valueOf(R.string.cod_Orden), cod_orden);

                            if (!controller.getUser().equals(m.getUsuario())) {//si no es el usuario pide confirmacion
                                AlertDialog.Builder builder = new AlertDialog.Builder(act);
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
                                data.putString(String.valueOf(R.string.user), controller.getUser());
                                entrarMiOrden(data);
                            }
                        }
                    }
                });

            } else {//es el usuario
                data.putString(String.valueOf(R.string.user), controller.getUser());
                entrarMiOrden(data);
            }

        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    public void onCambiarAreaButtonClick(View view) {//Cambia de area
        new LoadingHandler<String[]>(act, new LoadingProcess<String[]>() {
            @Override
            public String[] process() throws Exception {
                return controller.getAreas();
            }

            @Override
            public void post(final String[] value) {
                new AlertDialog.Builder(act).
                        setTitle(R.string.seleccionararea).
                        setSingleChoiceItems(value, selectedAreaWich, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedAreaWich = which;
                                selectedArea = value[selectedAreaWich];
                                dialog.dismiss();
                                configurarTabla();
                            }
                        }).create().show();
            }
        });

    }

    public void configurarTabla() {
        final String orden = switchOrden.isChecked() ? String.valueOf(R.string.orden) : String.valueOf(R.string.mesa);
        new LoadingHandler<MesaAdapter>(act, new LoadingProcess<MesaAdapter>() {
            @Override
            public MesaAdapter process() throws Exception {
                MesaAdapter adapter = controller.getData(selectedArea, act);
                adapter.orderBy(orden);
                return adapter;
            }

            @Override
            public void post(MesaAdapter value) {
                listaMesas.setAdapter(value);
            }
        });
    }

    private void entrarSoloLectura(Bundle data) {
        try {
            Intent launch = new Intent(act, OrdenReadOnlyActivity.class);
            launch.putExtras(data);
            startActivity(launch);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    private void entrarMiOrden(Bundle data) {
        try {
            Intent launch = new Intent(act, OrdenActivity.class);
            launch.putExtras(data);
            startActivity(launch);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

}
