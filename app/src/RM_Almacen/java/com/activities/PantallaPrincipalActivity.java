package com.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.controllers.PantallaPrincipalController;
import com.services.models.InsumoAlmacenModel;
import com.utils.adapters.AlmacenInsumoAdapter;


public class PantallaPrincipalActivity extends BaseActivity {

    private ListView listView;
    private TextView userText, almacenText;
    private EditText searchText;
    private RadioButton radioButtonSalida, radioButtonRebaja;
    private ImageButton salidaButton;
    private ImageButton entradaButton;
    private PantallaPrincipalController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacenstate);
        setBundle(getIntent().getExtras());

        initVarialbes();
        addListeners();
        setAdapters();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_almacenstate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_imprimirEstado:
                return controller.imprimirEstadoActual();
            case R.id.action_ticket_compra:
                return controller.imprimirTicketCompra();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onEntradaClick(final View v) {
        final InsumoAlmacenModel i = ((InsumoAlmacenModel) listView.getAdapter().getItem((Integer) v.getTag()));
        final EditText input = new EditText(v.getContext());
        final EditText amount = new EditText(v.getContext());
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setRawInputType(Configuration.KEYBOARD_12KEY);
        amount.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        amount.setRawInputType(Configuration.KEYBOARD_12KEY);
        new AlertDialog.Builder(v.getContext()).
                setView(input).
                setTitle("Entrada de InsumoModel").
                setMessage("Introduzca la cantidad de " + i.getInsumoModel() + " a dar entrada").
                setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton(R.string.agregar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Float cantidad = Float.valueOf(0);
                try {
                    cantidad = Float.parseFloat(input.getText().toString()) * i.getInsumoModel().getCostoPorUnidad();
                } catch (Exception e) {
                    return;
                }
                amount.setText(cantidad.toString());
                amount.selectAll();
                new AlertDialog.Builder(v.getContext()).setView(amount).setTitle("Monto").
                        setMessage("Introduzca el valor de la entrada ").setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        float cantidad = 0, monto = 0;
                        try {
                            cantidad = Float.parseFloat(input.getText().toString());
                            monto = Float.parseFloat(amount.getText().toString());
                        } catch (Exception e) {
                            dialog.dismiss();
                            return;
                        }
                        controller.darEntrada(i, cantidad, monto);

                        listView.post(new Runnable() {
                            @Override
                            public void run() {
                                listView.setAdapter(fetchData());
                            }
                        });
                        dialog.dismiss();
                    }
                }).create().show();
                dialog.dismiss();
            }
        }).
                create().
                show();
    }

    public void onRebajarClick(View v) {
        if (radioButtonSalida.isChecked()) {
            onSalidaClick(v);
        } else {
            onRebajaClick(v);
        }
    }

    private void onSalidaClick(final View v) {
        final InsumoAlmacenModel i = ((InsumoAlmacenModel) listView.getAdapter().getItem((Integer) v.getTag()));
        final EditText input = new EditText(v.getContext());
        final String[] ipvs = getIPVData(i.getInsumoModel().getCodInsumo());
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setRawInputType(Configuration.KEYBOARD_12KEY);
        new AlertDialog.Builder(v.getContext()).
                setView(input).
                setTitle("Salida a punto de elaboracion").
                setMessage("Introduzca la cantidad de " + i.getInsumoModel() + " a dar salida").
                setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).
                setPositiveButton(R.string.agregar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new AlertDialog.Builder(v.getContext())
                                .setItems(ipvs, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        float cantidad = 0;
                                        try {
                                            cantidad = Float.parseFloat(input.getText().toString());
                                        } catch (Exception e) {
                                            dialog.dismiss();
                                        }
                                        if (cantidad > i.getCantidad()) {
                                            Toast.makeText(v.getContext(), R.string.saldo_insuficiente, Toast.LENGTH_LONG);
                                            dialog.dismiss();
                                        } else {
                                            controller.darSalida(i, cantidad, ipvs[which]);
                                            listView.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    listView.setAdapter(fetchData());
                                                }
                                            });
                                            dialog.dismiss();
                                        }
                                    }
                                }).
                                setTitle("Destino").
                                setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).
                                create().
                                show();
                        dialog.dismiss();
                    }
                }).
                create().
                show();
    }

    private void onRebajaClick(final View v) {
        final InsumoAlmacenModel i = ((InsumoAlmacenModel) listView.getAdapter().getItem((Integer) v.getTag()));
        final EditText input = new EditText(v.getContext());
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setRawInputType(Configuration.KEYBOARD_12KEY);
        final EditText razon = new EditText(v.getContext());
        razon.setInputType(InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
        new AlertDialog.Builder(v.getContext()).
                setView(input).
                setTitle("Merma").
                setMessage("Introduzca la cantidad de " + i.getInsumoModel() + " a rebajar").
                setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).
                setPositiveButton(R.string.agregar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new AlertDialog.Builder(v.getContext()).
                                setView(razon).
                                setTitle("Razon de rebaja").
                                setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setPositiveButton("Mermar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                float cantidad = 0;
                                try {
                                    cantidad = Float.parseFloat(input.getText().toString());
                                } catch (Exception e) {
                                    dialog.dismiss();
                                }
                                if (cantidad > i.getCantidad()) {
                                    Toast.makeText(v.getContext(), R.string.saldo_insuficiente, Toast.LENGTH_LONG);
                                    dialog.dismiss();
                                } else {
                                    controller.rebajar(i, cantidad, razon.getText().toString());
                                    listView.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            listView.setAdapter(fetchData());
                                        }
                                    });
                                    dialog.dismiss();
                                }
                            }

                        }).
                                create().
                                show();

                        dialog.dismiss();
                    }
                }).
                create().
                show();
    }

    private AlmacenInsumoAdapter fetchData() {
        return new AlmacenInsumoAdapter(this, R.id.listaInsumos, controller.getPrimerAlmacen());
    }

    private String[] getIPVData(String insumoCod) {
        return controller.getCocinasNamesForIPV(insumoCod);
    }

    @Override
    void initVarialbes() {
        listView = (ListView) findViewById(R.id.listaInsumos);
        userText = (TextView) findViewById(R.id.textUser);
        almacenText = (TextView) findViewById(R.id.textViewNombreAlmacen);
        userText.setText(getBundle().getString(String.valueOf(R.string.user)));
        controller = new PantallaPrincipalController(userText.getText().toString());
        searchText = (EditText) findViewById(R.id.editText);
        radioButtonSalida = (RadioButton) findViewById(R.id.radioButtonSalida);
        radioButtonRebaja = (RadioButton) findViewById(R.id.radioButtonRebaja);
        salidaButton = (ImageButton) findViewById(R.id.salidaButton);
        entradaButton = (ImageButton) findViewById(R.id.entradaButton);
    }

    @Override
    void addListeners() {

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((AlmacenInsumoAdapter) listView.getAdapter()).getFilter().filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        salidaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRebajarClick(v);
            }
        });
        entradaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEntradaClick(v);
            }
        });
    }

    @Override
    protected void setAdapters() {
        listView.setAdapter(fetchData());
    }
}
