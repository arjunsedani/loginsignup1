package com.androidtutorialshub.loginregister.entities;


public class ProductObject {

    private int productId;

    private String productName;

    private int productImage;

    private String productDescription;

    private double productPrice;

    private int productSize;

    private String productColor;

    public ProductObject(int productId, String productName, int productImage, String productDescription, double productPrice, int productSize, String productColor) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productSize = productSize;
        this.productColor = productColor;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductImage() {
        return productImage;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductSize() {
        return productSize;
    }

    public String getProductColor() {
        return productColor;
    }

    @Override
    public String toString() {
        return "Product id and name: " + productId + " " + productName;
    }
}
