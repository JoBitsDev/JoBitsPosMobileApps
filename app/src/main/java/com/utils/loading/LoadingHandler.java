package com.utils.loading;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.activities.BaseActivity;
import com.utils.exception.ExceptionHandler;

import java.util.concurrent.ExecutionException;

public class LoadingHandler<T> extends AsyncTask<Void, Void, T> {

    /**
     * Progress Dialog para el cargando.
     */
    private ProgressDialog mProgressDialog = null;

    /**
     * Activity sobre el que se va a mostrar el dialog.
     */
    private BaseActivity activity;

    /**
     * Proceso que se va a ejecutar en background mientras sale el cargando.
     */
    private LoadingProcess process;

    /**
     * Excepcion que se puede lanzar en caso que algo salga mal en #doInBackground()
     */
    private Exception exc = null;

    /**
     * Crea el Hnadler, solo se encarga de ejecutar la secuencia.
     *
     * @param activity Activity sobre el que se va a mostrar el dialog.
     * @param process  Proceso que se va a ejecutar en background mientras sale el cargando.
     */
    public LoadingHandler(BaseActivity activity, LoadingProcess process) {
        this.activity = activity;
        this.process = process;
        this.execute();//start the secuence
    }

    /**
     * Muestra el progress dialog activo, cargando indefinidamente.
     * Termina cuando termina el proceso.
     */
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(activity);
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
     * Antes de ejecutar muestra el Cargando.
     */
    @Override
    protected void onPreExecute() {
        this.showProgressDialog();
    }

    /**
     * Ejecuta la accion process del proceso en el background.
     *
     * @param voids Parametro requerido en el extends.
     * @return Void Parametro requerido en el extends.
     */
    @Override
    protected T doInBackground(Void... voids) {
        try {
            return (T) process.process();
        } catch (Exception e) {
            this.exc = e;
        }
        return null;
    }

    /**
     * Cuando termina esconde el Cargando.
     */
    @Override
    protected void onPostExecute(T t) {
        this.hideProgressDialog();
        try {
            if (exc == null) {
                process.post(get());
            } else {
                throw exc;
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, activity);
        }
    }

}