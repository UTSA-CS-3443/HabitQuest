package edu.usta.cs3443.habitquest;

import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_goal);

        recyclerView = findViewById(R.id.recyclerViewGoals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadGoals();
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
