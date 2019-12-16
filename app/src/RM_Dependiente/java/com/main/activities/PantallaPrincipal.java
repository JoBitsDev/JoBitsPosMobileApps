package com.main.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import com.main.objetos.Mesa;
import com.main.adapters.adapterMesa;
import com.main.parser.MesaXMlParser;
import com.main.res;
import com.main.webServerCon.cartaConn;
import com.main.webServerCon.mesaConn;
import com.main.webServerCon.ordenConn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import firstdream.exception.ExceptionHandler;
import firstdream.exception.NoExistingException;
import firstdream.rm.BaseActivity;
import firstdream.rm.R;


public class PantallaPrincipal extends BaseActivity {

    String urlMesas = "http://" + com.main.res.IP + ":8080/RM/rest/com.restmanager.mesa",

    user;
    TextClock clock;
    TextView labelRestName;
    ListView lista;
    List<Mesa> mesas = new ArrayList<Mesa>();
    PantallaPrincipal l;
    private boolean readOnly = false;
    private String selectedArea = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);

        labelRestName = (TextView) findViewById(R.id.textViewNombreRest);

        if (labelRestName != null) {
            labelRestName.setText(new cartaConn().getNombreRest());
        }
        l = this;

        lista = (ListView) findViewById(R.id.listaMesas);
        user = getIntent().getExtras().getString(String.valueOf(R.string.user));
        clock = (TextClock) findViewById(R.id.textClock);
        ((TextView) findViewById(R.id.textviewusuario)).setText(user);
        configurarTabla();
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                configurarTabla();
                lista.setAdapter(getData());
                continuar(mesas.get(position));
                return true;
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarArea(v);
            }
        });

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

    private void continuar(Mesa m) {
        try {

            ordenConn orden;
            final Bundle data = new Bundle();
            data.putString(String.valueOf(R.string.user), user);
            data.putString("mesa", m.getCodMesa());

            orden = new ordenConn(m.getCodMesa(), user);
            if (!m.getEstado().equals(res.ESTADO_MESA_VACIA)) {
                String cod_orden = m.getEstado().split(" ")[0];
                orden.setCodOrden(cod_orden);
                if(!orden.validate()){
                    throw new NoExistingException("La orden a acceder ya no se encuentra abierta",this);
                }
                data.putString("codOrden", cod_orden);

                if (!user.equals(m.getEstado().split(" ")[1])) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("La mesa que quiere acceder " +
                            "la esta atendiendo otro camarero");
                    builder.setNegativeButton("No entrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("Entrar en modo solo lectura", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            readOnly = true;
                            execIntent(data);
                        }
                    });
                    builder.show();

                } else {
                    execIntent(data);
                }
            } else {
                execIntent(data);
            }


        } catch (Exception e) {
            ExceptionHandler.showMessageInAlert(e, this);
        }


    }

    public void cambiarArea(View view) {


    final String[] areas = new mesaConn(user, null).getAreasName();
        new AlertDialog.Builder(this).

    setTitle(R.string.seleccionararea).

    setSingleChoiceItems(areas, 1,new DialogInterface.OnClickListener() {
        @Override
        public void onClick (DialogInterface dialog,int which){
            selectedArea = areas[which];
            dialog.dismiss();
            configurarTabla();
        }
    }).

    create().

    show();



}
    private void execIntent(Bundle data) {
        Intent launch;
        if (readOnly) {
            launch = new Intent(this, OrdenReadOnly.class);
            launch.putExtras(data);
            startActivity(launch);
        } else {
            launch = new Intent(this, Orden.class);
            launch.putExtras(data);
            startActivity(launch);
        }
    }


    public void configurarTabla(){
        showProgressDialog();
        lista.post(new Runnable() {
            @Override
            public void run() {
            lista.setAdapter(getData());
                hideProgressDialog();
            }
        });



    }

    private adapterMesa getData(){
        if(selectedArea != null){
            mesas = new MesaXMlParser().fetch(urlMesas + "/AREA_"+ selectedArea);
        }else{
        mesas = new MesaXMlParser().fetch(urlMesas);
        }
        Collections.sort(mesas);
        adapterMesa adaptador = new adapterMesa(l,R.id.listaMesas,mesas,user);
        return adaptador;

    }




}
