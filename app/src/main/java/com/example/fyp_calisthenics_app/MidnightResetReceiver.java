package com.example.fyp_calisthenics_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class MidnightResetReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Reset the step counter here
        resetSteps(context);
    }

    private void resetSteps(Context context) {
        // Reset the step count to 0
        SharedPreferences sharedPreferences = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("key1", 0);
        editor.apply();
    }
}
