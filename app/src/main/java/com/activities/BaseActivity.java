package com.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    public void notificarNoConnection() {
        View v = findViewById(android.R.id.content).getRootView();

        Dialog dialog = new AlertDialog.Builder(this).setMessage(v.getContext().getResources().
                getText(R.string.exNoServerConn).toString()).create();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                navigateUpTo(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        dialog.show();
    }
}

