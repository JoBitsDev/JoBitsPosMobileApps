package com.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

/**
 * Created by Jorge on 17/11/18.
 */

public abstract class BaseActivity extends Activity {

    public ProgressDialog mProgressDialog;

    private Bundle bundle;

    abstract void initVarialbes();

    abstract void addListeners();

    protected void setAdapters(){

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

}

