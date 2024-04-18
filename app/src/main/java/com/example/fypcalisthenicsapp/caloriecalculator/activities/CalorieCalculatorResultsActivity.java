package com.example.fypcalisthenicsapp.caloriecalculator.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.fypcalisthenicsapp.R;

import java.text.DecimalFormat;

public class CalorieCalculatorResultsActivity extends AppCompatActivity {

    private TextView weightGainTextView, midWeightGainTextView, maintainWeightTextView,
            midWeightLossTextView, weightLossTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_calculator_results);

        // Initialize TextViews
        weightGainTextView = findViewById(R.id.calorie_calculator_results_weight_gain_half_calories);
        midWeightGainTextView = findViewById(R.id.calorie_calculator_results_weight_gain_quarter_calories);
        maintainWeightTextView = findViewById(R.id.calorie_calculator_results_weight_maintain_calories);
        midWeightLossTextView = findViewById(R.id.calorie_calculator_results_weight_loss_quarter_calories);
        weightLossTextView = findViewById(R.id.calorie_calculator_results_weight_loss_half_calories);

        // Retrieve data from Intent
        Intent intent = getIntent();
        double weightGain = intent.getDoubleExtra("WeightGain", 0.0);
        double midWeightGain = intent.getDoubleExtra("MidWeightGain", 0.0);
        double maintainWeight = intent.getDoubleExtra("MaintainWeight", 0.0);
        double midWeightLoss = intent.getDoubleExtra("MidWeightLoss", 0.0);
        double weightLoss = intent.getDoubleExtra("WeightLoss", 0.0);

        // Update TextViews
        DecimalFormat df = new DecimalFormat("#.##");
        weightGainTextView.setText(df.format(weightGain));
        midWeightGainTextView.setText(df.format(midWeightGain));
        maintainWeightTextView.setText(df.format(maintainWeight));
        midWeightLossTextView.setText(df.format(midWeightLoss));
        weightLossTextView.setText(df.format(weightLoss));
    }
}