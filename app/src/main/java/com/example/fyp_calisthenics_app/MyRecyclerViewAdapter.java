package com.example.fyp_calisthenics_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<MainMenuModel> mainMenuModels;
    public MyRecyclerViewAdapter(Context context, ArrayList<MainMenuModel> mainMenuModels){
        this.context = context;
        this.mainMenuModels = mainMenuModels;
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_row, parent, false);
        return new MyRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.MyViewHolder holder, int position) {
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
                            Intent stepCounterIntent = new Intent(context, StepsCounter.class);
                            context.startActivity(stepCounterIntent);
                            break;
                        case "Calorie Calculator":
                            // Start CalorieCalculatorActivity
                            Intent calorieCalculatorIntent = new Intent(context, CalorieCalculator.class);
                            context.startActivity(calorieCalculatorIntent);
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

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView menuImages;
        TextView menuNames;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            menuImages = itemView.findViewById(R.id.menu_image);
            menuNames = itemView.findViewById(R.id.menu_text);

        }
    }
}
