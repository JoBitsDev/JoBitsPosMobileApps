package com.utils.exception;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * NO SE USA
 */
public class NoExistingException extends Exception {
    public NoExistingException(String detailMessage) {
        super(detailMessage);
    }
}
