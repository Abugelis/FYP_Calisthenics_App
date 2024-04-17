package com.example.fypcalisthenicsapp.main.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fypcalisthenicsapp.main.models.MainMenuModel;
import com.example.fypcalisthenicsapp.main.recycleviews.MainRecyclerView;
import com.example.fypcalisthenicsapp.R;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    ArrayList<MainMenuModel> mainMenuModels = new ArrayList<>();
    int[] menuImages = {R.drawable.workout_menu, R.drawable.main_menu_test_2, R.drawable.menu_meal_log,
            R.drawable.menu_water_test_2, R.drawable.menu_outdoor_test, R.drawable.menu_feedback_test};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
        recyclerView.setBackgroundColor(ContextCompat.getColor(this, R.color.grey));

        setUpMainMenuModels();

        MainRecyclerView adapter = new MainRecyclerView(this, mainMenuModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpMainMenuModels() {
        String[] menuOptions = getResources().getStringArray(R.array.main_menu_text);

        for (int i = 0; i < menuOptions.length; i++) {
            mainMenuModels.add(new MainMenuModel(menuOptions[i], menuImages[i]));
        }
    }
}