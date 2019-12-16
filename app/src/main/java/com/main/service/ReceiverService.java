package com.main.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.provider.Settings;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import firstdream.rm.R;

public class ReceiverService extends Service {

    private ServerSocket server; // server socket
    private Socket connection; // connection to client
    private ObjectOutputStream output; // output stream to client
    private ObjectInputStream input; // input stream from client
    private int counter = 1; // counter of number of connections
    private String notificationTitle,notificationMessage,notificationDescription;
    private Thread backgroundListener = new Thread(new Runnable() {
        @Override
        public void run() {
            startSocketAndListnen();
        }
    });

    public ReceiverService() {

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
            backgroundListener.destroy();


    }

    private void startSocketAndListnen() {
runServer();
    }

    public void runServer() {
        try {
            server = new ServerSocket(8888); // create ServerSocket
            while (true) {
                try {
                    waitForConnection(); // wait for a connection
                    getStreams(); // get input & output streams
                    processConnection(); // process connection
                } // end try
                catch (EOFException eofException) {
                    //TODO: exception no atrapada
                } // end catch
                finally {
                    //closeConnection(); // close connection
                    // ++counter;
                } // end finally

            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } // end catch
    } // end method runServer

    private void waitForConnection() throws IOException {

        connection = server.accept();


    } // end method waitForConnection

    // get streams to send and receive data
    private void getStreams() throws IOException {
        // set up output stream for objects
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush(); // flush output buffer to send header information
        // set up input stream for objects
        input = new ObjectInputStream(connection.getInputStream());

    }

    private void processConnection() throws IOException {
        String message = "" ;
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

    private void closeConnection() {
        try {
            output.close();
            input.close();
            connection.close();
        } catch (IOException e) {
            //TODO: excepcion no atrapada

        }
    }

    private void fireNotification(){

        Notification builder = new Notification.Builder(this)
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                .setSubText(notificationDescription)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setSmallIcon(R.drawable.logo_android)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo_android))
                .build();
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify((int)Math.random()*100,builder);



    }

}
