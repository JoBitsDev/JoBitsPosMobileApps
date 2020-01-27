package com.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;

import com.controllers.ResumenVentasController;
import com.services.models.AreaListModel;
import com.services.models.DpteListModel;
import com.services.models.PuntoElaboracionListModel;
import com.services.models.VentaResumenModel;
import com.utils.adapter.AreaAdapter;
import com.utils.adapter.DependientesAdapter;
import com.utils.adapter.GeneralAdapter;
import com.utils.adapter.PtoElaboracionAdapter;
import com.utils.exception.ExceptionHandler;
import com.utils.loading.LoadingHandler;
import com.utils.loading.LoadingProcess;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Capa: Activities
 * Clase que controla el XML de la pantalla principal de POS Manager.
 *
 * @extends BaseActivity ya que es una activity propia de la aplicacion.
 */
public class PantallaPrincipalActivity extends BaseActivity {

    private AreaAdapter areaAdapter;
    private DependientesAdapter dependientesAdapter;
    private GeneralAdapter generalAdapter;
    private PtoElaboracionAdapter ptoElaboracionAdapter;
    private ResumenVentasController resumenVentasController;

    private List<VentaResumenModel> ventaResumenModels;
    private List<AreaListModel> areaListModels;
    private List<DpteListModel> dpteListModels;
    private List<PuntoElaboracionListModel> puntoElaboracionListModels;

    private Calendar calendar;
    private int mes;
    private int dia;
    private int año;

    private EditText editTextShowDate;
    private ImageButton imageButtonDatePicker;
    private ImageButton imageButtonActualizar;
    private TabHost host;
    private float lastX;

    private ListView listViewGeneral;
    private ListView listViewAreas;
    private ListView listViewDependientes;
    private ListView listViewPtoElaboracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        initVarialbes();
        addListeners();
        setAdapters();
        actualizar(formatDate());
    }

    @Override
    void initVarialbes() {
        try {
            calendar = Calendar.getInstance();
            mes = calendar.get(Calendar.MONTH);
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            año = calendar.get(Calendar.YEAR);

            editTextShowDate = (EditText) findViewById(R.id.editTextDatePicker);
            imageButtonDatePicker = (ImageButton) findViewById(R.id.imageButtonDatePicker);
            imageButtonActualizar = (ImageButton) findViewById(R.id.buttonActualizar);
            listViewGeneral = (ListView) findViewById(R.id.listViewGeneral);
            listViewAreas = (ListView) findViewById(R.id.listViewAreas);
            listViewDependientes = (ListView) findViewById(R.id.listViewDependientes);
            listViewPtoElaboracion = (ListView) findViewById(R.id.listViewPtoElaboracion);

            ventaResumenModels = new ArrayList<VentaResumenModel>();
            areaListModels = new ArrayList<AreaListModel>();
            dpteListModels = new ArrayList<DpteListModel>();
            puntoElaboracionListModels = new ArrayList<PuntoElaboracionListModel>();

            generalAdapter = new GeneralAdapter(this, R.layout.general_list, ventaResumenModels);
            areaAdapter = new AreaAdapter(this, R.layout.area_list, areaListModels);
            dependientesAdapter = new DependientesAdapter(this, R.layout.dependientes_list, dpteListModels);
            ptoElaboracionAdapter = new PtoElaboracionAdapter(this, R.layout.pto_elaboracion_list, puntoElaboracionListModels);

            resumenVentasController = new ResumenVentasController();

            initTab();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    @Override
    void addListeners() {
        try {
            imageButtonDatePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pickDate();
                }
            });

            imageButtonActualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actualizar(editTextShowDate.getText().toString());
                }
            });

            editTextShowDate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    actualizar(editTextShowDate.getText().toString());
                }
            });
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    @Override
    protected void setAdapters() {
        try {
            listViewGeneral.setAdapter(generalAdapter);
            listViewAreas.setAdapter(areaAdapter);
            listViewDependientes.setAdapter(dependientesAdapter);
            listViewPtoElaboracion.setAdapter(ptoElaboracionAdapter);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    /**
     * Método para actualizar las listas de datos.
     */

    public void actualizar(final String fecha){
        new LoadingHandler<VentaResumenModel>(act, new LoadingProcess<VentaResumenModel>() {
            @Override
            public VentaResumenModel process() throws Exception {
                return resumenVentasController.getResumenVentas(fecha);
            }

            @Override
            public void post(VentaResumenModel answer) {
                ventaResumenModels.clear();
                areaListModels.clear();
                dpteListModels.clear();
                ptoElaboracionAdapter.clear();

                ventaResumenModels.add(answer);
                areaListModels.addAll(answer.getAreas());
                dpteListModels.addAll(answer.getDptes());
                ptoElaboracionAdapter.addAll(answer.getPtosElaboracion());

                setAdapters();
            }
        });
    }

    private void initTab() {
        try {
            host = (TabHost) findViewById(R.id.tabHost);
            if (host != null) {//TODO: por que este if??
                host.setup();

                TabHost.TabSpec spec = host.newTabSpec("General");

                //Tab 1
                spec.setContent(R.id.General);
                spec.setIndicator("General");
                host.addTab(spec);

                //Tab 2
                spec = host.newTabSpec("Àreas");
                spec.setContent(R.id.Areas);
                spec.setIndicator("Àreas");
                host.addTab(spec);

                //Tab 3
                spec = host.newTabSpec("Dependientes");
                spec.setContent(R.id.Dependientes);
                spec.setIndicator("Dependiente");
                host.addTab(spec);

                //Tab 4
                spec = host.newTabSpec("Punto de Elaboraciòn");
                spec.setContent(R.id.Ptos_Elaboracion);
                spec.setIndicator("Punto de Elaboraciòn");
                host.addTab(spec);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    private boolean onTabChangeTouchEvent(MotionEvent event) {
        try {
            float currentX = 0;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = event.getX();
                    break;
                case MotionEvent.ACTION_UP:
                    currentX = event.getX();
                    boolean dirRight = false;
                    if (Math.abs(lastX - currentX) > 200) {//mayor derecha menor izquierda
                        dirRight = true;
                    }
                    switchTab(dirRight);
                    return dirRight;
            }
            return false;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
        return false;
    }

    private boolean switchTab(boolean change) {
        try {
            if (host.getCurrentTab() == 3 && change == true || host.getCurrentTab() == 0 && change == false) {
            } else {
                if (change == true) {
                    host.setCurrentTab(host.getCurrentTab() + 1);
                } else {
                    host.setCurrentTab(host.getCurrentTab() - 1);
                }
            }
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return onTabChangeTouchEvent(event);
    }

    /**
     * Metodo para mostrar un Widget al usuario para que introduzca la fecha y formatearla.
     */
    private void pickDate() {
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10) ? "0" + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10) ? "0" + String.valueOf(mesActual) : String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                editTextShowDate.setText(diaFormateado + "/" + mesFormateado + "/" + year);
            }
        }, año, mes, dia);
        recogerFecha.show();
    }
    private String formatDate(){
        //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
        final int mesActual = mes + 1;
        //Formateo el día obtenido: antepone el 0 si son menores de 10
        String diaFormateado = (dia < 10) ? "0" + String.valueOf(dia) : String.valueOf(dia);
        //Formateo el mes obtenido: antepone el 0 si son menores de 10
        String mesFormateado = (mesActual < 10) ? "0" + String.valueOf(mesActual) : String.valueOf(mesActual);
        return diaFormateado + "/" + mesFormateado + "/" + año;
    }
}
