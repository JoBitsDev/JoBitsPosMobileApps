package com.activities;

import java.util.*;

import android.view.*;
import android.text.*;
import android.widget.*;
import android.os.Bundle;

import com.services.models.*;
import com.controllers.OrdenController;

import android.app.AlertDialog;

import com.utils.EnvironmentVariables;

import android.content.DialogInterface;
import android.content.res.Configuration;

import com.utils.adapters.MenuAdapterThis;
import com.utils.adapters.MenuPrincipalAdapter;
import com.utils.exception.ExceptionHandler;
import com.utils.adapters.ProductoVentaOrdenAdapter;

public class OrdenActivity extends BaseActivity {

    private OrdenController controller;

    private Button cerrarOrdenButton, despacharACocinaButton;
    private TextView mesaNoLabel, ordenNoLabel, dependienteLabel, totalPrincipalLabel, totalSecundariaLabel;
    private EditText searchText;

    private ListView menuProductosListView, menuSeccionListView;

    private ListView listaOrden;
    private CheckBox deLaCasaCheckBox;

    private List<SeccionModel> secciones;
    private List<ProductoVentaModel> productos;
    private List<ProductoVentaOrdenModel> productosVentaOrden;
    private List<ProductoVentaModel> productosSelected;

    private ProductoVentaModel lastClickedMenu = null;
    private ProductoVentaOrdenModel lastClickedOrden = null;

    private MenuPrincipalAdapter menuPrincipalAdapter;
    private MenuAdapterThis menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_orden);

            initVarialbes();
            setAdapters();
            addListeners();

            Bundle bundleExtra = getIntent().getExtras();
            createOldOrden(bundleExtra);//crea la orden vieja
            actDeLaCasa();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    private void actDeLaCasa() {
        deLaCasaCheckBox.setChecked(controller.isDeLaCasa());
    }

    @Override
    protected void initVarialbes() {
        try {
            mesaNoLabel = (TextView) findViewById(R.id.mesaNoLabel);
            ordenNoLabel = (TextView) findViewById(R.id.ordenNoLabel);
            dependienteLabel = (TextView) findViewById(R.id.dependienteLabel);
            totalPrincipalLabel = (TextView) findViewById(R.id.totalPrincipalLabel);
            totalSecundariaLabel = (TextView) findViewById(R.id.totalSecundariaLabel);
            deLaCasaCheckBox = (CheckBox) findViewById(R.id.deLaCasaCheckBox);
            menuProductosListView = (ListView) findViewById(R.id.menuListView);
            menuSeccionListView = (ListView) findViewById(R.id.menuPrincipalListView);
            listaOrden = (ListView) findViewById(R.id.listaOrden);
            cerrarOrdenButton = (Button) findViewById(R.id.buttonCerrarOrden);
            despacharACocinaButton = (Button) findViewById(R.id.buttondespacharCocina);
            searchText = (EditText) findViewById(R.id.searchText);
            controller = new OrdenController();

            Bundle bundleExtra = getIntent().getExtras();
            mesaNoLabel.setText(bundleExtra.getString(String.valueOf(R.string.mesa)));//set el No de la mesa
            dependienteLabel.setText(bundleExtra.getString(String.valueOf(R.string.user)));//set el label con el dependiente
            ordenNoLabel.setText(bundleExtra.getString(String.valueOf(R.string.cod_Orden)));//set el label de la orden
            productosVentaOrden = new ArrayList<ProductoVentaOrdenModel>();
            productosSelected = new ArrayList<ProductoVentaModel>();

            initMenu(bundleExtra.getString(String.valueOf(R.string.mesa)));
            initTab();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    @Override
    protected void addListeners() {
        try {
            if (cerrarOrdenButton != null) {//TODO: por que esta este if??
                cerrarOrdenButton.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return onCerrarOrdenButtonLongClick();
                    }
                });
            }

            if (despacharACocinaButton != null) {//TODO: por que esta este if??
                despacharACocinaButton.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return onDespacharACocinaLongClock();
                    }
                });
            }

            menuSeccionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onMenuSeccionListViewItemClick(position);
                }
            });


            menuProductosListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    return onMenuProductosListViewLongClick(view, position);
                }
            });

            menuProductosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onMenuProductosListViewClick(position);
                }
            });


            searchText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //metodo abstracto en el padre y necesita reimplementacion, pero no se usa aqui y se deja vacio
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().isEmpty()) {
                        menuAdapter.getFilter().filter(s.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    //metodo abstracto en el padre y necesita reimplementacion, pero no se usa aqui y se deja vacio
                }
            });

            deLaCasaCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeLaCasaCheckBoxClick();
                }
            });

        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    private void onDeLaCasaCheckBoxClick() {
        try {
            controller.setDeLaCasa(deLaCasaCheckBox.isChecked());
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    private void onMenuSeccionListViewItemClick(int position) {
        productosSelected = secciones.get(position).getProductos();
        if (!productosSelected.isEmpty()) {
            menuAdapter = new MenuAdapterThis(getApplicationContext(), android.R.layout.simple_list_item_1, productosSelected);
            menuProductosListView.setAdapter(menuAdapter);
        }
    }

    private boolean onMenuProductosListViewLongClick(final View v, int position) {
        try {
            lastClickedMenu = menuAdapter.getItem(position);
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
                    addProductoVarios(Float.parseFloat(input.getText().toString()));
                }
            }).create().show();
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
            return false;
        }
    }

    private boolean onMenuProductosListViewClick(int position) {
        try {
            lastClickedMenu = menuAdapter.getItem(position);
            addProductoUnoSolo();
            searchText.setText("");
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
            return false;
        }
    }

    @Override
    protected void setAdapters() {
        try {
            menuPrincipalAdapter = new MenuPrincipalAdapter(this, android.R.layout.simple_list_item_1, secciones);
            menuSeccionListView.setAdapter(menuPrincipalAdapter);

            listaOrden.setAdapter(new ProductoVentaOrdenAdapter(this, R.id.listaOrden, productosVentaOrden));
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    private void createOldOrden(Bundle bundleExtra) {
        try {
            String codOrden;

            OrdenActivity old = (OrdenActivity) getLastNonConfigurationInstance();//TODO: porque un metodo deprecated??
            if (old != null) {
                codOrden = old.getController().getCodOrden();
            } else {
                codOrden = bundleExtra.getString(String.valueOf(R.string.cod_Orden));
            }

            String mesa = bundleExtra.getString(String.valueOf(R.string.mesa));
            String dependiente = bundleExtra.getString(String.valueOf(R.string.user));

            if (codOrden != null && mesa != null && dependiente != null) {
                fillAct(controller.starService(codOrden, mesa, dependiente).getProductoVentaOrden(codOrden));
            } else {
                controller.starService(mesa, dependiente);
                if (!controller.initOrden()) {
                    throw new NullPointerException("La cajera debe comenzar el dia\n" +
                            "de trabajo para acceder a las ordenes");

                }
            }
            ordenNoLabel.setText(controller.getCodOrden());
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    private void initTab() {
        try {
            TabHost host = (TabHost) findViewById(R.id.tabHost);
            if (host != null) {//TODO: por que este if??
                host.setup();

                TabHost.TabSpec spec = host.newTabSpec("Pedido");

                //Tab 2
                spec.setContent(R.id.ordenModel);
                spec.setIndicator("Pedido");
                host.addTab(spec);

                //Tab 1
                spec = host.newTabSpec("Menu");
                spec.setContent(R.id.menu);
                spec.setIndicator("Menu");
                host.addTab(spec);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        return this;
    }

    private void fillAct(final List<ProductoVentaOrdenModel> orden) {
        try {
            final ProductoVentaOrdenAdapter adapter = (ProductoVentaOrdenAdapter) listaOrden.getAdapter();
            listaOrden.post(new Runnable() {
                @Override
                public void run() {
                    adapter.clear();
                    adapter.setObjects(orden);
                    updateCosto();
                }
            });
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    private void initMenu(String codMesa) {
        try {
            secciones = controller.getSecciones();
            productos = controller.getProductos(codMesa);

            for (ProductoVentaModel x : productos) {
                for (SeccionModel y : secciones) {
                    if (x.getSeccionnombreSeccion().equals(y.getNombreSeccion())) {
                        y.addProducto(x);
                        break;
                    }
                }
            }

            for (Iterator<SeccionModel> it = secciones.iterator(); it.hasNext(); ) {//TODO: Revisar el for que asi es como mejor funciona
                SeccionModel secc = it.next();
                if (secc.getProductos().isEmpty()) {
                    it.remove();
                }
            }
        /*for (int i = 0; i < secciones.size(); ) {
            if (secciones.getProductoVentaOrden(i).getProductos().isEmpty()) {
                secciones.remove(i);
            } else {
                i++;
            }
        }*/
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            // Inflate the menuProductosListView; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_orden, menu);
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
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
                case R.id.moverAMesa:
                    moverAMesa();
                    break;
                case R.id.enviarCocina:
                    despacharACocina();
                    break;
                case R.id.cerrarOrden:
                    cerrarOrden();
                    break;
                case R.id.compartirPedido:
                    compartirPedido();
                    break;
                case R.id.cederACamarero:
                    cederACamarero();
                    break;
                case R.id.action_settings:
                    return true;
                default:
                    super.onOptionsItemSelected(item);
                    break;
            }

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {//TODO: wath??????? por que si esta el case puesto???
                return true;
            }
            return super.onOptionsItemSelected(item);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
            return false;
        }

    }

    private void cederACamarero() {
        try {
            final String[] usuarios = controller.getUsuariosActivos();

            if (usuarios.length > 0) {//hay usuarios
                final BaseActivity act = this;
                new AlertDialog.Builder(this).
                        setTitle(R.string.seleccioneElUsuarioACeder).
                        setSingleChoiceItems(usuarios, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    boolean serverReponse = controller.cederAUsuario(usuarios[which]);
                                    if (serverReponse) {//TODO: ver bien esto, xq el finish()
                                        dialog.dismiss();
                                        finish();
                                        Toast.makeText(getApplicationContext(), "Orden cedida al camarero.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "No se pudo seder la orden.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    ExceptionHandler.handleException(e, act);
                                }
                            }
                        }).
                        setNeutralButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            } else {
                Toast.makeText(this, "No existe ningun otro usuario trabajando para ceder orden", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    private void compartirPedido() {//TODO: vacio???

    }

    public void onAddClick(View v) {
        try {
            lastClickedMenu = ((ProductoVentaOrdenModel) listaOrden.getAdapter().getItem((Integer) v.getTag())).getProductoVentaModel();
            addProductoUnoSolo();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    public void onRemoveClick(View v) {//TODO: listener en el xml??
        try {
            lastClickedOrden = ((ProductoVentaOrdenModel) listaOrden.getAdapter().getItem((Integer) v.getTag()));
            removeProducto();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    public void onAdjuntoClick(View v) {//TODO: listener en el xml??
        try {
            lastClickedOrden = (ProductoVentaOrdenModel) listaOrden.getAdapter().getItem((Integer) v.getTag());

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Agregar Nota");
            // Get the layout inflater
            LayoutInflater inflater = this.getLayoutInflater();
            final EditText nota = new EditText(this);
            nota.getText().insert(0, controller.getNota(lastClickedOrden));

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(nota)
                    // Add action buttons
                    .setPositiveButton(R.string.agregar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            addNota(toHTMLString(nota.getText().toString()));
                        }
                    })
                    .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder.create().show();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    public void onComensalClick(View v) {//TODO: listener en el xml??
        try {
            lastClickedOrden = ((ProductoVentaOrdenModel) listaOrden.getAdapter().getItem((Integer) v.getTag()));

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Agregar Comensal");

            final EditText nota = new EditText(this);
            nota.getText().insert(0, controller.getComensal(lastClickedOrden));

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
            builder.create().show();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    private void addComensal(String comensal) {
        try {
            boolean response = controller.addComensal(lastClickedOrden, comensal);
            if (response) {
                Toast.makeText(this, lastClickedOrden.getProductoVentaModel().getNombre() + " Comensal Agregado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error agragando comensal.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    public void moverAMesa() {//TODO: terminar esto
        try {
            final BaseActivity act = this;

            final String[] mesas = controller.getMesas();
            new AlertDialog.Builder(this).
                    setTitle(R.string.seleccioneLaMesaAMover).
                    setSingleChoiceItems(mesas, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                if (controller.moverAMesa(mesas[which])) {//TODO: ver bien esto, xq el finish()
                                    dialog.dismiss();
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Movido a mesa", Toast.LENGTH_SHORT).show();//TODO: ver bien el texto
                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "No se pudo mover a mesa.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                ExceptionHandler.handleException(e, act);
                            }
                        }
                    }).
                    setNeutralButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    private void updateCosto() {
        try {
            float tot = 0;
            for (ProductoVentaOrdenModel x : productosVentaOrden) {
                tot += x.getProductoVentaModel().getPrecioVenta() * x.getCantidad();
            }
            if (EnvironmentVariables.MONEDA_PRINCIPAL.equals(EnvironmentVariables.MONEDA_PRINCIPAL)) {
                totalPrincipalLabel.setText(EnvironmentVariables.setDosLugaresDecimales(tot) + EnvironmentVariables.MONEDA_PRINCIPAL);
                totalSecundariaLabel.setText(EnvironmentVariables.setDosLugaresDecimales(tot / EnvironmentVariables.conversion) + EnvironmentVariables.MONEDA_SECUNDARIA);
            } else {
                totalPrincipalLabel.setText(EnvironmentVariables.setDosLugaresDecimales(tot) + EnvironmentVariables.MONEDA_PRINCIPAL);
                totalSecundariaLabel.setText(EnvironmentVariables.setDosLugaresDecimales(tot * EnvironmentVariables.conversion) + EnvironmentVariables.MONEDA_SECUNDARIA);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    public void addProductoUnoSolo() {
        try {
            if (lastClickedMenu != null) {
                if (controller.addProducto(lastClickedMenu)) {
                    controller.increasePoducto(lastClickedMenu, (ProductoVentaOrdenAdapter) listaOrden.getAdapter());
                    updateCosto();
                    Toast.makeText(this, lastClickedMenu.getNombre() + " agregado al pedido.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.errorAlAutenticar, Toast.LENGTH_SHORT).show();//TODO: como que error al autenticar??
                }
            } else {
                Toast.makeText(this, R.string.noItemSeleccionado, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    public void addProductoVarios(float cantidad) {
        try {
            if (lastClickedMenu != null) {
                if (controller.addProducto(lastClickedMenu, cantidad)) {
                    controller.addProducto(lastClickedMenu, cantidad);
                    updateCosto();
                    Toast.makeText(this, lastClickedMenu.getNombre() + " agregado al pedido.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.errorAlAutenticar, Toast.LENGTH_SHORT).show();//TODO: como que error al autenticar??
                }
            } else {
                Toast.makeText(this, R.string.noItemSeleccionado, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    private void removeProducto() {
        try {
            if (lastClickedOrden != null) {
                if (lastClickedOrden.getCantidad() > lastClickedOrden.getEnviadosACocina()) {
                    if (controller.removeProducto(lastClickedOrden.getProductoVentaModel())) {
                        ProductoVentaOrdenAdapter adapter = (ProductoVentaOrdenAdapter) listaOrden.getAdapter();
                        adapter.decrease(lastClickedOrden.getProductoVentaModel());
                        updateCosto();
                        Toast.makeText(this, lastClickedOrden.getProductoVentaModel().getNombre() + " removido del pedido.", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(this, R.string.errorAlBorrar, Toast.LENGTH_SHORT).show();
                } else {
                    showMessage("Para eliminar un producto enviado a cocina \n necesita autorizacion.");
                }
            } else {
                Toast.makeText(this, R.string.noItemSeleccionado, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    private boolean onCerrarOrdenButtonLongClick() {
        try {
            cerrarOrden();
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
            return false;
        }
    }

    private void cerrarOrden() {
        try {
            if (controller.finishOrden(deLaCasaCheckBox.isChecked())) {
                new AlertDialog.Builder(this).
                        setMessage(R.string.cerrarOrden).
                        setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                finish();
                            }
                        }).create().show();
            } else {
                new AlertDialog.Builder(this).setMessage(R.string.error_al_cerrar_falta_enviar_cocina).
                        setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    private boolean onDespacharACocinaLongClock() {
        try {
            despacharACocina();
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
            return false;
        }
    }

    private void despacharACocina() {
        try {
            controller.sendToKitchen();
            fillAct(controller.getProductoVentaOrden());
            new AlertDialog.Builder(this).setMessage(R.string.enviarAcocina).create().show();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    private void addNota(String nota) {
        try {
            controller.addNota(lastClickedOrden.getProductoVentaModel(), nota);
            Toast.makeText(this, lastClickedOrden.getProductoVentaModel().getNombre() + " Nota Agregada", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            ExceptionHandler.handleException(e, this);
        }
    }

    private String toHTMLString(String s) {
        return s.replace(' ', '-');
    }

    public OrdenController getController() {
        return controller;
    }
}

