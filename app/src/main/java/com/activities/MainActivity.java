package com.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.controllers.MainController;
import com.services.notifications.ReceiverNotificationService;

import java.util.concurrent.ExecutionException;


public class MainActivity extends BaseActivity {

    private MainController controller;

    private TextView connectionStatusText;
    private Button notificationButton;
    private Button initializeSesionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVarialbes();
        addListeners();

        updateConnectionText();
    }

    @Override
    void initVarialbes() {
        controller = new MainController();

        notificationButton = (Button) findViewById(R.id.notificationButton);
        initializeSesionButton = (Button) findViewById(R.id.initializeSesionButton);
        connectionStatusText = ((TextView) (findViewById(R.id.connectionStatusText)));
    }

    @Override
    void addListeners() {
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
    }

    private void onNotificationButtonOnClick(View v) {
        startService(v);
    }

    private void onInitializeSesionButtOnClick(View v) {
        if (!updateConnectionText()) {
            super.showMessage(v.getContext().getResources().
                    getText(R.string.exNoServerConn).toString());
        } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    private boolean updateConnectionText() {
        try {
            controller.checkConnection();
            connectionStatusText.setText(R.string.conexion_succesfull);
            connectionStatusText.setTextColor(Color.GREEN);

            return true;
        } catch (Exception e) {
            connectionStatusText.setText(R.string.no_network);
            connectionStatusText.setTextColor(Color.RED);
        }

        return false;
    }

    public void startService(View view) {
        if (notificationButton.getText().equals("Activar Notificaciones")) {
            startService(new Intent(MainActivity.this, ReceiverNotificationService.class));
            notificationButton.setText("Desactivar Notificationes");
        } else {
            stopService(new Intent(MainActivity.this, ReceiverNotificationService.class));
            notificationButton.setText("Activar Notificationes");
        }
    }

}
