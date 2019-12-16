package firstdream.exception;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class NoExistingException extends Exception {
    private Context context;

    public NoExistingException(String detailMessage, Context context) {
        super(detailMessage);
        this.context = context;
    }

    public void showMessageInToast(){
        Toast.makeText(context,getMessage(),Toast.LENGTH_LONG);

    }

    public void showMessageInAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(getMessage());
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}
