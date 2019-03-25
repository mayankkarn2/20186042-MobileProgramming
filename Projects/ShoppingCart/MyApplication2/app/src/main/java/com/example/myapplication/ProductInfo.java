package com.example.myapplication;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductInfo extends AppCompatActivity {
//    final static String TAG = "Yooooo";
    JSONObject arr = new JSONObject();
    String user;
    TextView pnv;
    TextView pdv;
    TextView priceValue;
    Button button;
    EditText editText;
    final static String TAG = "ProductInfo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null) {
            try {
                arr = new JSONObject(getIntent().getStringExtra("JSON"));
                user =  b.getString("User");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        pnv = (TextView) findViewById(R.id.pdnv);
        pdv = (TextView) findViewById(R.id.pdv);
        priceValue = (TextView) findViewById(R.id.priceValue);
        button = (Button) findViewById(R.id.cart);
        editText = (EditText) findViewById(R.id.quantity);
        try {
            pnv.setText(arr.getString("Name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            pdv.setText(arr.getString("Description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            priceValue.setText(arr.getString("Price"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int available = 0;
                try {
                    available = Integer.parseInt(arr.getString("Quantity"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(editText.getText().toString().equals("")) {
                    Toast.makeText(ProductInfo.this, " Quantity cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(editText.getText().toString()) < 0) {
                    Toast.makeText(ProductInfo.this, " Quantity cannot be less than 0", Toast.LENGTH_SHORT).show();

                }
                else if(Integer.parseInt(editText.getText().toString()) > available)
                {
                    Toast.makeText(ProductInfo.this, "Only " +available +" items left", Toast.LENGTH_SHORT).show();

                }
                else {
                    Intent intent = new Intent(ProductInfo.this, CartPage.class);
                    intent.putExtra("JSON", arr.toString());
                    intent.putExtra("User", user);
                    intent.putExtra("quantity", editText.getText().toString());
                    try {
                        intent.putExtra("price", arr.getString("Price"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        intent.putExtra("image", arr.getString("ProductPicUrl"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        Log.v(TAG, "Quantity"+ editText.getText().toString()+"  "+ arr.getString("Price")+"  "+arr.getString("ProductPicUrl") );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);
                }
            }

        });
    }
}
