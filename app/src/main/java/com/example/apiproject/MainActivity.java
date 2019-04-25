package com.example.apiproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnDisplay;
    Spinner quadrantDropDown;
    public static TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = findViewById(R.id.displayView);
        btnDisplay = findViewById(R.id.btnDisplay);
        quadrantDropDown = findViewById(R.id.quadrantDropdown);

        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get value selected from spinner
                String quadrant = quadrantDropDown.getSelectedItem().toString();

                fetchData process = new fetchData();
                process.execute(quadrant);
            }
        });
    }

}
