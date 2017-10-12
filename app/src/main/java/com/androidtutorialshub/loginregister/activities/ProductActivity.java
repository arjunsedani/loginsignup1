package com.androidtutorialshub.loginregister.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.entities.ProductObject;
import com.androidtutorialshub.loginregister.helpers.MySharedPreference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private static final String TAG = ProductActivity.class.getSimpleName();

    private TextView productSize, productColor, productPrice, productDescription;

    private ImageView productImage;

    private Gson gson;

    private int cartProductNumber = 0;

    private MySharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sharedPreference = new MySharedPreference(ProductActivity.this);

        productImage = (ImageView)findViewById(R.id.full_product_image);
        productSize = (TextView)findViewById(R.id.product_size);
        productColor = (TextView)findViewById(R.id.product_color);
        productPrice = (TextView)findViewById(R.id.product_price);
        productDescription = (TextView)findViewById(R.id.product_description);

        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();

        String productInStringFormat = getIntent().getExtras().getString("PRODUCT");
        final ProductObject singleProduct = gson.fromJson(productInStringFormat, ProductObject.class);
        if(singleProduct != null){
            setTitle(singleProduct.getProductName());

            productImage.setImageResource(singleProduct.getProductImage());
            productSize.setText("Size: " + String.valueOf(singleProduct.getProductSize()));
            productColor.setText("Color: " + singleProduct.getProductColor());
            productPrice.setText("Price: " + String.valueOf(new Double(singleProduct.getProductPrice()).intValue()) + " $");
            productDescription.setText(Html.fromHtml("<strong>Product Description</strong><br/>" + singleProduct.getProductDescription()));
        }

        Button addToCartButton = (Button)findViewById(R.id.add_to_cart);
        assert addToCartButton != null;
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //increase product count
                String productsFromCart = sharedPreference.retrieveProductFromCart();
                if(productsFromCart.equals("")){
                    List<ProductObject> cartProductList = new ArrayList<ProductObject>();
                    cartProductList.add(singleProduct);
                    String cartValue = gson.toJson(cartProductList);
                    sharedPreference.addProductToTheCart(cartValue);
                    cartProductNumber = cartProductList.size();
                }else{
                    String productsInCart = sharedPreference.retrieveProductFromCart();
                    ProductObject[] storedProducts = gson.fromJson(productsInCart, ProductObject[].class);

                    List<ProductObject> allNewProduct = convertObjectArrayToListObject(storedProducts);
                    allNewProduct.add(singleProduct);
                    String addAndStoreNewProduct = gson.toJson(allNewProduct);
                    sharedPreference.addProductToTheCart(addAndStoreNewProduct);
                    cartProductNumber = allNewProduct.size();
                }
                sharedPreference.addProductCount(cartProductNumber);
                invalidateCart();
            }
        });
    }

    private List<ProductObject> convertObjectArrayToListObject(ProductObject[] allProducts){
        List<ProductObject> mProduct = new ArrayList<ProductObject>();
        Collections.addAll(mProduct, allProducts);
        return mProduct;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_shop);
        int mCount = sharedPreference.retrieveProductCount();
        menuItem.setIcon(buildCounterDrawable(mCount, R.drawable.cart));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_shop) {
            Intent checkoutIntent = new Intent(ProductActivity.this, CheckoutActivity.class);
            startActivity(checkoutIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.shopping_layout, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = (TextView) view.findViewById(R.id.count);
            textView.setText("" + count);
        }

        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

    private void invalidateCart() {
        invalidateOptionsMenu();
    }

}
