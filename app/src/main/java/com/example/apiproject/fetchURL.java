package com.example.apiproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
    String imgURL = null;
    private Context mContext;

    public fetchURL (Context mContext) {
       this.mContext = mContext;
    }

    @Override
    protected Void doInBackground(MainActivity.AsyncTaskParams... asyncTaskParams) {
        String jsonStr = "";
        try {
            String quadrant = asyncTaskParams[0].quadrant;
            String description = asyncTaskParams[0].description;

            // If description contains '&', remove it and the rest of the string,
            // since it's not allowed in the query parameter
            // then use $starts_with query function
            String urlStr;
            int index;
            if ((index = description.indexOf('&')) == -1) {
                // Does not contain '&'
                urlStr = String.format("https://data.calgary.ca/resource/35kd-jzrv.json?quadrant=%s&description=%s",quadrant,description);
            } else {
                description = description.substring(0,index);
                urlStr = String.format("https://data.calgary.ca/resource/35kd-jzrv.json?quadrant=%s&$where=starts_with(description,'%s')",quadrant,description);
            }

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
                data = data + "Quadrant:\n" + jsonObject.get("quadrant") + "\n\n" +
                        "Intersections:\n" + jsonObject.get("description");
                        //"URL: " + jsonObject.get("url") + "\n\n";
            }

            if (jsonArray.length() == 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                imgURL = jsonObject.get("url").toString();
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(imgURL).getContent());

                // Get longitude and latitude
                MapActivity.lat = Double.parseDouble(jsonObject.get("latitude").toString());
                MapActivity.lng = Double.parseDouble(jsonObject.get("longitude").toString());

                // Get description
                MapActivity.description = jsonObject.get("description").toString();
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

        MainActivity.cameraDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(imgURL));
                mContext.startActivity(intent);
            }
        });
    }
}
