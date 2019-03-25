package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class productAdapter extends RecyclerView.Adapter<productAdapter.ProductViewHolder> {

    private JSONObject data;
    JSONArray arr;
    List<String> listTitles;
    Context c;
    List<String> Images;
    final static String TAG = "Yo";
    String user;
    public productAdapter(JSONObject data, String user) {
        this.data = data;
        this.user = user;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.product_list, viewGroup, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, final int in) {
        String title;
        title = listTitles.get(in);
        String img;
        img = Images.get(in);
        String url = "http://msitmp.herokuapp.com"+img;
        productViewHolder.txtTitle.setText(title);
        Picasso.get().load(url).into(productViewHolder.imageView);
        productViewHolder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, ProductInfo.class);
                try {
                    intent.putExtra("JSON", (Serializable) arr.getJSONObject(in).toString());
                    intent.putExtra("User", user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.v(TAG, "User"+ user);
                c.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        arr = new JSONArray();
        try {
            arr = data.getJSONArray("ProductCollection");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listTitles = new ArrayList<String>();
        for(int i = 0; i < arr.length();i++) {
            try {
                listTitles.add(arr.getJSONObject(i).getString("Name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Images = new ArrayList<String>();
        for(int i = 0; i < arr.length();i++) {
            try {
                Images.add(arr.getJSONObject(i).getString("ProductPicUrl"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return listTitles.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        ImageView imageView;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            c = itemView.getContext();
            txtTitle = itemView.findViewById(R.id.product_text_id);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
