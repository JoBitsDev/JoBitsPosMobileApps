package com.utils.exception;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.activities.BaseActivity;
import com.activities.MainActivity;
import com.activities.R;

/**
 * Capa: Utils.
 * Esta clase es la encargada de manejar _TODO el proceso de excepciones de la aplicacion.
 * Como funciona el sistema de tratamiento de excepciones:
 * 1 - Al seguir una arquitectura en capas las excepciones se pueden generar en cualquier capa.
 * 2 - Como solución para un tratamiento generico y concentrados de todas se crea esta clase.
 * 3 - La idea general es: DA IGUAL DONDE SE LANZE UNA EXCEPCION, NO SE MANEJA, SE SUBE DE CAPA HASTA QUE LA MAS ARRIBA LA PROCESA.
 * 4 - TODAS las excepciones van subiendo de capa hasta llegar a cada activity,
 * y cada activity es la encargada de llamar a ExceptionHandler para manejar la excepcion.
 * 5 - El metodo handleException maneja cada excepcion en dependencia de su tipo, llamando a cada método y permitiendo
 * un manejo personalizado de cada una.
 */
public class ExceptionHandler {
    /**
     * Titulo del Popup que se le muestra al usuario.
     */
    public static final String POPUP_TITLE = "ERROR";

    /**
     * Titulo del boton del Popup que se le muestra al usuario.
     */
    public static final String POPUP_BUTTON_TEXT = "OK";

    /**
     * Duracion por defecto del Toast.
     */
    public static final int TOAST_DURATION = Toast.LENGTH_SHORT;

    /**
     * Maneja TODAS las excepciones de la aplicacion.
     *
     * @param e        Excepcion a tratar, de tipo generico y se castea internamente para un tratamiento especializado de cada una.
     * @param activity Donde se lanzo la excepcion para poder notificar al usuario.
     */
    public static void handleException(Exception e, BaseActivity activity) {
        StackTraceElement element = e.getStackTrace()[0];

        if (e instanceof NoConnectionException) {//no conection
            handleNoConnectionException((NoConnectionException) e, activity);
        } else if (e instanceof ServerErrorException) {//error del server
            handleServerErrorException((ServerErrorException) e, activity);
        } else if (e instanceof NoExistingException) {
            handleNoExistingException((Exception) e, activity);
        } else {//error inesperado
            handleUnknownException((Exception) e, activity);
        }
        /*Viejo, sin boton ni nada
        Dialog dialog = new AlertDialog.Builder(c).setMessage(message).create();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

        dialog.show();*/
    }

    private static void handleNoExistingException(Exception e, BaseActivity activity) {
        final Context c = activity.getApplicationContext();
        final View v = activity.findViewById(android.R.id.content).getRootView();

        //popup a mostrar
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(e.getMessage());
        builder.setTitle(ExceptionHandler.POPUP_TITLE);

        builder.setNeutralButton(ExceptionHandler.POPUP_BUTTON_TEXT, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {//comportamiento al clickear el boton
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * Maneja las excepciones que se lanzan por no haber conección con el servidor.
     *
     * @param e        Excepcion a tratar, de tipo generico y se castea internamente para un tratamiento especializado de cada una.
     * @param activity Donde se lanzo la excepcion para poder notificar al usuario.
     */
    private static void handleNoConnectionException(NoConnectionException e, final BaseActivity activity) {
        final Context c = activity.getApplicationContext();
        final View v = activity.findViewById(android.R.id.content).getRootView();

        //mensaje explicando que pasa
        String noConnectionErrorMessage = v.findViewById(android.R.id.content).getRootView().getContext().getResources().getText(R.string.noConnectionError).toString();

        createDialog(noConnectionErrorMessage, c, activity);
    }

    /**
     * Maneja las excepciones que se lanzan por errores internos en el servidor.
     *
     * @param e        Excepcion a tratar, de tipo generico y se castea internamente para un tratamiento especializado de cada una.
     * @param activity Donde se lanzo la excepcion para poder notificar al usuario.
     */
    private static void handleServerErrorException(ServerErrorException e, final BaseActivity activity) {
        final Context c = activity.getApplicationContext();
        final View v = activity.findViewById(android.R.id.content).getRootView();

        //mensaje explicando que pasa
        String serverErrorMessage = v.findViewById(android.R.id.content).getRootView().getContext().getResources().getText(R.string.serverError).toString();

        createDialog(serverErrorMessage, c, activity);
    }

    /**
     * Maneja una excepcion generica que no pertence a ningun tipo especifico previamente tratada.
     *
     * @param e        Excepcion a tratar, de tipo generico y se castea internamente para un tratamiento especializado de cada una.
     * @param activity Donde se lanzo la excepcion para poder notificar al usuario.
     */
    private static void handleUnknownException(Exception e, final BaseActivity activity) {
        final Context c = activity.getApplicationContext();
        final View v = activity.findViewById(android.R.id.content).getRootView();

        //mensaje explicando que pasa
        String unespectedError = v.findViewById(android.R.id.content).getRootView().getContext().getResources().getText(R.string.unespectedError).toString();

        createDialog(unespectedError, c, activity);

    }

    /**
     * Metodo para crear el dialogo que se va a mostrar con el error
     *
     * @param message el mensaje que va a contener el dialogo
     * @param c       el contexto en el que se va a mostrar el dialogo
     * @param a       la actividad para subir a la actividad superior
     */
    private static void createDialog(String message, final Context c, final BaseActivity a) {
        //popup a mostrar
        AlertDialog.Builder builder = new AlertDialog.Builder(a);
        builder.setMessage(message);
        builder.setTitle(ExceptionHandler.POPUP_TITLE);

        builder.setNeutralButton(ExceptionHandler.POPUP_BUTTON_TEXT, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {//comportamiento al clickear el boton
                dialog.dismiss();
                a.navigateUpTo(new Intent(c, MainActivity.class));
            }
        });
        builder.create().show();

        //Toast.makeText(c, e.getCause().getMessage(), TOAST_DURATION).show();//un pequenno toast con mas detalles
    }
}
