package com.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CalendarView;

import com.utils.adapter.AreaAdapter;
import com.utils.adapter.DependientesAdapter;
import com.utils.adapter.GeneralAdapter;
import com.utils.adapter.PtoElaboracionAdapter;

public class PantallaPrincipalActivity extends BaseActivity {

    private AreaAdapter areaAdapter;
    private DependientesAdapter dependientesAdapter;
    private GeneralAdapter generalAdapter;
    private PtoElaboracionAdapter ptoElaboracionAdapter;
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        initVarialbes();
        addListeners();
    }

    @Override
    void initVarialbes() {
        calendarView = (CalendarView) findViewById(R.id.calendarView);
    }

    @Override
    void addListeners() {

    }
}
