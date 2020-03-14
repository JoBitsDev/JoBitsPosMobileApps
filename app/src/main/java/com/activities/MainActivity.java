package com.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.controllers.MainController;
import com.services.models.ConfigModel;
import com.services.models.UbicacionModel;
import com.services.notifications.ReceiverNotificationService;
import com.utils.EnvironmentVariables;
import com.utils.exception.ExceptionHandler;
import com.utils.exception.ServerErrorException;
import com.utils.exception.ServerNoCompatibleException;
import com.utils.loading.LoadingHandler;
import com.utils.loading.LoadingProcess;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Locale;

/**
 * Capa: Activities
 * Clase que controla el XML del Main.
 *
 * @extends BaseActivity ya que es una activity propia de la aplicacion.
 */
public class MainActivity extends BaseActivity {//  |||||

    /**
     * Controller del MainActivity para manejar las peticiones a la capa inferior.
     */
    private MainController controller;

    //Varialbes de control

    /**
     * Label con el estado de la coneccion.
     */
    private TextView connectionStatusText;

    /**
     * Boton para activar las notificaciones.
     */
    private Button notificationButton;

    /**
     * Boton para iniciar el flujo de la aplicacion. Boton comenzar.
     */
    private Button initializeSesionButton;

    /**
     * Label que contiene la version del sistema que esta corriendo
     */
    private TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(2000);
            setContentView(R.layout.activity_main);
            initVarialbes();
            addListeners();

            updateConnectionText();
            loadConfig();
            setUpInfo();
            setUpLanguaje();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    private void setUpLanguaje() {
        Resources res = getApplicationContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale("es"));
        res.updateConfiguration(conf, dm);
    }

    private void setUpInfo() {
        new LoadingHandler<Void>(act, new LoadingProcess<Void>() {
            @Override
            public Void process() throws Exception {
                controller.readInfo();
                return null;
            }

            @Override
            public void post(Void value) {

            }
        });
    }

    private void loadConfig() {
        ConfigModel model;
        try {
            FileInputStream fis = openFileInput(EnvironmentVariables.CONFIG_PATH);
            ObjectInputStream ois = new ObjectInputStream(fis);
            model = (ConfigModel) ois.readObject();
            fis.close();
            ois.close();
            controller.setCfg(model);
        } catch (Exception e) {
            try {
                controller.setCfg(new ConfigModel());
                controller.guardarCFG(openFileOutput(EnvironmentVariables.CONFIG_PATH, Context.MODE_PRIVATE));
            } catch (Exception ex) {
            }
        }
    }


    @Override
    protected void onResume() {
        try {
            super.onResume();
            updateConnectionText();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    protected void initVarialbes() {
        try {
            controller = new MainController();

            notificationButton = (Button) findViewById(R.id.notificationButton);
            initializeSesionButton = (Button) findViewById(R.id.initializeSesionButton);
            connectionStatusText = ((TextView) (findViewById(R.id.connectionStatusText)));
            version = (TextView) findViewById(R.id.version);
            version.setText("Versión " + BuildConfig.VERSION_NAME);

        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    protected void addListeners() {
        try {
            initializeSesionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onInitializeSesionButtOnClick(v);
                }
            });
            notificationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNotificationButtonOnClick(v);
                }
            });
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            // Inflate the menuProductosListView; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.cambiar_ubicacion:
                cambiarUbicacion();
                return true;
            case R.id.editar_ubicacion:
                editarUbicacion();
                return true;
            case R.id.action_turn_on_offline_mode_main:
                setUpOffline();
                return true;
            case R.id.action_turn_off_offline_mode_main:
                setUpOnline();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cambiarUbicacion() {
        String ubicaciones[] = controller.getAllUbicaciones();

        new AlertDialog.Builder(act).
                setTitle(R.string.cambiar_ubicacion).
                setSingleChoiceItems(ubicaciones, controller.getCfg().getSelected(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        try {
                            controller.setSelected(which);
                            controller.guardarCFG(openFileOutput(EnvironmentVariables.CONFIG_PATH, Context.MODE_PRIVATE));
                            updateConnectionText();
                        } catch (Exception e) {
                        }
                        dialog.dismiss();
                    }
                }).setNeutralButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    private void editarUbicacion() {
        String ubicaciones[] = controller.getAllUbicaciones();
        new AlertDialog.Builder(act).
                setTitle(R.string.cambiar_ubicacion).
                setSingleChoiceItems(ubicaciones, controller.getCfg().getSelected(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.dismiss();


                        final int posicAReemplazar = which;

                        final Dialog d = new Dialog(act);
                        d.setContentView(R.layout.cambiar_ubicacion_dialog);

                        final EditText nombre = (EditText) d.findViewById(R.id.nombre);
                        final EditText ip = (EditText) d.findViewById(R.id.ip);
                        final EditText puerto = (EditText) d.findViewById(R.id.puerto);
                        final Button button = (Button) d.findViewById(R.id.ok);

                        d.setTitle("Editar Ubicación");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                d.dismiss();
                                try {
                                    String n = nombre.getText().toString();
                                    String i = ip.getText().toString();
                                    String p = puerto.getText().toString();
                                    controller.editarUbicacion(posicAReemplazar, new UbicacionModel(n, i, p));
                                    controller.guardarCFG(openFileOutput(EnvironmentVariables.CONFIG_PATH, Context.MODE_PRIVATE));
                                    updateConnectionText();
                                } catch (Exception e) {
                                    ExceptionHandler.handleException(e, act);
                                }
                            }
                        });
                        d.show();
                    }
                }).setNeutralButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();

    }

    /**
     * Accion a ejecutar cuando se da click en el boton de notificacion.
     *
     * @param v View de la aplicacion.
     */
    private void onNotificationButtonOnClick(View v) {
        try {
            startService(v);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    /**
     * Accion a ejecutar cuando se da click en el boton de iniciar.
     *
     * @param v View de la aplicacion.
     */
    private void onInitializeSesionButtOnClick(final View v) {
        new LoadingHandler<Boolean>(act, new LoadingProcess<Boolean>() {
            @Override
            public Boolean process() throws Exception {
                return controller.checkConnection();
            }

            @Override
            public void post(Boolean value) {
                if (value) {
                    //load the info
                    new LoadingHandler<Void>(act, new LoadingProcess<Void>() {
                        @Override
                        public Void process() throws Exception {
                            controller.readInfo();
                            return null;
                        }

                        @Override
                        public void post(Void value) {
                            if (getCompatibilidadServer()) {
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            } else {
                                ExceptionHandler.handleException(new ServerNoCompatibleException(), act);
                            }
                        }
                    });
                } else {
                    manageNoConnection(getResources().getText(R.string.noConnectionError).toString());
                }
            }
        });
    }

    /**
     * Actualiza el label de coneccion en dependencia de si hay o no coneccion con el servidor.
     */
    private void updateConnectionText() {
        new LoadingHandler<Boolean>(act, new LoadingProcess<Boolean>() {
            @Override
            public Boolean process() throws Exception {
                return controller.checkConnection();
            }

            @Override
            public void post(Boolean value) {
                if (value) {
                    connectionStatusText.setText(R.string.conexion_succesfull);
                    connectionStatusText.setTextColor(Color.GREEN);

                    setUpOnline();
                } else {
                    connectionStatusText.setText(R.string.no_network);
                    connectionStatusText.setTextColor(Color.RED);
                }
            }
        });
    }

    private boolean getCompatibilidadServer() {
        if (EnvironmentVariables.MAYOR != BuildConfig.MAYOR_SERVER_VERSION || BuildConfig.MINOR_SERVER_VERSION > EnvironmentVariables.MINOR) {
            return false;
        } else {
            return true;
        }
    }

    public void startService(View view) {
        try {
            if (notificationButton.getText().equals("Activar Notificaciones")) {
                startService(new Intent(MainActivity.this, ReceiverNotificationService.class));
                notificationButton.setText("Desactivar Notificationes");
            } else {
                stopService(new Intent(MainActivity.this, ReceiverNotificationService.class));
                notificationButton.setText("Activar Notificationes");
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

}
