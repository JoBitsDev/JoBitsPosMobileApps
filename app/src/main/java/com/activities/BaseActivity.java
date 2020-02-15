package com.activities;

import android.app.*;
import android.content.Context;
import android.os.Bundle;

/**
 * Capa: Activities
 * Clase base abstracta para TODAS las activitys de la aplicación.
 * TODAS las activitys extienden de esta clase y proporciona metodos basicos para todas como
 * iniciar variables y agregar listeners.
 *
 * @extends Activity ya que es una activity
 */
public abstract class BaseActivity extends Activity {

    protected BaseActivity act = this;

    private Bundle bundle;

    private static BaseActivity other;

    public static Context getContext() {
        return other.getApplicationContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        other = this;
        super.onCreate(savedInstanceState);
    }

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
     * Get el bundle. Es usado por ejemplo en almacen para pasarle cosas al initVariables.
     *
     * @return
     */
    public Bundle getBundle() {
        return bundle;
    }

    /**
     * Set el nuevo bundle.
     *
     * @param bundle
     */
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    /**
     * Sobreescribe el onStop del Activity para cerrar el progress dialog en caso de que este activo.
     */
    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * Muestra un PopUp basico con un mensaje.
     *
     * @param message a mostrar en el PopUp.
     */
    public void showMessage(String message) {
        new AlertDialog.Builder(this).setMessage(message).create().show();
    }


    /*public void notificarError(Exception e) { Noficacion de error vieja, entes de procesarlo _todo con el ExceptionHandler.
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
        dialog.show();
    }*/

}

