package com.utils.exception;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by Jorge on 26/2/18.
 */

public class MutualExclusionException extends Exception {

    private Context context;

    public MutualExclusionException(String detailMessage,Context context) {
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
