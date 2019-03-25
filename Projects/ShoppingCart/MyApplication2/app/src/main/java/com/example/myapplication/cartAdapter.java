package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.CartViewHolder>{
    private static final String TAG = "cartAdapter";
    Context c;
    String user;
    ArrayList<String> data ;
    private DatabaseReference mDatabase;
    public cartAdapter(ArrayList<String> data, String user) {
        this.data = data;
        this.user = user;
    }
    @NonNull

    @Override
    public int getItemViewType(int position) {
        return (position == data.size()) ? R.layout.button : R.layout.cart_product;
    }

    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(viewGroup.getContext());
        View view;
        if(i == R.layout.cart_product) {
            view = inflater.inflate(R.layout.cart_product, viewGroup, false);
        }

        else {
            view = inflater.inflate(R.layout.button, viewGroup, false);

        }
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder cartViewHolder, final int in) {

        if(in == data.size()) {
            cartViewHolder.Total.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int t = sum();
                    Toast.makeText(cartViewHolder.Total.getContext(), "Total is " + t, Toast.LENGTH_SHORT).show();
                }
            });
            cartViewHolder.order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    placeOrder();
//                    data.clear();
//                    cartUpdate();
                    Toast.makeText(cartViewHolder.order.getContext(), "Order is placed ", Toast.LENGTH_SHORT).show();

                }
            });
        }
        else {
            String raw_data = data.get(in);
            String[] values = raw_data.split("#");
            String title = values[0];
            String quan = values[1];
            String price = values[2];
            String image = values[3];
            cartViewHolder.t1.setText(title);
            cartViewHolder.t3.setText(quan);
            cartViewHolder.t5.setText(Integer.toString(Integer.parseInt(price) * Integer.parseInt(quan)));
            String url = "http://msitmp.herokuapp.com" + image;
            Picasso.get().load(url).into(cartViewHolder.imageView);
            cartViewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.remove(in);
                    notifyItemRemoved(in);
                    notifyItemRangeChanged(in, data.size());
                    cartUpdate();
                }
            });
        }
    }

    public void placeOrder() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = "";
                for(int i = 0; i < data.size(); i++) {
                    s = data.get(i);
                }
                Map<String, Object> res = new HashMap<String, Object>();
                res.put("orders", s);
                res.put("cart", "");
                data.clear();
                notifyDataSetChanged();
                cartUpdate();
                mDatabase.updateChildren(res);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void cartUpdate() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = "";
                for(int i = 0; i < data.size(); i++) {
                    s = data.get(i);
                }
                Map<String, Object> res = new HashMap<String, Object>();
                res.put("cart", s);
                mDatabase.updateChildren(res);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public int sum() {
        int cal = 0;
        for(int i = 0; i < data.size();i++) {
            String[] a = data.get(i).split("#");
            cal += Integer.parseInt(a[1]) * Integer.parseInt(a[2]);
        }
        return cal;
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }
    public class CartViewHolder extends RecyclerView.ViewHolder{
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
        TextView t5;
        ImageView imageView;
        Button button;
        public Button Total;
        Button order;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            c = itemView.getContext();
            imageView = itemView.findViewById(R.id.cart_image);
            t1 = itemView.findViewById(R.id.cart_title);
            t3 = itemView.findViewById(R.id.qtyv);
            t5 = itemView.findViewById(R.id.stv);
            button = itemView.findViewById(R.id.del_btn);
            Total = itemView.findViewById(R.id.sum_button);
            order = itemView.findViewById(R.id.cart_order);
        }
    }
}
