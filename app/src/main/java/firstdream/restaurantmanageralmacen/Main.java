package firstdream.restaurantmanageralmacen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.main.service.ReceiverService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import firstdream.exception.ExceptionHandler;


public class Main extends Activity {


    private final String URLCONN= "http://" + com.main.res.IP + ":8080/RM";

    private TextView text;

    private Button notif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);

        text = ((TextView)(findViewById(R.id.TextConnexion)));

        updateConnLabel();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateConnLabel();
                if(text.getText().toString().equals(v.getContext().getResources().
                        getText(R.string.no_network).toString()))
                {
                    ExceptionHandler.showMessageInAlert(
                            new IOException(v.getContext().getResources().
                                    getText(R.string.exNoServerConn).toString()),v.getContext());
                }
                else{
                    startActivity(new Intent(Main.this, Login.class));
            }}});




    }


    public void configuracionClick(View view){

    }


    private void updateConnLabel() {
        if(checkConn()){
            text.setText(R.string.conexion_succesfull);
            text.setTextColor(Color.GREEN);


        }else{
            text.setText(R.string.no_network);
            text.setTextColor(Color.RED);
        }

    }

    private boolean checkConn() {
        try {
            check c =new check();
            c.execute(URLCONN);
            return c.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

     protected class check extends AsyncTask<String, Void, Boolean> {


        @Override
        protected Boolean doInBackground(String... uri) {
            URL url = null;
            try {
                url = new URL(uri[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoInput(true);
                // Starts the query
                con.setConnectTimeout(400);
                con.connect();
                return true;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            }//TODO: arreglar esto que no pincha bien

    }


    public void startService(View view){
        notif = (Button)findViewById(R.id.buttonNotificacion);
        if(notif.getText().equals("Activar Notificaciones")){
            startService(new Intent(Main.this, ReceiverService.class));
            notif.setText("Desactivar Notificationes");

        }
        else{
            stopService(new Intent(Main.this, ReceiverService.class));
            notif.setText("Activar Notificationes");

        }

    }


}
