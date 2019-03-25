package com.example.dictionary;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class dic_ary extends AppCompatActivity {
    private static final String TAG = "dic_ary";
    Button search;
    EditText query;
    TextView res;
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dic_ary);
        search = (Button) findViewById(R.id.search_word);
        query = (EditText) findViewById(R.id.word);
        res = (TextView) findViewById(R.id.meaning);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"1:");
                out();
            }
        });
    }
    public void out() {
        Log.v(TAG,"2:");
        ProgressDialog loadingBar;
        Log.v(TAG,"3:");
        loadingBar = new ProgressDialog(this);
        Map<String, Object> m = null;
        try {
            loadingBar.setTitle("Loading Dictionary");
            loadingBar.setMessage("Please Wait..");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            m = new fetchData(dic_ary.this).fetchdata();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loadingBar.dismiss();
        Log.v(TAG,"sas "+ m.get("chickadee"));
        Log.v(TAG,"sas "+ query.getText().toString());
        result = (String) m.get(query.getText().toString());
        Log.v(TAG,"sas "+ result);
        if(result == null) {
            res.setText("No word Found");
        }
        else {
            res.setText(result);
        }
    }
}
