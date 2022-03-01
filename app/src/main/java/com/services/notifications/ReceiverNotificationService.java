package com.services.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.provider.Settings;

import com.activities.R;
import com.utils.EnvironmentVariables;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Capa: Services
 * Servicio de notificaciones entre las aplicaciones.
 *
 * @extends Service ya que es un servicio.
 */
public class ReceiverNotificationService extends Service {

    /**
     * Socket con el servidor.
     */
    private ServerSocket server; // server socket

    /**
     * Socket con el cliente.
     */
    private Socket connection; // connection to client

    /**
     * Output stream to client.
     */
    private ObjectOutputStream output; // output stream to client

    /**
     * Input stream from client.
     */
    private ObjectInputStream input; // input stream from client

    /**
     * Counter of number of connections.
     */
    private int counter = 1; // counter of number of connections

    /**
     * Mensajes qa mostrar.
     */
    private String notificationTitle, notificationMessage, notificationDescription;

    /**
     * Hilo del listener para comunicarse en paralelo.
     */
    private Thread backgroundListener = new Thread(new Runnable() {
        @Override
        public void run() {
            startSocketAndListnen();
        }
    });

    public ReceiverNotificationService() {

    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        backgroundListener.start();
    }

    @Override
    public void onDestroy() {
        try {
            server.close();
            backgroundListener.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inicia los sockets y sockets.
     */
    private void startSocketAndListnen() {
        runServer();
    }

    /**
     * Lo que se conecta con el socket del servidor.
     */
    public void runServer() {
        try {
            server = new ServerSocket(EnvironmentVariables.SOCKET_PORT); // create ServerSocket
            while (true) {
                try {
                    waitForConnection(); // wait for a connection
                    getStreams(); // getProductoVentaOrden input & output streams
                    processConnection(); // process connection
                } // end try
                catch (EOFException eofException) {
                    //TODO: exception no atrapada
                } // end catch

            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } // end catch
    } // end method runServer


    /**
     * Espera por coneccion
     *
     * @throws IOException Si hay error
     */
    private void waitForConnection() throws IOException {
        connection = server.accept();
    } // end method waitForConnection


    /**
     * Get streams to send and receive data
     *
     * @throws IOException Si hay error
     */
    private void getStreams() throws IOException {
        // set up output stream for objects
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush(); // flush output buffer to send header information
        // set up input stream for objects
        input = new ObjectInputStream(connection.getInputStream());

    }

    /**
     * Procesa la coneccion con el server.
     *
     * @throws IOException Si hay error.
     */
    private void processConnection() throws IOException {
        String message = "";
        do {
            try {
                message = (String) input.readObject();
                notificationTitle = message.split("_")[0];
                notificationMessage = message.split("_")[1];
                notificationDescription = message.split("_")[2];

                fireNotification();
            } catch (ClassNotFoundException e) {
                // closeConnection();//TODO: excepcion not catched
            }
        } while (!message.equals("CLIENT>>> TERMINATE"));

    }

    /**
     * Cierra la coneccion con el servidor.
     */
    private void closeConnection() {
        try {
            output.close();
            input.close();
            connection.close();
        } catch (IOException e) {
            //TODO: excepcion no atrapada

        }
    }

    /**
     * Crea las notificaciones.
     */
    private void fireNotification() {

        Notification builder = new Notification.Builder(this)
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                .setSubText(notificationDescription)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setSmallIcon(R.drawable.logo_app)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo_app))
                .build();
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify((int) Math.random() * 100, builder);
    }

}
