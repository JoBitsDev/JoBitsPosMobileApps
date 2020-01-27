package com.activities;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.controllers.OrdenController;
import com.services.models.ProductoVentaOrdenModel;
import com.utils.EnvironmentVariables;
import com.utils.adapters.ProductoVentaOrdenReadOnlyAdapter;
import com.utils.exception.ExceptionHandler;
import com.utils.loading.LoadingHandler;
import com.utils.loading.LoadingProcess;

import java.util.ArrayList;
import java.util.List;

public class OrdenReadOnlyActivity extends BaseActivity {

    private TextView mesa, ordenNo, dependiente, totalPrincipal, totalSecundaria;
    private ListView listaOrden;
    private List<ProductoVentaOrdenModel> productosVOrden;
    private OrdenController controller;
    private CheckBox deLaCasaCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orden_readonly);

        try {
            initVarialbes();

            final Bundle bundleExtra = getIntent().getExtras();

            new LoadingHandler<Void>(act,new LoadingProcess<Void>() {
                @Override
                public Void process() throws Exception {
                    controller.starService(bundleExtra.getString(String.valueOf(R.string.cod_Orden)), bundleExtra.getString(String.valueOf(R.string.mesa)));
                    productosVOrden = new ArrayList<ProductoVentaOrdenModel>(controller.getProductoVentaOrden(bundleExtra.getString(String.valueOf(R.string.cod_Orden))));
                    return null;
                }

                @Override
                public void post(Void answer) {
                    updateCosto();
                    setAdapters();
                    addListeners();
                    actDeLaCasa();
                }
            });

        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    private void actDeLaCasa() {
        deLaCasaCheckBox.setChecked(controller.isDeLaCasa());
    }

    @Override
    protected void initVarialbes() {
        mesa = (TextView) findViewById(R.id.mesaNoLabel);
        ordenNo = (TextView) findViewById(R.id.ordenNoLabel);
        dependiente = (TextView) findViewById(R.id.dependienteLabel);
        totalPrincipal = (TextView) findViewById(R.id.totalPrincipalLabel);
        totalSecundaria = (TextView) findViewById(R.id.totalSecundariaLabel);
        listaOrden = (ListView) findViewById(R.id.listaOrdenReadOnly);
        controller = new OrdenController();
        deLaCasaCheckBox = (CheckBox) findViewById(R.id.deLaCasaCheckBoxReadOnly);
        deLaCasaCheckBox.setEnabled(false);

        Bundle bundleExtra = getIntent().getExtras();

        mesa.setText(bundleExtra.getString(String.valueOf(R.string.mesa)));//set el No de la mesa
        dependiente.setText(bundleExtra.getString(String.valueOf(R.string.user)));//set el label con el dependiente

        ordenNo.setText(bundleExtra.getString(String.valueOf(R.string.cod_Orden)));//set el label de la orden
    }

    @Override
    protected void addListeners() {
    }

    @Override
    protected void setAdapters() {
        listaOrden.setAdapter(new ProductoVentaOrdenReadOnlyAdapter(act, R.id.listaOrdenReadOnly, productosVOrden));
    }

    private void updateCosto() {
        try {
            float tot = 0;
            for (ProductoVentaOrdenModel x : productosVOrden) {
                tot += x.getProductoVentaModel().getPrecioVenta() * x.getCantidad();
            }
            if (EnvironmentVariables.MONEDA_PRINCIPAL.equals(EnvironmentVariables.MONEDA_PRINCIPAL)) {
                totalPrincipal.setText(EnvironmentVariables.setDosLugaresDecimales(tot) + EnvironmentVariables.MONEDA_PRINCIPAL);
                totalSecundaria.setText(EnvironmentVariables.setDosLugaresDecimales(tot / EnvironmentVariables.conversion) + EnvironmentVariables.MONEDA_SECUNDARIA);
            } else {
                totalPrincipal.setText(EnvironmentVariables.setDosLugaresDecimales(tot) + EnvironmentVariables.MONEDA_PRINCIPAL);
                totalSecundaria.setText(EnvironmentVariables.setDosLugaresDecimales(tot * EnvironmentVariables.conversion) + EnvironmentVariables.MONEDA_SECUNDARIA);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }
}
