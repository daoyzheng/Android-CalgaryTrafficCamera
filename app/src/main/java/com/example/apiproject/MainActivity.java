package com.example.apiproject;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    Button btnDisplay;
    Spinner quadrantDropDown;
    Button btnShowMap;

    public static ImageView cameraDisplay;
    public static Spinner descriptionDisplay;
    public static TextView data;

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            // everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            // an error occurred but we can resolve it
            Log.d(TAG, "isServicesOK: an error occurred but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this,"You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = findViewById(R.id.displayView);
        btnDisplay = findViewById(R.id.btnDisplay);
        quadrantDropDown = findViewById(R.id.quadrantDropdown);
        descriptionDisplay = findViewById(R.id.descriptionDisplay);
        cameraDisplay = findViewById(R.id.cameraDisplay);
        btnShowMap = findViewById(R.id.btnShowMap);

        if (isServicesOK()) {
            init();
        }

        // Set custom layout to spinner
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,R.array.quadrant_arrays,R.layout.spinner_item);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        quadrantDropDown.setAdapter(dataAdapter);

        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get value selected from description spinner
                String description = descriptionDisplay.getSelectedItem().toString();
                // Get value selected from quadrant spinner
                String quadrant = quadrantDropDown.getSelectedItem().toString();

                // Create wrapper object and pass it to AsyncTask
                AsyncTaskParams params = new AsyncTaskParams(quadrant, description);
                fetchURL urlProcess = new fetchURL(MainActivity.this);
                urlProcess.execute(params);

                btnShowMap.setVisibility(View.VISIBLE);
            }
        });

        descriptionDisplay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                btnShowMap.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        quadrantDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get value selected from spinner
                String quadrant = quadrantDropDown.getSelectedItem().toString();

                // Fetch description and populate description drop down
                fetchData process = new fetchData(getApplicationContext());
                process.execute(quadrant);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void init() {

        btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    // Wrapper class to pass parameters to AsyncTask
    public static class AsyncTaskParams {
        String quadrant;
        String description;

        AsyncTaskParams(String quadrant, String description) {
            this.quadrant = quadrant;
            this.description = description;
        }
    }

}
