package com.services.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

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

    private static final String CHANNEL_ID = "org.jobits.pos";
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
    private String notificationTitle = "Notificacion", notificationMessage = "Notificacion", notificationDescription = "Noficacion";
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


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "JoBits POS";
            String description = "POS Software";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        backgroundListener.start();
        createNotificationChannel();
        fireNotification(
                "JoBits POS",
                "Notificaciones Activadas",
                "Las notificaciones fueron activadas");

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

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                .setSubText(notificationDescription)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setSmallIcon(R.drawable.logo_app)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo_app));

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify((int) Math.random() * 100, builder.build());
    }

    /**
     * Crea las notificaciones.
     */
    private void fireNotification(String notificationTitle, String notificationDescription, String notificationMessage) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                .setSubText(notificationDescription)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setSmallIcon(R.drawable.logo_app)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo_app));

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify((int) Math.random() * 100, builder.build());
    }

}
