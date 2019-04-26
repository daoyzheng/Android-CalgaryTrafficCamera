package com.example.apiproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnDisplay;
    Spinner quadrantDropDown;

    public static ImageView cameraDisplay;
    public static Spinner descriptionDisplay;
    public static TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = findViewById(R.id.displayView);
        btnDisplay = findViewById(R.id.btnDisplay);
        quadrantDropDown = findViewById(R.id.quadrantDropdown);
        descriptionDisplay = findViewById(R.id.descriptionDisplay);
        cameraDisplay = findViewById(R.id.cameraDisplay);

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
