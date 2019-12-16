package com.main.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextClock;
import android.widget.TextView;

import com.main.ProductovOrden;
import com.main.adapters.adapterMenu;
import com.main.webServerCon.cocinaConn;
import com.main.webServerCon.cartaConn;
import com.main.webServerCon.notificacionConn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import firstdream.rm.BaseActivity;
import firstdream.rm.R;


public class PantallaPrincipal extends BaseActivity {

    String user, cocinaTrabajo;
    TextClock clock;
    TextView labelRestName,labelCocinaName,labelUsuario;
    ExpandableListView lista;
    List<ProductovOrden> pedidos = new ArrayList<ProductovOrden>();
    PantallaPrincipal l;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);

        labelRestName = (TextView) findViewById(R.id.textViewNombreRest);
        labelCocinaName = (TextView) findViewById(R.id.textViewNombreCocina);
        labelUsuario = (TextView) findViewById(R.id.labelUsuario);
        lista = (ExpandableListView) findViewById(R.id.listaMesas);



        if (labelRestName != null) {
            labelRestName.setText(new cartaConn().getNombreRest());
        }

        l = this;




        user = getIntent().getExtras().getString(String.valueOf(R.string.user));
        cocinaTrabajo = getIntent().getExtras().getString(String.valueOf(R.string.cocina_cod));

        labelUsuario.setText(user);


        if(cocinaTrabajo == null){
        cocinaTrabajo = "-";
        }
            labelCocinaName.setText(cocinaTrabajo);

        configurarTabla();

        findViewById(R.id.buttonCambiarArea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarCocina(v);
            }
        });
        findViewById(R.id.buttonrefresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refrescar(v);
            }
        });

        lista.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String ret = new notificacionConn(cocinaTrabajo).notificar((ProductovOrden)
                        (lista.getExpandableListAdapter()).getChild(groupPosition,childPosition));
                new AlertDialog.Builder(l).
                        setMessage(ret).
                        setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                configurarTabla();
                            }
                        }).create().show();
                return true;
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
        //getMenuInflater().inflate(R.menu.menu_activity_principal, menu);
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

    public void onAddClick(View view) {
        String ret = new notificacionConn(cocinaTrabajo).notificar((ProductovOrden)
                lista.getAdapter().getItem((Integer) view.getTag()));
        new AlertDialog.Builder(this).
                setMessage(ret).
                setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        configurarTabla();
                    }
                }).create().show();

    }

    public void seleccionarCocina(View view){
        final String[] nombreCocinas = new cocinaConn().getCocinasNames();
        Arrays.sort(nombreCocinas);
       if(cocinaTrabajo == null){
           cocinaTrabajo = nombreCocinas[0];
       }
        new AlertDialog.Builder(this).
                setTitle(R.string.seleccionar_cocina).
                setSingleChoiceItems(nombreCocinas, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cocinaTrabajo = nombreCocinas[which];
                        dialog.dismiss();
                        configurarTabla();
                        labelCocinaName.setText(cocinaTrabajo);
                    }
                }).
                create().
                show();

    }

    public void configurarTabla() {
        showProgressDialog();
        lista.post(new Runnable() {
            @Override
            public void run() {
                lista.setAdapter(getData());
                hideProgressDialog();
            }
        });


    }


    private adapterMenu getData() {
        if (!cocinaTrabajo.equals("-")){
        pedidos = new notificacionConn(cocinaTrabajo).fetchPendingOrders();
        }
        if(pedidos == null){
            pedidos = new ArrayList<ProductovOrden>();
        }
        adapterMenu adaptador = new adapterMenu(l, pedidos);
        return adaptador;

    }

    public void refrescar(View view){
        configurarTabla();
    }

}
