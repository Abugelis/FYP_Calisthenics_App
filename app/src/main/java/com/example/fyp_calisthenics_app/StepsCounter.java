package com.example.fyp_calisthenics_app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class StepsCounter extends AppCompatActivity implements SensorEventListener {

    private SensorManager mySensorManager = null;
    private Sensor stepSensor;
    private int totalSteps = 0;
    private int previewTotalSteps = 0;
    private ProgressBar progressBar;
    private TextView steps;
    private EditText stepGoalInput;
    private int stepGoal;
    private static final int MIDNIGHT_RESET_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_counter);

        progressBar = findViewById(R.id.progress_bar);
        steps = findViewById(R.id.steps_counter_steps_label);
        stepGoalInput = findViewById(R.id.steps_goal_input);

        loadStepGoal();
        resetSteps();
        loadData();
        registerMidnightResetReceiver(); // Register the BroadcastReceiver for midnight reset
        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        // Set the EditText with the current step goal
        stepGoalInput.setText(String.valueOf(stepGoal));

        // Listen for changes in the step goal input
        stepGoalInput.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                // Update the step goal when the user finishes editing
                updateStepGoal(stepGoalInput.getText().toString());
                return true;
            }
            return false;
        });
    }

    // Method to load the user-defined step goal from SharedPreferences
    private void loadStepGoal() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        stepGoal = sharedPreferences.getInt("stepGoal", 10000); // Default goal is 10000 steps
        updateStepGoalTextView();
    }

    // Method to update the user-defined step goal in SharedPreferences
    private void updateStepGoal(String goal) {
        if (!goal.isEmpty()) {
            // Update the step goal and save it to SharedPreferences
            stepGoal = Integer.parseInt(goal);
            saveStepGoal();

            // Update the TextView displaying the step goal
            updateStepGoalTextView();

            // Clear focus from EditText and hide keyboard
            stepGoalInput.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(stepGoalInput.getWindowToken(), 0);
        }
    }


    // Method to save the user-defined step goal to SharedPreferences
    private void saveStepGoal() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stepGoal", stepGoal);
        editor.apply();
    }

    // Update the TextView displaying the step goal
    private void updateStepGoalTextView() {
        TextView stepGoalTextView = findViewById(R.id.step_goal_label);
        String goalText = getString(R.string.goal_prefix) + " " + getString(R.string.steps_display_value, stepGoal);
        stepGoalTextView.setText(goalText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (stepSensor == null) {
            Toast.makeText(this, "This device has no step counter sensor", Toast.LENGTH_SHORT).show();
        } else {
            mySensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            totalSteps = (int) sensorEvent.values[0];
            int currentSteps = totalSteps - previewTotalSteps;
            steps.setText(String.valueOf(currentSteps));

            // Calculate progress percentage
            int progressPercentage = (int) ((double) currentSteps / stepGoal * 100);

            // Ensure progress percentage is within range
            progressPercentage = Math.min(progressPercentage, 100);

            // Update progress bar on the main UI thread
            int finalProgressPercentage = progressPercentage;
            runOnUiThread(() -> progressBar.setProgress(finalProgressPercentage));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void resetSteps() {
        steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(StepsCounter.this, "Long press to reset steps", Toast.LENGTH_SHORT).show();
            }
        });

        steps.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                previewTotalSteps = totalSteps;
                steps.setText("0");
                progressBar.setProgress(0);
                saveData();
                return true;
            }
        });
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        int savedNumber = sharedPreferences.getInt("key1", 0);
        previewTotalSteps = savedNumber;
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("key1", previewTotalSteps);
        editor.apply();
    }

    // Method to register the BroadcastReceiver for midnight reset
    private void registerMidnightResetReceiver() {
        Intent intent = new Intent(this, MidnightResetReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE); // Add FLAG_IMMUTABLE here

        // Calculate the time for midnight
        Calendar midnight = Calendar.getInstance();
        midnight.set(Calendar.HOUR_OF_DAY, 0);
        midnight.set(Calendar.MINUTE, 0);
        midnight.set(Calendar.SECOND, 0);

        // If the current time is already past midnight, set it for the next day
        if (midnight.before(Calendar.getInstance())) {
            midnight.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Set up the alarm manager to trigger the broadcast at midnight
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC, midnight.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
