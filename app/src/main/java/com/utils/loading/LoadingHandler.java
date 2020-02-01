package com.utils.loading;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.activities.BaseActivity;
import com.utils.exception.ExceptionHandler;
/*
  new LoadingHandler<Boolean>(this, new LoadingProcess<Boolean>() {
       @Override
       public Boolean process() throws Exception {
       }

       @Override
       public void post(Boolean value) {

       }
  });
 */

//TODO: el loading handler se quita cuando se da un click fuera de el.lo esperado seria que bloqueara cualquier interaccion con el hasta que no terminara el proceso

/**
 * Asi se usa.
 * <pre>
 * new LoadingHandler<Boolean>(this, new LoadingProcess<Boolean>() {
 *
 *      @Override
 *      public Boolean process() throws Exception {
 *          return controller.loginAction(username, password, access);
 *      }
 *
 *      @Override
 *      public void post(Boolean value) {
 *          if (value) {
 *              loginResult.setTextColor(Color.GREEN);
 *              loginResult.setText(R.string.autenticacionCorrecta);
 *
 *              Intent launch = new Intent(act, PantallaPrincipalActivity.class);
 *              launch.putExtra(String.valueOf(R.string.user), username);
 *              startActivity(launch);
 *
 *          } else {//si no es correcto lanza error
 *              errorAlAutenticar();
 *          }
 *      }
 * });
 * <pre/>
 */
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
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
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