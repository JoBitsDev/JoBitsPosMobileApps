package com.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.utils.exception.NoConnectionException;
import com.utils.exception.ServerErrorException;

import java.util.concurrent.ExecutionException;

/**
 * Created by Jorge on 17/11/18.
 */

public abstract class BaseActivity extends Activity {

    public ProgressDialog mProgressDialog;

    private Bundle bundle;

    abstract void initVarialbes();

    abstract void addListeners();

    protected void setAdapters() {
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Cargando ...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public void showMessage(String message) {
        new AlertDialog.Builder(this).setMessage(message).create().show();
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public void notificarError(Exception e) {
        String noConnectionError = findViewById(android.R.id.content).getRootView().getContext().getResources().getText(R.string.noConnectionError).toString();
        String serverError = findViewById(android.R.id.content).getRootView().getContext().getResources().getText(R.string.serverError).toString();
        String unespectedError = findViewById(android.R.id.content).getRootView().getContext().getResources().getText(R.string.unespectedError).toString();

        View v = findViewById(android.R.id.content).getRootView();

        String message = "Error to tiza";
        if (e instanceof NoConnectionException) {//no conection
            message = noConnectionError;
        } else if (e instanceof ServerErrorException) {//error del server
            message = serverError;
        } else {//error inesperado
            message = unespectedError;
        }

        Dialog dialog = new AlertDialog.Builder(this).setMessage(message).create();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                navigateUpTo(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        dialog.show();
    }

}

