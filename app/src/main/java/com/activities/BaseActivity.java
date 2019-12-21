package com.activities;

import android.app.*;
import android.content.*;
import com.utils.exception.*;
import android.os.Bundle;
import android.view.View;

/**
 * Capa: Activities
 * Clase base abstracta para TODAS las activitys de la aplicación.
 * TODAS las activitys extienden de esta clase y proporciona metodos basicos para todas como
 * iniciar variables y agregar listeners.
 */
public abstract class BaseActivity extends Activity {

    /**
     * Progress Dialog para el cargando.
     */
    public ProgressDialog mProgressDialog;

    /**
     * TODO: ver bien que es esto y para que se usa.
     */
    private Bundle bundle;

    /**
     * Método abstracto a reimplementar para inicializar las variables del XML en el activity.
     */
    abstract void initVarialbes();

    /**
     * Método abstracto a reimplementar para agregar los listener de las variables del XML
     * en el activity.
     * NOTA: TODOS los listener se declara aqui y NO en el XML para respetar la arquitectura en capas.
     */
    abstract void addListeners();

    /**
     * Metodo a reimplementar por las activitys que lo necesiten si usan algun adapter.
     */
    protected void setAdapters() {
    }

    /**
     * Muestra el progress dialog activo, cargando indefinidamente.
     */
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Cargando ...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    /**
     * Esconde el progress dialog.
     */
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    /**
     * Sobreescribe el onStop del Activity para cerrar el progress dialog en caso de que este activo.
     */
    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    /**
     * Muestra un PopUp basico con un mensaje.
     * @param message a mostrar en el PopUp.
     */
    public void showMessage(String message) {
        new AlertDialog.Builder(this).setMessage(message).create().show();
    }

    /**
     * Devuelve el bundle.
     * @return el bundle.
     */
    public Bundle getBundle() {
        return bundle;
    }

    /**
     * Sobreescribe el bundle actual por el especifico
     * @param bundle a sobreescribir
     */
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    /**
     * Notifica un error en la aplicacion. En dependencia del tipo de error, se procesa y ejecuta
     * un comportamiento especifico.
     * Este es el metodo para el control de excepciones.
     * @param e Excepcion recivida a manejar.
     */
    public void notificarError(Exception e) {
        //Mensaje a enviar cuando no hay coneccion con el servidor
        String noConnectionError = findViewById(android.R.id.content).getRootView().getContext().getResources().getText(R.string.noConnectionError).toString();
        //Mensaje a enviar cuando hay un error en el servidor
        String serverError = findViewById(android.R.id.content).getRootView().getContext().getResources().getText(R.string.serverError).toString();
        //Mensaje a enviar cuando hay un error extranno que no se conoce
        String unespectedError = findViewById(android.R.id.content).getRootView().getContext().getResources().getText(R.string.unespectedError).toString();

        View v = findViewById(android.R.id.content).getRootView();

        String message = "Error to tiza";//este nunca se lanza por el ultimo else
        if (e instanceof NoConnectionException) {//no conection
            message = noConnectionError;
        } else if (e instanceof ServerErrorException) {//error del server
            message = serverError;
        } else {//error inesperado
            message = unespectedError;
        }

        //popup que se muestra
        Dialog dialog = new AlertDialog.Builder(this).setMessage(message).create();

        //accion para cuando se quita el popup
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {//que cuando se cierre se mueva para la pantalla principal
                navigateUpTo(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //muestra  el popup
        dialog.show();

    }

}

