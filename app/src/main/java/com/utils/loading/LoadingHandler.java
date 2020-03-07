package com.utils.loading;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.activities.BaseActivity;
import com.activities.R;
import com.controllers.LoginController;
import com.utils.exception.ExceptionHandler;
import com.utils.exception.ServerErrorException;

import java.net.HttpURLConnection;
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
            if (exc == null) {//no hubo excepcion, sigo
                process.post(get());
            } else if (exc instanceof ServerErrorException
                    && (((ServerErrorException) exc).getCode() == HttpURLConnection.HTTP_FORBIDDEN
                    || ((ServerErrorException) exc).getCode() == HttpURLConnection.HTTP_UNAUTHORIZED)) {//hace falta autorizacion
                autorizar((ServerErrorException) exc);
            } else {
                throw exc;
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, activity);
        }
    }

    private void autorizar(final ServerErrorException exc) {
        final LoginController cont = new LoginController();
        final String oldTOKEN = cont.getToken();

        final Dialog d = new Dialog(activity);
        d.setContentView(R.layout.login_dialog);

        final TextView det = (TextView) d.findViewById(R.id.detallesAutorizacion);
        final EditText user = (EditText) d.findViewById(R.id.nombreUsuarioAutorizacion);
        final EditText pass = (EditText) d.findViewById(R.id.passAutorizacion);
        final Button button = (Button) d.findViewById(R.id.okAutorizacion);

        d.setTitle(exc.getCode() == HttpURLConnection.HTTP_FORBIDDEN ? "Autorizar acción de usuario" : "La sesión expiró. Autenticarse de nuevo");
        det.setTextColor(exc.getCode() == HttpURLConnection.HTTP_FORBIDDEN ? Color.RED : Color.BLUE);
        det.setText(exc.getMessage());
        d.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (exc.getCode() == HttpURLConnection.HTTP_FORBIDDEN) {
                    cont.setToken(oldTOKEN);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final String usuario = user.getText().toString();
                    final String contrasenna = pass.getText().toString();

                    new LoadingHandler<Boolean>(activity, new LoadingProcess<Boolean>() {
                        @Override
                        public Boolean process() throws Exception {
                            return cont.loginAction(usuario, contrasenna);
                        }

                        @Override
                        public void post(Boolean value) {
                            if (value) {
                                new LoadingHandler<T>(activity, new LoadingProcess<T>() {
                                    @Override
                                    public T process() throws Exception {
                                        T val = null;
                                        try {
                                            val = (T) process.process();
                                        } catch (Exception e) {
                                            d.dismiss();
                                            throw e;
                                        }
                                        return val;
                                    }

                                    @Override
                                    public void post(T value) {
                                        process.post(value);
                                        d.dismiss();
                                    }
                                });
                            } else {
                                d.dismiss();
                            }
                        }
                    });


                } catch (Exception e) {
                    ExceptionHandler.handleException(e, activity);
                }
            }
        });
        d.show();
    }

}