package com.activities;

import android.widget.*;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.graphics.Color;

import com.controllers.MainController;
import com.services.notifications.ReceiverNotificationService;
import com.utils.exception.ExceptionHandler;
import com.utils.loading.LoadingHandler;
import com.utils.loading.LoadingProcess;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_main);
            initVarialbes();
            addListeners();

            updateConnectionText();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    @Override
    protected void onResume() {
        try {
            super.onResume();
            updateConnectionText();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    @Override
    protected void initVarialbes() {
        try {
            controller = new MainController();

            notificationButton = (Button) findViewById(R.id.notificationButton);
            initializeSesionButton = (Button) findViewById(R.id.initializeSesionButton);
            connectionStatusText = ((TextView) (findViewById(R.id.connectionStatusText)));
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
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
            ExceptionHandler.handleException(e, this);
        }
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
            ExceptionHandler.handleException(e, this);
        }
    }

    /**
     * Accion a ejecutar cuando se da click en el boton de iniciar.
     *
     * @param v View de la aplicacion.
     */
    private void onInitializeSesionButtOnClick(View v) {
        try {
            if (updateConnectionText()) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            } else {
                this.showMessage(v.getContext().getResources().
                        getText(R.string.noConnectionError).toString());
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    /**
     * Actualiza el label de coneccion en dependencia de si hay o no coneccion con el servidor.
     *
     * @return true si hay coneccion con el servidor, false en cualquier otro caso.
     */
    private boolean updateConnectionText() {
        try {

            boolean resp = new LoadingHandler<Boolean>(this, new LoadingProcess<Boolean>() {
                @Override
                public Boolean process() {
                    return controller.checkConnection();
                }
            }).get();

            if (resp) {
                connectionStatusText.setText(R.string.conexion_succesfull);
                connectionStatusText.setTextColor(Color.GREEN);
                return true;
            } else {
                connectionStatusText.setText(R.string.no_network);
                connectionStatusText.setTextColor(Color.RED);
                return false;
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
            return false;
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
            ExceptionHandler.handleException(e, this);
        }
    }

}
