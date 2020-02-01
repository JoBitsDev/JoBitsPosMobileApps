package com.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.controllers.MainController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.models.ConfigModel;
import com.services.notifications.ReceiverNotificationService;
import com.utils.EnvironmentVariables;
import com.utils.exception.ExceptionHandler;
import com.utils.loading.LoadingHandler;
import com.utils.loading.LoadingProcess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * Capa: Activities
 * Clase que controla el XML del Main.
 *
 * @extends BaseActivity ya que es una activity propia de la aplicacion.
 */
public class MainActivity extends BaseActivity {

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
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
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
            case R.id.agregar_ubicacion:
                agregarUbicacion();
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

    private void agregarUbicacion() {
        String ubicaciones[] = controller.getAllUbicaciones();
        new AlertDialog.Builder(act).
                setTitle(R.string.cambiar_ubicacion).
                setSingleChoiceItems(ubicaciones, controller.getCfg().getSelected(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        int posicAReemplazar = which;
                        new AlertDialog.Builder(act).
                                setView(R.layout.cambiar_ubicacion_dialog).
                                setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setPositiveButton(R.string.agregar_ubicacion, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //controller.agregarUbicacion(input.getText().toString());
                            }
                        }).create().show();




                        dialog.dismiss();
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
    private void onInitializeSesionButtOnClick(View v) {
        new LoadingHandler<Boolean>(act, new LoadingProcess<Boolean>() {
            @Override
            public Boolean process() {
                return controller.checkConnection();
            }

            @Override
            public void post(Boolean value) {
                if (value) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else {
                    act.showMessage(act.getApplication().getApplicationContext().getResources().
                            getText(R.string.noConnectionError).toString());
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
            public Boolean process() {
                return controller.checkConnection();
            }

            @Override
            public void post(Boolean value) {
                if (value) {
                    connectionStatusText.setText(R.string.conexion_succesfull);
                    connectionStatusText.setTextColor(Color.GREEN);
                } else {
                    connectionStatusText.setText(R.string.no_network);
                    connectionStatusText.setTextColor(Color.RED);
                }
            }
        });
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
