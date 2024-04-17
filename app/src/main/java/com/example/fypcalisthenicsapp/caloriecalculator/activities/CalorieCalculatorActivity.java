package com.example.fypcalisthenicsapp.caloriecalculator.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fypcalisthenicsapp.R;

import java.text.DecimalFormat;

public class CalorieCalculatorActivity extends AppCompatActivity {

    private EditText ageEditText, heightEditText, weightEditText;
    private RadioButton maleRadioButton, femaleRadioButton;
    private Spinner activityLevelSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_calculator);

        ageEditText = findViewById(R.id.calorie_calculator_age);
        heightEditText = findViewById(R.id.calorie_calculator_height);
        weightEditText = findViewById(R.id.calorie_calculator_weight);
        maleRadioButton = findViewById(R.id.radioButton);
        femaleRadioButton = findViewById(R.id.radioButton2);
        activityLevelSpinner = findViewById(R.id.calorie_calculator_activity_level_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.calorie_calculator_activity_level, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityLevelSpinner.setAdapter(adapter);

        findViewById(R.id.calorie_calculator_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCalories();
            }
        });
    }

    private void calculateCalories() {
        double age, height, weight;
        try {
            age = Double.parseDouble(ageEditText.getText().toString());
            height = Double.parseDouble(heightEditText.getText().toString());
            weight = Double.parseDouble(weightEditText.getText().toString());

            // Calculate BMR based on gender
            double bmr = 0;
            if (maleRadioButton.isChecked()) {
                bmr = (13.75 * weight) + (5 * height) - (6.76 * age) + 66;
            } else if (femaleRadioButton.isChecked()) {
                bmr = (9.56 * weight) + (1.85 * height) - (4.68 * age) + 655;
            }

            // Get activity level points
            double activityPoints = 1.2;
            int position = activityLevelSpinner.getSelectedItemPosition();
            switch (position) {
                case 0:
                    activityPoints = 1.2;
                    break;
                case 1:
                    activityPoints = 1.37;
                    break;
                case 2:
                    activityPoints = 1.55;
                    break;
                case 3:
                    activityPoints = 1.725;
                    break;
                case 4:
                    activityPoints = 1.9;
                    break;
            }

            // Calculate total calories burned
            double totalCalories = bmr * activityPoints;

            // Display the result
            DecimalFormat df = new DecimalFormat("#.##");
            String result = "Total calories burned per day: " + df.format(totalCalories);
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid values for age, height, and weight", Toast.LENGTH_SHORT).show();
        }
    }
}
