package com.androidtutorialshub.loginregister.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.androidtutorialshub.loginregister.R;

public class NetworkConnectivityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_connectivity);
        Toast.makeText(this, getConnectivityStatus(this), Toast.LENGTH_SHORT).show();
    }

    private String getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork && activeNetwork.getState() == NetworkInfo.State.CONNECTED) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) return "WIFI";
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) return "MOBILE";
        }
        return "NOT_CONNECTED";
    }

}