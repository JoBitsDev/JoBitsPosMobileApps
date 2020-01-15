package com.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TabHost;

import com.utils.adapter.AreaAdapter;
import com.utils.adapter.DependientesAdapter;
import com.utils.adapter.GeneralAdapter;
import com.utils.adapter.PtoElaboracionAdapter;
import com.utils.exception.ExceptionHandler;

import java.util.Calendar;

public class PantallaPrincipalActivity extends BaseActivity {

    private AreaAdapter areaAdapter;
    private DependientesAdapter dependientesAdapter;
    private GeneralAdapter generalAdapter;
    private PtoElaboracionAdapter ptoElaboracionAdapter;

    private Calendar calendar;
    private int mes;
    private int dia;
    private int año;

    private EditText editTextShowDate;
    private ImageButton imageButtonDatePicker;
    private TabHost host;
    private float lastX;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        initVarialbes();
        addListeners();
    }

    @Override
    void initVarialbes() {
        calendar = Calendar.getInstance();
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        año = calendar.get(Calendar.YEAR);

        editTextShowDate = (EditText)findViewById(R.id.editTextDatePicker);
        imageButtonDatePicker = (ImageButton)findViewById(R.id.imageButtonDatePicker);

        initTab();
    }

    @Override
    void addListeners() {

        imageButtonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
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
        float currentX = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                currentX = event.getX();
                boolean dirRight = Math.abs( lastX - currentX) > 200;
                switchTab(dirRight);
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return onTabChangeTouchEvent(event);
    }

    private boolean switchTab(boolean change) {
        if (change) {
            if (host.getCurrentTab() == 1) {
                host.setCurrentTab(0);
            } else {
                host.setCurrentTab(1);
            }
        }
        return false;
    }

    private void pickDate(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? "0" + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? "0" + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                editTextShowDate.setText(diaFormateado + "/" + mesFormateado + "/" + year);
            }
        },año, mes, dia);
        recogerFecha.show();
    }
}
