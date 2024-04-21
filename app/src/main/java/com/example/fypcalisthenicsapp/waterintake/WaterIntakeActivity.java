package com.example.fypcalisthenicsapp.waterintake;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fypcalisthenicsapp.R;

public class WaterIntakeActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String SELECTED_VALUE_KEY = "selectedValue";

    private NumberPicker numberPicker;
    private ProgressBar progressBar;
    private TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake);

        numberPicker = findViewById(R.id.numberPicker);
        progressBar = findViewById(R.id.water_progress_bar);
        messageTextView = findViewById(R.id.messageTextView);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(15);

        // Load the previously selected value from SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedValue = prefs.getInt(SELECTED_VALUE_KEY, 0); // Default value is 0
        numberPicker.setValue(savedValue);

        // Update progress bar and message text based on the selected value
        updateProgressBarAndMessage(savedValue);

        // Listen for changes in the number picker
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            // Save the selected value to SharedPreferences
            saveSelectedValue(newVal);

            // Update progress bar and message text based on the new selected value
            updateProgressBarAndMessage(newVal);
        });
    }

    // Method to save the selected value to SharedPreferences
    private void saveSelectedValue(int value) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(SELECTED_VALUE_KEY, value);
        editor.apply();
    }

    // Method to update progress bar and message text based on the selected value
    private void updateProgressBarAndMessage(int value) {
        int progress = value * 10; // Each glass represents 10% progress
        progressBar.setProgress(progress);

        // Update text based on the selected number of glasses
        String message;
        if (value >= 1 && value <= 2) {
            message = "Great Start";
        } else if (value > 2 && value <= 4) {
            message = "Almost Half Way";
        } else if (value == 5) {
            message = "Half Way There";
        } else if (value > 5 && value <= 6) {
            message = "Keep It Up";
        } else if (value >= 7 && value <= 9) {
            message = "Nearly There";
        } else if (value >= 10) {
            message = "Well Done!";
        } else {
            message = "";
        }
        messageTextView.setText(message);
    }
}
