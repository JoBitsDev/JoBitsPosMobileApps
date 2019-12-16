package com.main.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.main.objetos.Mesa;
import com.main.objetos.ProductoVenta;
import com.main.objetos.ProductovOrden;
import com.main.objetos.Seccion;
import com.main.adapters.adapterMenu;
import com.main.adapters.adapterProductoVOrden;
import com.main.parser.MesaXMlParser;
import com.main.parser.ProductoVentaXMlParser;
import com.main.parser.ProductoVordenXMLParser;
import com.main.parser.SeccionXMlParser;
import com.main.res;
import com.main.webServerCon.ordenConn;
import com.main.webServerCon.personalConn;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import firstdream.exception.ExceptionHandler;
import firstdream.rm.BaseActivity;
import firstdream.rm.R;

public class Orden extends BaseActivity {

    Orden o;

    Button cerrarOrden,despacharaCocina;

    TextView mesa,ordenNo,dependiente, totalPrincipal, totalSecundaria;

    EditText searchText;

    ExpandableListView menu ;

    ListView listaOrden;

    CheckBox check;

    Spinner s;

    List<Seccion> secciones;
    List<ProductoVenta> productos;
    List<ProductovOrden> productosVOrden;

    ProductoVenta lastClickedMenu = null;
    ProductovOrden lastClickedOrden = null;


    ordenConn orden = null;

    adapterMenu listAdapter;

    boolean readOnly;

    static final String  url1 = "http://"+com.main.res.IP +":8080/RM/rest/com.restmanager.seccion",
                         url2 = "http://"+com.main.res.IP +":8080/RM/rest/com.restmanager.productoventa/PRODUCTS_",
                         url3 = "http://"+com.main.res.IP +":8080/RM/rest/com.restmanager.orden/LISTPRODUCTS_";

    public static int entrante,plato_fuerte,postre,liquido;
    public static String menuinfantil_nota = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_orden);

        initComponents(getIntent().getExtras());

        initTab();

        Bundle data  = getIntent().getExtras();
        mesa.setText(data.getString("mesa"));
        dependiente.setText(data.getString(String.valueOf(R.string.user)));
        String codOrden = data.getString("codOrden");


        try {

            Orden old = (Orden) getLastNonConfigurationInstance();
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
            ExceptionHandler.showMessageInAlert(e,this);
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

    private void fillAct(final List<ProductovOrden> orden) {
        final adapterProductoVOrden adapter = (adapterProductoVOrden) listaOrden.getAdapter();
        listaOrden.post(new Runnable() {
            @Override
            public void run() {
        adapter.clear();
        adapter.setObjects(orden);
        updateCost();

            }
        });
    }

    private void generarMenu(String cod_mesa){
        secciones =  new SeccionXMlParser().fetch(url1);
        productos = new ProductoVentaXMlParser().fetch(url2 + cod_mesa);
        //Collections.sort(productos);
        Collections.sort(secciones);
        Collections.sort(productos);


        for (ProductoVenta x: productos){
            for (Seccion y : secciones){
                if(x.getSeccionnombreSeccion().equals(y.getNombreSeccion())){
                    y.addProducto(x);
                    break;
                }
            }
        }

        for (int i = 0;i < secciones.size();){
            if(secciones.get(i).getProductos().isEmpty()){
                secciones.remove(i);
            }
            else{
                i++;
            }
        }
    }

    private void initComponents(Bundle extras) {
        productosVOrden = new ArrayList<ProductovOrden>();

        mesa = (TextView) findViewById(R.id.labelmesaNo);
        ordenNo = (TextView) findViewById(R.id.labelOrdenNo);
        dependiente = (TextView) findViewById(R.id.labelDependiente);
        totalPrincipal = (TextView) findViewById(R.id.labelTotal);
        totalSecundaria = (TextView) findViewById(R.id.labelTotal2);


        check = (CheckBox) findViewById(R.id.checkBoxDeLaCasa);

        menu = (ExpandableListView) findViewById(R.id.expandableListView);
        listaOrden = (ListView) findViewById(R.id.listaOrden);

        o = this;

        cerrarOrden = (Button) findViewById(R.id.buttonCerrarOrden);
        despacharaCocina = (Button) findViewById(R.id.buttondespacharCocina);

        searchText = (EditText) findViewById(R.id.searchText);

        if(cerrarOrden != null){
        cerrarOrden.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               cerrarOrden();
                return true;
            }
        });
        }

        if(despacharaCocina != null){
        despacharaCocina.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                enviarACocina();
                return true;
            }
        });
        }


        generarMenu(extras.getString("mesa"));

        listAdapter = new adapterMenu(this, secciones);
        menu.setAdapter(listAdapter);
        menu.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                lastClickedMenu = (ProductoVenta)
                        listAdapter.getChild(groupPosition, childPosition);
                 addProducto();
                 searchText.setText("");
                 return true;
            }
        });
    menu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
            addProductoFromMenu(v,position);
            return true;
        }
    });


        final adapterProductoVOrden l = new adapterProductoVOrden(this, R.id.listaOrden, productosVOrden, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final View listview = v;
                final EditText input = new EditText(v.getContext());
                input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                        new AlertDialog.Builder(v.getContext()).
                                setView(input).
                                setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setPositiveButton(R.string.agregar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                addProducto(listview,Float.parseFloat(input.getText().toString()));
                            }
                        }).
                                create().
                                show();
                        return true;
                }
        });
        listaOrden.setAdapter(l);


        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
                listAdapter.getFilter().filter(s.toString());}

            }

            @Override
            public void afterTextChanged(Editable s) {
               
            }
        });









    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_orden, menu);



        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.moverAMesa: MoverAMesa();break;
            case R.id.enviarCocina:enviarACocina();break;
            case R.id.cerrarOrden:cerrarOrden();break;
            case R.id.compartirPedido:compartitPedido();break;
            case R.id.cederACamarero: cederCamarero();break;
            case R.id.action_settings: return true;


            default : super.onOptionsItemSelected(item); break;
        }



        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void cederCamarero() {


        final String [] usuarios = new personalConn().
                getUsiariosActivos();
        Arrays.sort(usuarios);

if(usuarios.length > 0){
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
                show();}else{
    Toast.makeText(this,"No existe ningun usuario trabajando para ceder orden",Toast.LENGTH_LONG).show();
                }
    }


    private void compartitPedido() {

    }

    public void onAddClick(View v){
        lastClickedMenu = ((ProductovOrden) listaOrden.getAdapter().getItem((Integer) v.getTag())).getProductoVenta();
        addProducto();
    }

    public void onRemoveClick(View v){
     lastClickedOrden = ((ProductovOrden) listaOrden.getAdapter().getItem((Integer) v.getTag()));
     removeProducto();
     }

    public void onAdjuntoClick(View v){
        lastClickedOrden = ((ProductovOrden) listaOrden.getAdapter().getItem((Integer) v.getTag()));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        final EditText nota = new EditText(this);
        nota.getText().insert(0,(orden.getNota(lastClickedOrden.getProductoVenta().getPCod())));


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
         lastClickedOrden = ((ProductovOrden) listaOrden.getAdapter().getItem((Integer) v.getTag()));

         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         // Get the layout inflater
         LayoutInflater inflater = this.getLayoutInflater();
         final EditText nota = new EditText(this);
         nota.getText().insert(0,(orden.getComensal(lastClickedOrden.getProductoVenta().getPCod())));


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
        orden.addComensal(lastClickedOrden.getProductoVenta().getPCod(),s);
        Toast.makeText(this,lastClickedOrden.getProductoVenta().getNombre() + " Comensal Agregado",Toast.LENGTH_SHORT).show();


    }

    public void MoverAMesa(){//TODO: terminar esto
        final List<Mesa> mesas = new MesaXMlParser().
                fetch("http://"+com.main.res.IP +":8080/RM/rest/com.restmanager.mesa/MOSTRARVACIAS");
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
            tot += x.getProductoVenta().getPrecioVenta() * x.getCantidad();
        }
        if(res.MONEDA_PRINCIPAL.equals("MN")){
            totalPrincipal.setText(String.format("%.2f " + res.MONEDA_PRINCIPAL,tot));
            totalSecundaria.setText(String.format("%.2f "+ res.MONEDA_SECUNDARIA,tot/res.conversion));}
        else{
            totalPrincipal.setText(String.format("%.2f " + res.MONEDA_PRINCIPAL,tot));
            totalSecundaria.setText(String.format("%.2f "+ res.MONEDA_SECUNDARIA,tot*res.conversion));
        }


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

    public void addProducto(View v,float cantidad){
        //fillAct(new ProductoVordenXMLParser(orden.getCodOrden()).fetch(url3+orden.getCodOrden()));
        adapterProductoVOrden adapter = (adapterProductoVOrden) listaOrden.getAdapter();
        lastClickedMenu = ((ProductovOrden) listaOrden.getAdapter().getItem((Integer) v.getTag())).getProductoVenta();
        if(lastClickedMenu != null){
            if(orden.addProducto(lastClickedMenu.getPCod(),cantidad)){
                adapter.increase(lastClickedMenu,cantidad,orden);
                updateCost();
                Toast.makeText(this,lastClickedMenu.getNombre() + " agregado al pedido.",Toast.LENGTH_SHORT).show();}
            else
                Toast.makeText(this,R.string.errorAlAutenticar,Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,R.string.noItemSeleccionado,Toast.LENGTH_SHORT).show();


    }

    public void addProductoFromMenu(final View v,int position) {
        long packedPosition = menu.getExpandableListPosition(position);
        if (ExpandableListView.getPackedPositionType(packedPosition) ==
                ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            // get item ID's
            int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
            int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);
            lastClickedMenu = (ProductoVenta)
                    listAdapter.getChild(groupPosition, childPosition);
            final EditText input = new EditText(v.getContext());
            input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            new AlertDialog.Builder(v.getContext()).
                    setView(input).
                    setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton(R.string.agregar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (lastClickedMenu != null) {
                        float f = Float.parseFloat(input.getText().toString());
                        if (orden.addProducto(lastClickedMenu.getPCod(), f)) {
                            adapterProductoVOrden adapter = (adapterProductoVOrden) listaOrden.getAdapter();
                            adapter.increase(lastClickedMenu, f, orden);
                            updateCost();
                            Toast.makeText(v.getContext(), lastClickedMenu.getNombre() + " agregado al pedido.", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(v.getContext(), R.string.errorAlAutenticar, Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(v.getContext(), R.string.noItemSeleccionado, Toast.LENGTH_SHORT).show();
                }
            }).
                    create().
                    show();

        }
    }




    private void removeProducto() {

        //fillAct(new ProductoVordenXMLParser(orden.getCodOrden()).fetch(url3+orden.getCodOrden()));
        adapterProductoVOrden adapter = (adapterProductoVOrden) listaOrden.getAdapter();

        if(lastClickedOrden != null){
            if(lastClickedOrden.getCantidad() > lastClickedOrden.getEnviadosACocina()){
            if(orden.removeProducto(lastClickedOrden.getProductoVenta().getPCod())){
                adapter.decrease(lastClickedOrden.getProductoVenta());
                updateCost();
                Toast.makeText(this,lastClickedOrden.getProductoVenta().getNombre() + " removido del pedido.",Toast.LENGTH_SHORT).show();}
            else
                Toast.makeText(this,R.string.errorAlBorrar,Toast.LENGTH_SHORT).show();
        }else{
            showMessage("Para eliminar un producto enviado a cocina \n necesita autorizacion.");
            }


    }else
            Toast.makeText(this,R.string.noItemSeleccionado,Toast.LENGTH_SHORT).show();

    }

    private void cerrarOrden(){
        if(orden.finishOrden(check.isChecked())){
        new AlertDialog.Builder(this).
                setMessage(R.string.cerrarOrden).
                setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                }).create().show();}
        else{
            new AlertDialog.Builder(this).setMessage(R.string.error_al_cerrar_falta_enviar_cocina).
                    setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    dialog.dismiss();
                }
            }).create().show();
        }
    }

    private void enviarACocina(){
        orden.sendToKitchen();
        fillAct(new ProductoVordenXMLParser(orden.getCodOrden()).fetch(url3 + orden.getCodOrden()));
        new AlertDialog.Builder(this).setMessage(R.string.enviarAcocina).create().show();

    }

    private void addnota(String nota){
        orden.addNota(lastClickedOrden.getProductoVenta().getPCod(),nota);
        Toast.makeText(this,lastClickedOrden.getProductoVenta().getNombre() + " Nota Agregada",Toast.LENGTH_SHORT).show();
    }

    private String toHTMLString(String s) {
       return s.replace(' ','-');
    }



}

