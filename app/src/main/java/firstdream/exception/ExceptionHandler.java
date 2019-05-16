package firstdream.exception;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Jorge on 26/2/18.
 */

public class ExceptionHandler  {

    public static void showMessageInAlert(Exception e, Context c){
        StackTraceElement element =  e.getStackTrace()[0];

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage(e.getMessage() + "\n" +
                element.getClassName()+"\n" + element.getMethodName() +"\n"+ element.getLineNumber());
        builder.setTitle("ERROR");
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.show();

    }
}
