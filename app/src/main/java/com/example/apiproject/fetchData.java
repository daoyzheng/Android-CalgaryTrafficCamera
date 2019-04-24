package com.example.apiproject;

import android.os.AsyncTask;

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

public class fetchData extends AsyncTask<Void,Void,Void> {
    String jsonStr = "";
    String data = "";
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://data.calgary.ca/resource/35kd-jzrv.json?quadrant=NE");

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

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                data = data + "Description: " + jsonObject.get("description") + "\n" +
                        "Quadrant: " + jsonObject.get("quadrant") + "\n" +
                        "URL: " + jsonObject.get("url") + "\n\n";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MainActivity.data.setText(this.data);
    }
}
