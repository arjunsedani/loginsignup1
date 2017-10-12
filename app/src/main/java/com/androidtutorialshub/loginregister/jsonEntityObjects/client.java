package com.androidtutorialshub.loginregister.jsonEntityObjects;


public class client {

    private String environment;

    private String paypal_sdk_version;

    private String platform;

    private String product_name;

    public client(String environment, String paypal_sdk_version, String platform, String product_name) {
        this.environment = environment;
        this.paypal_sdk_version = paypal_sdk_version;
        this.platform = platform;
        this.product_name = product_name;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getPaypal_sdk_version() {
        return paypal_sdk_version;
    }

    public String getPlatform() {
        return platform;
    }

    public String getProduct_name() {
        return product_name;
    }
}



