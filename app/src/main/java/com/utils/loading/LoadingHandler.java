package com.utils.loading;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.activities.BaseActivity;
import com.activities.PantallaPrincipalActivity;
import com.activities.R;
import com.controllers.LoginController;
import com.services.models.UbicacionModel;
import com.utils.EnvironmentVariables;
import com.utils.exception.ExceptionHandler;
import com.utils.exception.UnauthorizedException;
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
            } else if (exc instanceof UnauthorizedException) {//hace falta autorizacion
                autorizar();
            } else {
                throw exc;
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, activity);
        }
    }

    private void autorizar() {
        final LoginController cont = new LoginController();
        final String oldTOKEN = cont.getToken();

        final Dialog d = new Dialog(activity);
        d.setContentView(R.layout.login_dialog);

        final EditText user = (EditText) d.findViewById(R.id.user);
        final EditText pass = (EditText) d.findViewById(R.id.pass);
        final Button button = (Button) d.findViewById(R.id.ok);

        d.setTitle("Editar Ubicación");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                try {
                    final String usuario = user.getText().toString();
                    final String contrasenna = pass.getText().toString();

                    try {
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
                                            return (T) process.process();
                                        }

                                        @Override
                                        public void post(T value) {
                                            onPostExecute(value);
                                            cont.setToken(oldTOKEN);
                                        }
                                    });
                                }
                            }
                        });

                    } catch (Exception e) {
                        cont.setToken(oldTOKEN);
                    }

                } catch (Exception e) {
                    ExceptionHandler.handleException(e, activity);
                }
            }
        });
        d.show();
    }

}