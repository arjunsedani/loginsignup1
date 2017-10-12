package com.androidtutorialshub.loginregister.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.activities.ProductActivity;
import com.androidtutorialshub.loginregister.entities.ProductObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class ShopRecyclerViewAdapter extends RecyclerView.Adapter<ShopRecyclerViewHolder>{

    private static final String TAG = ShopRecyclerViewAdapter.class.getSimpleName();

    private Context context;

    private List<ProductObject> allProducts;

    public ShopRecyclerViewAdapter(Context context, List<ProductObject> allProducts) {
        this.context = context;
        this.allProducts = allProducts;
    }

    @Override
    public ShopRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_listing, parent, false);
        ShopRecyclerViewHolder productHolder = new ShopRecyclerViewHolder(layoutView);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(ShopRecyclerViewHolder holder, int position) {
        final ProductObject singleProduct = allProducts.get(position);

        holder.productName.setText(singleProduct.getProductName());
        holder.produceImage.setImageResource(singleProduct.getProductImage());

        holder.produceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productIntent = new Intent(context, ProductActivity.class);

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                String stringObjectRepresentation = gson.toJson(singleProduct);

                productIntent.putExtra("PRODUCT", stringObjectRepresentation);
                context.startActivity(productIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allProducts.size();
    }
}
