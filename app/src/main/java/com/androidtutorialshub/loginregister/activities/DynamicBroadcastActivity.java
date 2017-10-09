package com.androidtutorialshub.loginregister.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidtutorialshub.loginregister.R;

public class DynamicBroadcastActivity extends AppCompatActivity {
    private static final String TAG=BroadcastActivity.class.getSimpleName();

    private DynamicBroadcastRxActivity dynamicBroadcastRxActivity;
    private TextView textView;
    private int ctr = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_broadcast);

        dynamicBroadcastRxActivity =new DynamicBroadcastRxActivity();
        textView =(TextView) findViewById(R.id.textView);
    }

    @Override
    protected void onResume(){
        super.onResume();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        //intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(dynamicBroadcastRxActivity,intentFilter);
    }

    @Override
    protected void onPause(){
        super.onPause();
        unregisterReceiver(dynamicBroadcastRxActivity);
    }

    public void registerReceiver(View view){
      IntentFilter intentFilter=new IntentFilter();
      intentFilter.addAction(Intent.ACTION_TIME_TICK);
      registerReceiver(timeTickReceiver,intentFilter);
    }

    public void unregisterReceiver(View view){

        unregisterReceiver(timeTickReceiver);
    }

    private BroadcastReceiver timeTickReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int minutes = ctr;
            textView.setText(minutes + "minutes over");
            ctr++;

            Toast.makeText(context,"hello from time tick receiver",Toast.LENGTH_SHORT).show();
        }
    };
}
