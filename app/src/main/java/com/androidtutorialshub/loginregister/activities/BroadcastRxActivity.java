package com.androidtutorialshub.loginregister.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BroadcastRxActivity extends BroadcastReceiver {
    private static final String TAG=BroadcastActivity.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"HELLO");
        Toast.makeText(context, "hello from 1st rx", Toast.LENGTH_LONG).show();
    }
}