package com.example.apiproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class fetchURL extends AsyncTask <MainActivity.AsyncTaskParams, Void, Void> {
    String data = "";
    Bitmap bitmap = null;
    @Override
    protected Void doInBackground(MainActivity.AsyncTaskParams... asyncTaskParams) {
        String jsonStr = "";
        try {
            String quadrant = asyncTaskParams[0].quadrant;
            String description = asyncTaskParams[0].description;

            String urlStr = String.format("https://data.calgary.ca/resource/35kd-jzrv.json?quadrant=%s&description=%s",quadrant,description);
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

            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i=0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                data = data + "Quadrant: " + jsonObject.get("quadrant") + "\n" +
                        "Description: " + jsonObject.get("description") + "\n" +
                        "URL: " + jsonObject.get("url") + "\n\n";
            }

            if (jsonArray.length() == 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String imgURL = jsonObject.get("url").toString();
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(imgURL).getContent());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainActivity.data.setText(data);
        MainActivity.cameraDisplay.setImageBitmap(bitmap);

    }
}
