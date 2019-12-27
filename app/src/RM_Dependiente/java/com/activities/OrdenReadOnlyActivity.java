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
        mesa.setText(bundleExtra.getString(String.valueOf(R.string.mesa)));//set el No de la mesa
        dependiente.setText(bundleExtra.getString(String.valueOf(R.string.user)));//set el label con el dependiente
        ordenNo.setText(bundleExtra.getString(String.valueOf(R.string.cod_Orden)));//set el label de la orden

        productosVOrden = new ArrayList<ProductoVentaOrdenModel>();
        readOnlyAdapter = new ProductoVentaOrdenReadOnlyAdapter(getApplicationContext(), R.id.listaOrdenReadOnly, productosVOrden);
    }

    @Override
    protected void addListeners() {

    }

    @Override
    protected void setAdapters() {
        listaOrden.setAdapter(readOnlyAdapter);
    }
}
