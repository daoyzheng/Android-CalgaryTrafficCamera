package com.example.apiproject;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class fetchData extends AsyncTask<String,Void,String> {
    private Context mContext;

    public fetchData (Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... params) {
        String jsonStr = "";
        try {
            String urlStr = String.format("https://data.calgary.ca/resource/35kd-jzrv.json?quadrant=%s",params[0]);
            URL url = new URL(urlStr);

            //Open HTTPS connection
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            //Include app token for calgary data API
            conn.addRequestProperty("X-App-Token", "z7Aep6xlwpG4GAN0ihFbvCn12");

            //Set Request property
            conn.setRequestProperty("Accept", "application/json");

            //Get input stream
            InputStream inputStream = conn.getInputStream();

            //Set up buffer
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonStr = jsonStr + line;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonStr;
    }

    @Override
    protected void onPostExecute(String jsonStr) {
        super.onPostExecute(jsonStr);

        List<String> descriptionStr = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                descriptionStr.add(String.valueOf(jsonObject.get("description")));
            }

            // Populate Description spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mContext,R.layout.spinner_item, descriptionStr);
            dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            MainActivity.descriptionDisplay.setAdapter(dataAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
