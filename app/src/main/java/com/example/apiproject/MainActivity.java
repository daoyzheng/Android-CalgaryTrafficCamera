package com.example.apiproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnDisplay;
    Spinner quadrantDropDown;
    TextView quadrantTest;
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
        quadrantTest = findViewById(R.id.quadrantTest);

        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        quadrantDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get value selected from spinner
                String quadrant = quadrantDropDown.getSelectedItem().toString();
                quadrantTest.setText(quadrant);

                fetchData process = new fetchData(getApplicationContext());
                process.execute(quadrant);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
