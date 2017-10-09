package com.androidtutorialshub.loginregister.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidtutorialshub.loginregister.R;


public class BroadcastActivity extends AppCompatActivity {

    private static final String TAG=BroadcastActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
    }

    public void broadcastIntent(View view) {
       Intent intent=new Intent(this,BroadcastRxActivity.class);
        sendBroadcast(intent);
    }

    public void broadcastToInnerReceiver(View view) {
        Intent intent=new Intent(this,MyThirdReceiverInner.class);
        sendBroadcast(intent);
    }

    public static class MyThirdReceiverInner extends BroadcastReceiver{
        private static final String TAG=MyThirdReceiverInner.class.getSimpleName();
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG,"hello from 3rd rx");
            Toast.makeText(context, "hello from 3RD rx", Toast.LENGTH_LONG).show();
        }
    }
}
