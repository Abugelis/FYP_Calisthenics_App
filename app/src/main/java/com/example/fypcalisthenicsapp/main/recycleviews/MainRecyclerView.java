package com.example.fypcalisthenicsapp.main.recycleviews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fypcalisthenicsapp.R;
import com.example.fypcalisthenicsapp.caloriecalculator.activities.CalorieCalculatorActivity;
import com.example.fypcalisthenicsapp.main.models.MainMenuModel;
import com.example.fypcalisthenicsapp.stepcounter.activities.StepCounterActivity;
import com.example.fypcalisthenicsapp.waterintake.WaterIntakeActivity;
import com.example.fypcalisthenicsapp.workout.activities.WorkoutMenuActivity;

import java.util.ArrayList;

public class MainRecyclerView extends RecyclerView.Adapter<MainRecyclerView.MyViewHolder> {
    Context context;
    ArrayList<MainMenuModel> mainMenuModels;

    public MainRecyclerView(Context context, ArrayList<MainMenuModel> mainMenuModels) {
        this.context = context;
        this.mainMenuModels = mainMenuModels;
    }

    @NonNull
    @Override
    public MainRecyclerView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_row, parent, false);
        return new MainRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerView.MyViewHolder holder, int position) {
        holder.menuImages.setImageResource(mainMenuModels.get(position).getMenuImage());
        holder.menuNames.setText(mainMenuModels.get(position).getMenuName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getBindingAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    String itemName = mainMenuModels.get(clickedPosition).getMenuName();

                    // Start the relevant activity based on the clicked item
                    switch (itemName) {
                        case "Steps Counter":
                            // Start StepCounterActivity
                            Intent stepCounterIntent = new Intent(context, StepCounterActivity.class);
                            context.startActivity(stepCounterIntent);
                            break;
                        case "Calorie Calculator":
                            // Start CalorieCalculatorActivity
                            Intent calorieCalculatorIntent = new Intent(context, CalorieCalculatorActivity.class);
                            context.startActivity(calorieCalculatorIntent);
                            break;
                        case "Water Intake":
                            // Start WaterIntakeActivity
                            Intent waterIntakeIntent = new Intent(context, WaterIntakeActivity.class);
                            context.startActivity(waterIntakeIntent);
                            break;
                        case "Workout":
                            Intent workoutIntent = new Intent(context, WorkoutMenuActivity.class);
                            context.startActivity(workoutIntent);
                            break;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainMenuModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView menuImages;
        TextView menuNames;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            menuImages = itemView.findViewById(R.id.menu_image);
            menuNames = itemView.findViewById(R.id.menu_text);

        }
    }
}
