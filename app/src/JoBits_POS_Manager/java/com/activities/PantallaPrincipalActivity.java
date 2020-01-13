package com.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.utils.adapter.AreaAdapter;
import com.utils.adapter.DependientesAdapter;
import com.utils.adapter.GeneralAdapter;
import com.utils.adapter.PtoElaboracionAdapter;

import java.util.Calendar;

public class PantallaPrincipalActivity extends BaseActivity {

    private AreaAdapter areaAdapter;
    private DependientesAdapter dependientesAdapter;
    private GeneralAdapter generalAdapter;
    private PtoElaboracionAdapter ptoElaboracionAdapter;

    private Calendar calendar;
    private int mes = calendar.get(Calendar.MONTH);
    private int dia = calendar.get(Calendar.DAY_OF_MONTH);
    private int año = calendar.get(Calendar.YEAR);

    private EditText editTextShowDate;
    private ImageButton imageButtonDatePicker;

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
    }

    @Override
    void addListeners() {

        imageButtonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
