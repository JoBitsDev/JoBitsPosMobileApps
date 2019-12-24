package com.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.controllers.MainController;
import com.services.notifications.ReceiverNotificationService;
import com.utils.exception.ExceptionHandler;


public class MainActivity extends BaseActivity {

    private MainController controller;

    private TextView connectionStatusText;
    private Button notificationButton;
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

    private void onNotificationButtonOnClick(View v) {
        try {
            startService(v);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

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

    private boolean updateConnectionText() {
        try {
            if (controller.checkConnection()) {
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
