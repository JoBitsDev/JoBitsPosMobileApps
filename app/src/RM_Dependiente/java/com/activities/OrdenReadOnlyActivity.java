package com.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import com.services.models.ProductoVentaOrdenModel;
import com.utils.adapters.ProductoVentaOrdenAdapter;
import com.utils.adapters.ProductoVentaOrdenReadOnlyAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrdenReadOnlyActivity extends BaseActivity {

    TextView mesa, ordenNo, fecha, dependiente, totalPrincipal, totalSecundaria;
    ListView listaOrden;
    List<ProductoVentaOrdenModel> productosVOrden;
    ProductoVentaOrdenReadOnlyAdapter readOnlyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orden_readonly);
        initVarialbes();
        setAdapters();
        addListeners();
    }

    private void initTab() {
        TabHost host = (TabHost) findViewById(R.id.tabHost);
        if (host != null) {
            host.setup();
            //Tab 1
            TabHost.TabSpec spec = host.newTabSpec("Menu");
            spec.setContent(R.id.menu);
            spec.setIndicator("Menu");
            host.addTab(spec);
            //Tab 2
            spec = host.newTabSpec("Pedido");
            spec.setContent(R.id.ordenModel);
            spec.setIndicator("Pedido");
            host.addTab(spec);
        }
    }

    @Override
    protected void initVarialbes() {
        mesa = (TextView) findViewById(R.id.mesaNoLabel);
        ordenNo = (TextView) findViewById(R.id.ordenNoLabel);
        fecha = (TextView) findViewById(R.id.labelFecha);
        dependiente = (TextView) findViewById(R.id.dependienteLabel);
        totalPrincipal = (TextView) findViewById(R.id.totalPrincipalLabel);
        totalSecundaria = (TextView) findViewById(R.id.totalSecundariaLabel);
        listaOrden = (ListView) findViewById(R.id.listaOrdenReadOnly);

        Bundle bundleExtra = getIntent().getExtras();
        mesa.setText(bundleExtra.getString("mesaNoLabel"));//set el No de la mesa
        dependiente.setText(bundleExtra.getString(String.valueOf(R.string.user)));//set el label con el dependiente

        productosVOrden = new ArrayList<ProductoVentaOrdenModel>();
        readOnlyAdapter = new ProductoVentaOrdenReadOnlyAdapter(getApplicationContext(),R.id.listaOrdenReadOnly,productosVOrden);

        initTab();
    }

    @Override
    protected void addListeners() {

    }

    @Override
    protected void setAdapters() {
        listaOrden.setAdapter(readOnlyAdapter);
    }
}
