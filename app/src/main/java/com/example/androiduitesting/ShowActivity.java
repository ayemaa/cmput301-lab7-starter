package com.example.androiduitesting;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        TextView cityName = findViewById(R.id.text_city_name);
        Button backButton = findViewById(R.id.button_back);

        // Get city name from intent
        String name = getIntent().getStringExtra("city_name");
        cityName.setText(name);

        // Back button
        backButton.setOnClickListener(v -> finish());
    }
}