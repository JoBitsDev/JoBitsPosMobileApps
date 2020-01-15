package com.activities;

import android.text.*;
import android.view.*;
import android.widget.*;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;

import com.utils.adapters.*;
import com.utils.exception.ExceptionHandler;
import com.services.models.InsumoAlmacenModel;
import com.controllers.PantallaPrincipalController;

/**
 * Capa: Activities
 * Clase que controla el XML de la pantalla principal del Almacen.
 *
 * @extends BaseActivity ya que es una activity propia de la aplicacion.
 */
public class PantallaPrincipalActivity extends BaseActivity {

    /**
     * Controller del {@link PantallaPrincipalActivity} para manejar las peticiones a la capa inferior.
     */
    private PantallaPrincipalController controller;

    /**
     * Lista que muestra los productos en el almacen.
     */
    private ListView listView;

    /**
     * Textos con el usuario y el almacen
     */
    private TextView userText, almacenText;

    /**
     * Cuadro de texto de busqueda.
     */
    private EditText searchText;

    /**
     * Radio Button para darle salida a producto.
     */
    private RadioButton radioButtonSalida;

    /**
     * NO SE USAN
     */
    private RadioButton radioButtonRebaja;
    private ImageButton salidaButton;
    private ImageButton entradaButton;

    /**
     * Spinner para filtrar por cocina.
     */
    private Spinner spinnerFiltrar;

    /**
     * Adapter para el filtro.
     */
    private FilterAdapter filterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_almacenstate);
            setBundle(getIntent().getExtras());

            initVarialbes();//inicializa las  variables
            addListeners();//agrega los listener
            setAdapters();//agrega los adapters
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }


    @Override
    protected void initVarialbes() {
        try {
            listView = (ListView) findViewById(R.id.listaInsumos);
            almacenText = (TextView) findViewById(R.id.textViewNombreAlmacen);

            userText = (TextView) findViewById(R.id.textUser);
            userText.setText(getBundle().getString(String.valueOf(R.string.user)));

            controller = new PantallaPrincipalController(userText.getText().toString());
            searchText = (EditText) findViewById(R.id.editText);
            radioButtonSalida = (RadioButton) findViewById(R.id.radioButtonSalida);
            radioButtonRebaja = (RadioButton) findViewById(R.id.radioButtonRebaja);
            salidaButton = (ImageButton) findViewById(R.id.salidaButton);
            entradaButton = (ImageButton) findViewById(R.id.entradaButton);
            spinnerFiltrar = (Spinner) findViewById(R.id.filtrarBy);
            filterAdapter = new FilterAdapter(act, android.R.layout.simple_spinner_dropdown_item, controller.getCocinasNames());
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    protected void addListeners() {
        try {
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

            spinnerFiltrar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    onSpinnerFiltrarItemSelected(view);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            //TODO: faltan dos listener que estan directo en el xml
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    /**
     * Accion a llamar cuando se selecciona una cocina en el spinner de filtrar
     *
     * @param view View de la aplicacion.
     */
    private void onSpinnerFiltrarItemSelected(View view) {
        try {
            if (spinnerFiltrar.getSelectedItemPosition() == 0) {
                listView.setAdapter(controller.getAdapter(act, R.id.listaInsumos));
            } else {
                listView.setAdapter(controller.getAdapter(act, R.id.listaInsumos, spinnerFiltrar.getSelectedItem().toString()));
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    protected void setAdapters() {
        try {
            listView.setAdapter(controller.getAdapter(act, R.id.listaInsumos));
            spinnerFiltrar.setAdapter(filterAdapter.createAdapter());
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_almacenstate, menu);
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            switch (id) {
                case R.id.action_imprimirEstado:
                    return imprimirEstadoAlmacen();
                case R.id.action_ticket_compra:
                    return imprimirTicketCompra();
                default:
                    return super.onOptionsItemSelected(item);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return false;
        }
    }

    /**
     * Manda a imprimir el ticket de compra.
     *
     * @return true si se puede imprimir el ticket, false de lo contrario.
     */
    private boolean imprimirTicketCompra() {
        try {
            boolean resp = controller.imprimirTicketCompra();
            if (resp) {
                Toast.makeText(getApplicationContext(), "Imprimiendo...", Toast.LENGTH_SHORT);
            } else {
                ExceptionHandler.handleException(new Exception("Error imprimiendo"), act);
            }
            return resp;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return false;
        }
    }

    /**
     * Manda a imprimir el estado del almacen.
     *
     * @return true si se puede imprimir el ticket, false de lo contrario.
     */
    private boolean imprimirEstadoAlmacen() {
        try {
            boolean resp = controller.imprimirEstadoActualAlmacen();
            if (resp) {
                showMessage("Imprimiendo...");
            } else {
                ExceptionHandler.handleException(new Exception("Error imprimiendo"), act);
            }
            return resp;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return false;
        }
    }

    /**
     * Accion a llamar cuando se le da entrada a un producto.
     *
     * @param v View de la aplicacion.
     */
    public void onEntradaClick(final View v) {
        try {
            final int pos = (Integer) v.getTag();
            final InsumoAlmacenModel insumoModel = ((InsumoAlmacenModel) listView.getAdapter().getItem(pos));

            final EditText input = new EditText(v.getContext());
            input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);

            final EditText amount = new EditText(v.getContext());
            amount.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            amount.setRawInputType(Configuration.KEYBOARD_12KEY);

            new AlertDialog.Builder(v.getContext()).
                    setView(input).
                    setTitle("Entrada de InsumoModel").
                    setMessage("Introduzca la cantidad de " + insumoModel.getInsumoModel() + " a dar entrada").
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
                        cantidad = Float.parseFloat(input.getText().toString()) * insumoModel.getInsumoModel().getCostoPorUnidad();
                    } catch (Exception e) {
                        ExceptionHandler.handleException(e, act);
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

                                controller.darEntrada(insumoModel, cantidad, monto);
                            } catch (Exception e) {
                                ExceptionHandler.handleException(e, act);
                            }

                            updateListView(v);

                            dialog.dismiss();
                        }
                    }).create().show();
                    dialog.dismiss();
                }
            }).create().show();

        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    private void updateListView(View v) {
        listView.post(new Runnable() {
            @Override
            public void run() {
                try {
                    int index = listView.getFirstVisiblePosition();
                    View v = listView.getChildAt(0);
                    int top = v.getTop();

                    onSpinnerFiltrarItemSelected(v);
                    //listView.setAdapter(controller.getAdapter(act, R.id.listaInsumos));

                    listView.setSelectionFromTop(index, top);
                } catch (Exception e) {
                    ExceptionHandler.handleException(e, act);
                }
            }
        });
    }

    /**
     * Accion a llamar cuando se le dar rebaja a un producto o salida dependiendo del estado del combo box.
     *
     * @param v View de la aplicacion.
     */
    public void onSalidaRebajaClick(View v) {
        try {
            if (radioButtonSalida.isChecked()) {
                onSalidaClick(v);
            } else {
                onRebajaClick(v);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    /**
     * Accion a llamar cuando se le da salida a un producto.
     *
     * @param v View de la aplicacion.
     */
    private void onSalidaClick(final View v) {
        try {
            int pos = (Integer) v.getTag();
            final InsumoAlmacenModel i = ((InsumoAlmacenModel) listView.getAdapter().getItem(pos));
            final String[] ipvs = getIPVData(i.getInsumoModel().getCodInsumo());

            final EditText input = new EditText(v.getContext());
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
                                                ExceptionHandler.handleException(e, act);
                                            }
                                            if (cantidad > i.getCantidad()) {
                                                Toast.makeText(v.getContext(), R.string.saldo_insuficiente, Toast.LENGTH_LONG);
                                                dialog.dismiss();
                                            } else {
                                                try {
                                                    controller.darSalida(i, cantidad, ipvs[which]);
                                                } catch (Exception e) {
                                                    ExceptionHandler.handleException(e, act);
                                                }

                                                updateListView(v);

                                                dialog.dismiss();
                                            }
                                        }
                                    }).setTitle("Destino")
                                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).
                                    create().show();
                            dialog.dismiss();
                        }
                    }).create().show();

        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    /**
     * Accion a llamar cuando se va a rebajar un producto.
     *
     * @param v View de la aplicacion.
     */
    private void onRebajaClick(final View v) {
        try {
            int pos = (Integer) v.getTag();
            final InsumoAlmacenModel insumoModel = ((InsumoAlmacenModel) listView.getAdapter().getItem(pos));

            final EditText input = new EditText(v.getContext());
            input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);

            final EditText razon = new EditText(v.getContext());
            razon.setInputType(InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);

            new AlertDialog.Builder(v.getContext()).
                    setView(input).
                    setTitle("Merma").
                    setMessage("Introduzca la cantidad de " + insumoModel.getInsumoModel() + " a rebajar").
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
                                    if (cantidad > insumoModel.getCantidad()) {
                                        Toast.makeText(v.getContext(), R.string.saldo_insuficiente, Toast.LENGTH_LONG);
                                        dialog.dismiss();
                                    } else {
                                        try {
                                            controller.rebajar(insumoModel, cantidad, razon.getText().toString());
                                        } catch (Exception e) {
                                            ExceptionHandler.handleException(e, act);
                                        }

                                        updateListView(v);

                                        dialog.dismiss();
                                    }
                                }

                            }).create().show();

                            dialog.dismiss();
                        }
                    }).create().show();

        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    /**
     * Obtiene la informacion de los IPVs segun un insumo.
     *
     * @param insumoCod Codigo del insumo.
     * @return arreglo de String con los IPVs.
     */
    private String[] getIPVData(String insumoCod) {
        try {
            return controller.getCocinasNamesForIPV(insumoCod);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
            return null;
        }
    }

}
