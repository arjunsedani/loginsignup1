package com.androidtutorialshub.loginregister.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.helpers.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;

import com.google.android.gms.wallet.PaymentMethodTokenizationParameters;
import com.google.android.gms.wallet.PaymentMethodTokenizationType;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.gms.wallet.fragment.SupportWalletFragment;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.google.android.gms.wallet.fragment.WalletFragmentMode;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;


public class PaymentsActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = PaymentsActivity.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;

    private SupportWalletFragment mWalletFragment;

    private MaskedWallet maskedWallet;

    private static final int REQUEST_CODE_MASKED_WALLET = 1000;

    public static final int FULL_WALLET_REQUEST_CODE = 889;

    private double cartTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setTitle("Payment Page");

        double totalPrice = getIntent().getExtras().getDouble("TOTAL_PRICE");

        Address.AddressOptions options = new Address.AddressOptions(WalletConstants.THEME_LIGHT);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .setAccountName("Inducesmile")
                .addApi(Wallet.API, new Wallet.WalletOptions.Builder()
                        .setEnvironment(Constants.WALLET_ENVIRONMENT)
                        .setTheme(WalletConstants.THEME_LIGHT)
                        .build())
                .build();

        mWalletFragment = SupportWalletFragment.newInstance(getWalletFragmentOption());
        initializeWalletFragment();

        Wallet.Payments.isReadyToPay(mGoogleApiClient).setResultCallback(new ResultCallback<BooleanResult>() {
            @Override
            public void onResult(@NonNull BooleanResult booleanResult) {
                if (booleanResult.getStatus().isSuccess()) {
                    if (booleanResult.getValue()) {
                        // Show Android Pay buttons
                        initializeWalletFragment();
                    } else {
                        // Hide Android Pay buttons, show a message that Android Pay
                        // cannot be used yet, and display a traditional checkout button
                    }
                } else {
                    // Error making isReadyToPay call
                    Log.e(TAG, "isReadyToPay:" + booleanResult.getStatus());
                }
            }
        });
    }

    private PaymentMethodTokenizationParameters useStripePaymentGatewayForProcessing() {
        PaymentMethodTokenizationParameters parameters = PaymentMethodTokenizationParameters.newBuilder()
                .setPaymentMethodTokenizationType(PaymentMethodTokenizationType.PAYMENT_GATEWAY)
                .addParameter("gateway", "stripe")
                .addParameter("stripe:publishableKey", Constants.PUBLISHABLE_KEY)
                .addParameter("stripe:version", Constants.VERSION)
                .build();
        return parameters;
    }

    private WalletFragmentOptions getWalletFragmentOption(){
        WalletFragmentStyle walletFragmentStyle = new WalletFragmentStyle()
                .setBuyButtonText(WalletFragmentStyle.BuyButtonText.BUY_WITH)
                .setBuyButtonAppearance(WalletFragmentStyle.BuyButtonAppearance.ANDROID_PAY_DARK)
                .setBuyButtonWidth(WalletFragmentStyle.Dimension.MATCH_PARENT);

        WalletFragmentOptions walletFragmentOptions = WalletFragmentOptions.newBuilder()
                .setEnvironment(Constants.WALLET_ENVIRONMENT)
                .setFragmentStyle(walletFragmentStyle)
                .setTheme(WalletConstants.THEME_LIGHT)
                .setMode(WalletFragmentMode.BUY_BUTTON)
                .build();
        return walletFragmentOptions;
    }

    private void initializeWalletFragment(){
        WalletFragmentInitParams.Builder startParamsBuilder = WalletFragmentInitParams.newBuilder()
                .setMaskedWalletRequest(sendMaskedWalletRequest())
                .setMaskedWalletRequestCode(REQUEST_CODE_MASKED_WALLET)
                .setAccountName("Inducesmile");
        mWalletFragment.initialize(startParamsBuilder.build());

        // add Wallet fragment to the UI
        getSupportFragmentManager().beginTransaction().replace(R.id.wallet_button_holder, mWalletFragment).commit();
    }

     private MaskedWalletRequest sendMaskedWalletRequest() {
        MaskedWalletRequest request = MaskedWalletRequest.newBuilder()
                .setMerchantName(Constants.MERCHANT_NAME)
                .setPhoneNumberRequired(true)
                .setShippingAddressRequired(true)
                .setCurrencyCode(Constants.CURRENCY_CODE_USD)
                .setEstimatedTotalPrice(String.valueOf(cartTotal))
                // Create a Cart with the current line items. Provide all the information
                // available up to this point with estimates for shipping and tax included.

                .setCart(Cart.newBuilder()
                        .setCurrencyCode(Constants.CURRENCY_CODE_USD)
                        .setTotalPrice(String.valueOf(cartTotal))
                        //.setLineItems()
                        .build())
                .setPaymentMethodTokenizationParameters(useStripePaymentGatewayForProcessing())
                .build();
        return request;
    }

    private void makeFullWalletRequest(){
        Wallet.Payments.loadFullWallet(mGoogleApiClient, generateFullWalletRequest(), FULL_WALLET_REQUEST_CODE);
    }

    private FullWalletRequest generateFullWalletRequest() {
        FullWalletRequest request = FullWalletRequest.newBuilder()
                .setGoogleTransactionId(maskedWallet.getGoogleTransactionId())
                        .setCart(Cart.newBuilder()
                                .setCurrencyCode(Constants.CURRENCY_CODE_USD)
                                .setTotalPrice(String.valueOf(cartTotal))
                                //.setLineItems(lineItems)
                                .build())
                        .build();
        return request;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnecting() || mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        int errorCode = -1;
        if (data != null) {
            errorCode = data.getIntExtra(WalletConstants.EXTRA_ERROR_CODE, -1);
        }
        switch (requestCode) {
            case REQUEST_CODE_MASKED_WALLET:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.d(TAG, "Testing for output");
                        if (data != null) {
                            maskedWallet = data.getParcelableExtra(WalletConstants.EXTRA_MASKED_WALLET);

                            // call to get the Google transaction id
                            String googleTransactionId = maskedWallet.getGoogleTransactionId();
                            detailedMaskedWalletInformation(maskedWallet);
                        }
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                    default:
                        handleError(errorCode);
                        break;
                }
                break;

            case WalletConstants.RESULT_ERROR:
                handleError(errorCode);
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    void handleError(int errorCode) {
        switch (errorCode) {
            case WalletConstants.ERROR_CODE_SPENDING_LIMIT_EXCEEDED:
                Toast.makeText(PaymentsActivity.this, "Error with your payment", Toast.LENGTH_LONG).show();
                break;
            case WalletConstants.ERROR_CODE_INVALID_PARAMETERS:
            case WalletConstants.ERROR_CODE_AUTHENTICATION_FAILURE:
            case WalletConstants.ERROR_CODE_BUYER_ACCOUNT_ERROR:
            case WalletConstants.ERROR_CODE_MERCHANT_ACCOUNT_ERROR:
            case WalletConstants.ERROR_CODE_SERVICE_UNAVAILABLE:
            case WalletConstants.ERROR_CODE_UNSUPPORTED_API_VERSION:
            case WalletConstants.ERROR_CODE_UNKNOWN:
            default:
                // unrecoverable error
                break;
        }
    }

    private void detailedMaskedWalletInformation(MaskedWallet maskedWallet){

    }
}
