package com.example.dictionary;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class fetchData {
    private static final String TAG = "fetchData";
    Context c;
    String json = "";
    String j;
    Map<String, Object> m = null;
    JSONObject jsonObject = null;
    public fetchData(Context c) {
        this.c = c;
    }

    public Map<String, Object> fetchdata() throws IOException, JSONException {

        try {
            InputStream io = c.getAssets().open("new.json");
            int size = io.available();
            byte[] buffer = new byte[size];
            io.read(buffer);
            io.close();
            json = new String(buffer);
            jsonObject= new JSONObject(json);
            Log.v(TAG, "HEre "+jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        File file = new File("C:\\Users\\Lenovo\\Desktop\\Downloads\\Exercise-EditTextPreference\\Dictionary\\app\\src\\main\\assets\\new.json");
//        BufferedReader br = null;
//        try {
//            br = new BufferedReader(new FileReader(file));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        while ((j = br.readLine()) != null) {
//            json = j + json;
//        }
//        Log.v(TAG, "HEre "+json);
        JSONObject jsonobject = new JSONObject(json);
        JSONUtil map = null;
        try {
            m = new JSONUtil().jsonToMap(jsonobject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "HEre "+m.get("chickadee"));

        return m;
    }

}
