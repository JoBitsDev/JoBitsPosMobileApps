package com.utils.exception;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class DayClosedException extends Exception {

    private Context context;

    public DayClosedException(String detailMessage) {
        super(detailMessage);
    }
}
