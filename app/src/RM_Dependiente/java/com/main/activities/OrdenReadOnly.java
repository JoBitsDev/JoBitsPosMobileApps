package com.main.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.main.objetos.Mesa;
import com.main.objetos.ProductoVenta;
import com.main.objetos.ProductovOrden;
import com.main.adapters.adapterMenu;
import com.main.adapters.adapterProductoVOrden;
import com.main.parser.MesaXMlParser;
import com.main.parser.ProductoVordenXMLParser;
import com.main.res;
import com.main.webServerCon.ordenConn;
import com.main.webServerCon.personalConn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import firstdream.rm.BaseActivity;
import firstdream.rm.R;

public class OrdenReadOnly extends BaseActivity {

    OrdenReadOnly o;

    TextView mesa,ordenNo,fecha,dependiente, totalPrincipal, totalSecundaria;


    ListView listaOrden;

    List<ProductovOrden> productosVOrden;

    ProductoVenta lastClickedMenu = null,lastClickedOrden = null;


    ordenConn orden = null;

    adapterMenu listAdapter;

    boolean readOnly;

    static final String  url1 = "http://"+ res.IP +":8080/RM/rest/com.restmanager.seccion",
                         url2 = "http://"+ res.IP +":8080/RM/rest/com.restmanager.productoventa",
                         url3 = "http://"+ res.IP +":8080/RM/rest/com.restmanager.orden/LISTPRODUCTS_";

    public static int entrante,plato_fuerte,postre,liquido;
    public static String menuinfantil_nota = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data  = getIntent().getExtras();

        setContentView(R.layout.activity_orden_readonly);


        initComponents();

        initTab();


        mesa.setText(data.getString("mesa"));
        dependiente.setText(data.getString(String.valueOf(R.string.user)));
        String codOrden = data.getString("codOrden");


        try {

            OrdenReadOnly old = (OrdenReadOnly) getLastNonConfigurationInstance();
            if(old != null){
                orden = old.orden;
                codOrden = orden.getCodOrden();
            }

            if(codOrden!=null){
            orden = new ordenConn(codOrden,mesa.getText().toString(),dependiente.getText().toString());
            fillAct(new ProductoVordenXMLParser(codOrden).fetch(url3+codOrden));
            }
            else{
            orden = new ordenConn(mesa.getText().toString(),dependiente.getText().toString());
                if(!orden.initOrden()){
                    throw new NullPointerException("La cajera debe comenzar el dia " +
                            "de trabajo para acceder a las ordenes");

                }
            }

            ordenNo.setText(orden.getCodOrden());


        } catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(e.getMessage());
            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();

                }
            });
            builder.show();

        }


    }




    private void initTab() {
        TabHost host = (TabHost)findViewById(R.id.tabHost);
        if(host != null){
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Menu");
        spec.setContent(R.id.menu);
        spec.setIndicator("Menu");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Pedido");
        spec.setContent(R.id.orden);
        spec.setIndicator("Pedido");
        host.addTab(spec);}




    }


    @Override
    public Object onRetainNonConfigurationInstance() {
        return this;
    }

    private void fillAct(List<ProductovOrden> orden) {


        adapterProductoVOrden adapter = (adapterProductoVOrden) listaOrden.getAdapter();
        adapter.clear();
        for (ProductovOrden x : orden){
            adapter.increase(x.getProductoVenta(),x.getCantidad(),this.orden);
        }
        updateCost();
    }


    private void initComponents() {
        productosVOrden = new ArrayList<ProductovOrden>();

        mesa = (TextView) findViewById(R.id.labelmesaNo);
        ordenNo = (TextView) findViewById(R.id.labelOrdenNo);
        fecha = (TextView) findViewById(R.id.labelFecha);
        dependiente = (TextView) findViewById(R.id.labelDependiente);
        totalPrincipal = (TextView) findViewById(R.id.labelTotal);
        totalSecundaria = (TextView) findViewById(R.id.labelTotal2);

        listaOrden = (ListView) findViewById(R.id.listaOrden);

        o = this;

        final adapterProductoVOrden l = new adapterProductoVOrden(this,R.id.listaOrden,productosVOrden,true);
        listaOrden.setAdapter(l);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return false;

    }


    private void cederCamarero() {


        final String [] usuarios = new personalConn().
                getUsiariosActivos();
        Arrays.sort(usuarios);


        new AlertDialog.Builder(this).
                setTitle(R.string.seleccioneElUsuarioACeder).
                setSingleChoiceItems(usuarios, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String serverReponse = orden.cederAUsuario(usuarios[which]);
                        if(!serverReponse.equals("1")){
                            new AlertDialog.Builder(getParent()).setMessage(serverReponse);
                            dialog.dismiss();
                        }
                        else{
                            dialog.dismiss();
                            finish();
                        }
                    }
                }).
                setNeutralButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).
                create().
                show();
    }



    private void compartitPedido() {

    }

    public void onAddClick(View v){
        lastClickedMenu = ((ProductovOrden) listaOrden.getAdapter().getItem((Integer) v.getTag())).getProductoVenta();
        addProducto();
    }

    public void onRemoveClick(View v){
     lastClickedOrden = ((ProductovOrden) listaOrden.getAdapter().getItem((Integer) v.getTag())).getProductoVenta();
     removeProducto();
     }

    public void onAdjuntoClick(View v){
        lastClickedOrden = ((ProductovOrden) listaOrden.getAdapter().getItem((Integer) v.getTag())).getProductoVenta();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        final EditText nota = new EditText(this);
        nota.getText().insert(0,(orden.getNota(lastClickedOrden.getPCod())));


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(nota)
                // Add action buttons
                .setPositiveButton(R.string.agregar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addnota(toHTMLString(nota.getText().toString()));
                        //addnota(nota.getText().toString());

                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create();

        builder.show();

     }

     public void onComensalClick(View v){
         lastClickedOrden = ((ProductovOrden) listaOrden.getAdapter().getItem((Integer) v.getTag())).getProductoVenta();

         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         // Get the layout inflater
         LayoutInflater inflater = this.getLayoutInflater();
         final EditText nota = new EditText(this);
         nota.getText().insert(0,(orden.getComensal(lastClickedOrden.getPCod())));


         // Inflate and set the layout for the dialog
         // Pass null as the parent view because its going in the dialog layout
         builder.setView(nota)
                 // Add action buttons
                 .setPositiveButton(R.string.agregar, new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int id) {
                         addComensal(nota.getText().toString());

                     }
                 })
                 .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {
                         dialog.cancel();
                     }
                 });
         builder.create();

         builder.show();

     }

    private void addComensal(String s) {
        orden.addComensal(lastClickedOrden.getPCod(),s);
        Toast.makeText(this,lastClickedOrden.getNombre() + " Comensal Agregado",Toast.LENGTH_SHORT).show();


    }

    public void MoverAMesa(){//TODO: terminar esto
        final List<Mesa> mesas = new MesaXMlParser().
                fetch("http://"+ res.IP +":8080/RM/rest/com.restmanager.mesa/MOSTRARVACIAS");
        Collections.sort(mesas);
        String [] codMesas = new String[mesas.size()];
        for (int i = 0; i < mesas.size(); i++ ){
            codMesas[i] = mesas.get(i).getCodMesa();
        }

        new AlertDialog.Builder(this).
                setTitle(R.string.seleccioneLaMesaAMover).
                setSingleChoiceItems(codMesas, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String serverReponse = orden.moverAMesa(mesas.get(which).getCodMesa());
                if(!serverReponse.equals("1")){
                    new AlertDialog.Builder(getParent()).setMessage(serverReponse);
                    dialog.dismiss();
                }
                else{
                    dialog.dismiss();
                    finish();
                }
            }
        }).
                setNeutralButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).
                create().
                show();




    }

    public void onLimpiarClick(View v){
        orden.removeAllProducts();
        ((adapterProductoVOrden) listaOrden.getAdapter()).clear();
         updateCost();

    }

    private void updateCost() {

        float tot = 0;
        for (ProductovOrden x : productosVOrden){
            tot += x.getProductoVenta().getPrecioVenta()*x.getProductoVenta().getCantidad();
        }
        if(res.MONEDA_PRINCIPAL.equals("MN")){
            totalPrincipal.setText(String.format("%.2f " + res.MONEDA_PRINCIPAL,tot));
            totalSecundaria.setText(String.format("%.2f "+ res.MONEDA_SECUNDARIA,tot/24));}
        else{
            totalPrincipal.setText(String.format("%.2f " + res.MONEDA_PRINCIPAL,tot));
            totalSecundaria.setText(String.format("%.2f "+ res.MONEDA_SECUNDARIA,tot*24));
        }


    }

    private void onBuscarClick(View v){


    }

    public void addProducto(){
        //fillAct(new ProductoVordenXMLParser(orden.getCodOrden()).fetch(url3+orden.getCodOrden()));
        adapterProductoVOrden adapter = (adapterProductoVOrden) listaOrden.getAdapter();
        if(lastClickedMenu != null){
            if(orden.addProducto(lastClickedMenu.getPCod())){
                adapter.increase(lastClickedMenu,orden);
                updateCost();
            Toast.makeText(this,lastClickedMenu.getNombre() + " agregado al pedido.",Toast.LENGTH_SHORT).show();}
            else
                Toast.makeText(this,R.string.errorAlAutenticar,Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,R.string.noItemSeleccionado,Toast.LENGTH_SHORT).show();


    }

    private void removeProducto() {

        //fillAct(new ProductoVordenXMLParser(orden.getCodOrden()).fetch(url3+orden.getCodOrden()));
        adapterProductoVOrden adapter = (adapterProductoVOrden) listaOrden.getAdapter();

        if(lastClickedOrden != null){

            if(orden.removeProducto(lastClickedOrden.getPCod())){
                adapter.decrease(lastClickedOrden);
                updateCost();
                Toast.makeText(this,lastClickedOrden.getNombre() + " removido del pedido.",Toast.LENGTH_SHORT).show();}
            else
                Toast.makeText(this,R.string.errorAlBorrar,Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,R.string.noItemSeleccionado,Toast.LENGTH_SHORT).show();

    }



    private void enviarACocina(){
        orden.sendToKitchen();
        new AlertDialog.Builder(this).setMessage(R.string.enviarAcocina).create().show();

    }

    private void addnota(String nota){
        orden.addNota(lastClickedOrden.getPCod(),nota);
        Toast.makeText(this,lastClickedOrden.getNombre() + " Nota Agregada",Toast.LENGTH_SHORT).show();
    }

    private String toHTMLString(String s) {
       return s.replace(' ','-');
    }





}

