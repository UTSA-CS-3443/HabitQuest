package edu.usta.cs3443.habitquest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.usta.cs3443.habitquest.model.Goal;
import edu.usta.cs3443.habitquest.model.GoalAdapter;

import java.util.List;

public class todays_goal_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GoalAdapter goalAdapter;
    private List<Goal> goalList;
    private Button addHabitButton;
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_goal);

        recyclerView = findViewById(R.id.recyclerViewGoals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadGoals();

        homeButton = findViewById(R.id.homeButton);
        addHabitButton = findViewById(R.id.addHabitButton);

        // Home button
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(todays_goal_Activity.this, MainActivity.class);
            startActivity(intent);
        });

        // Add habit button
        addHabitButton.setOnClickListener(v -> {
            Intent intent = new Intent(todays_goal_Activity.this, set_Goal_Activity.class);
            startActivity(intent);
        });
    }

    private void loadGoals() {
        List<Goal> newGoals = Goal.loadGoalsFromCSV(this);

        Log.d("TodaysGoalActivity", "Loaded goals: " + newGoals.size());

        if (goalAdapter == null) {
            goalList = newGoals;
            goalAdapter = new GoalAdapter(goalList, this);
            recyclerView.setAdapter(goalAdapter);
        } else {
            if (!goalList.equals(newGoals)) {
                goalList.clear();
                goalList.addAll(newGoals);
                goalAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Optionally reload goals if necessary
        if (goalAdapter != null) {
            loadGoals();
        }
    }
}
