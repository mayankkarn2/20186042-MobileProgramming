package com.example.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartPage extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;
    JSONObject arr = new JSONObject();
    String user;
    String[] values;
    ArrayList<String> data = new ArrayList<>();
    int total=0;
//    TextView sum = (TextView) findViewById(R.id.total);
    String quantity, price, image;
    final static String TAG = "Cart Page";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null) {
            try {
                arr = new JSONObject(getIntent().getStringExtra("JSON"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            user =  b.getString("User");
            quantity = b.getString("quantity");
            price = b.getString("price");
            image = b.getString("image");
            Log.v(TAG, "Quantity"+quantity);

        }
        setContentView(R.layout.activity_cart_page);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_cartlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.order:
                Toast.makeText(CartPage.this, "Order Placed", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.total:
                Toast.makeText(CartPage.this, "Total is", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String cart = dataSnapshot.child("cart").getValue().toString();
                String update = "";
                if(cart.equals("")) {
                    try {
                        update = arr.getString("Name")+"#"+quantity+"#"+price+"#"+image;
                        total += Integer.parseInt(quantity)*Integer.parseInt(price);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        if(cart.contains(arr.getString("Name"))){
                            update = arr.getString("Name")+"#"+quantity+"#"+price+"#"+image;
                            total += Integer.parseInt(quantity)*Integer.parseInt(price);
                            Toast.makeText(CartPage.this, "Already in Cart", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            try {
                                update = cart + ";" + arr.getString("Name")+"#"+quantity+"#"+price+"#"+image;
                                total += Integer.parseInt(quantity)*Integer.parseInt(price);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Map<String, Object> res = new HashMap<String, Object>();
                res.put("cart", update);
                mDatabase.updateChildren(res);
                values = update.split(";");
                for(String value : values) {
                    data.add(value);
                }
                Log.v(TAG, "Data " + data);
                mRecyclerView.setAdapter(new cartAdapter(data, user));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
