package com.androidtutorialshub.loginregister.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.jsonEntityObjects.PaymentResponseObject;
import com.androidtutorialshub.loginregister.jsonEntityObjects.ServerObject;
import com.androidtutorialshub.loginregister.network.GsonRequest;
import com.androidtutorialshub.loginregister.network.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class PayPalCheckoutActivity extends AppCompatActivity {

    private static final String TAG = PayPalCheckoutActivity.class.getSimpleName();

    private static final int MY_SOCKET_TIMEOUT_MS = 5000;

    private static final String SERVER_PATH = "Path_to_Server_To_Store_Token";

    private double totalCostPrice;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId("ENTER PAY PAL CLIENT ID");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal_checkout);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setTitle(getString(R.string.pay_with_paypal));

        totalCostPrice = getIntent().getExtras().getDouble("TOTAL_PRICE");
        Log.d(TAG, "Price " + totalCostPrice);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        Button payPalButton = (Button)findViewById(R.id.pay_pal_button);
        assert payPalButton != null;
        payPalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializePayPalPayment();
            }
        });
    }

    private void initializePayPalPayment(){
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(totalCostPrice)), "USD", "Female Cloths", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));
                    String jsonPaymentResponse = confirm.toJSONObject().toString(4);

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    PaymentResponseObject responseObject = gson.fromJson(jsonPaymentResponse, PaymentResponseObject.class);
                    if(responseObject != null){
                        String paymentId = responseObject.getResponse().getId();
                        String paymentState = responseObject.getResponse().getState();

                        Log.d(TAG, "Log payment id and state " + paymentId + " " + paymentState);

                        //send to your server for verification.
                        sendPaymentVerificationToServer(paymentId, paymentState);
                    }

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }

    private void sendPaymentVerificationToServer(String id, String state){
        Map<String, String> params = new HashMap<String,String>();
        params.put("PAYMENT_ID", id);
        params.put("PAYMENT_STATE", state);

        GsonRequest<ServerObject> serverRequest = new GsonRequest<ServerObject>(
                Request.Method.POST,
                SERVER_PATH,
                ServerObject.class,
                params,
                createRequestSuccessListener(),
                createRequestErrorListener());

        serverRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(PayPalCheckoutActivity.this).addToRequestQueue(serverRequest);
    }

    private Response.Listener<ServerObject> createRequestSuccessListener() {
        return new Response.Listener<ServerObject>() {
            @Override
            public void onResponse(ServerObject response) {
                try {
                    Log.d(TAG, "Json Response " + response.getSuccess());
                    if(!TextUtils.isEmpty(response.getSuccess())){
                        Toast.makeText(PayPalCheckoutActivity.this, getString(R.string.successful_payment), Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(PayPalCheckoutActivity.this, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        };
    }

    private Response.ErrorListener createRequestErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
